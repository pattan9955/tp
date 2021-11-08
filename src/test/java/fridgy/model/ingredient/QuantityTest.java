package fridgy.model.ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fridgy.testutil.Assert;

public class QuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Quantity(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Quantity(invalidQuantity));
    }

    @Test
    public void isValidQuantity() {
        // null quantity number
        Assert.assertThrows(NullPointerException.class, () -> Quantity.isValidQuantityString(null));

        // invalid quantity numbers
        assertFalse(Quantity.isValidQuantityString("")); // empty string
        assertFalse(Quantity.isValidQuantityString(" ")); // spaces only
        assertFalse(Quantity.isValidQuantityString("0")); // zero number
        assertFalse(Quantity.isValidQuantityString("-23423")); // negative number
        assertFalse(Quantity.isValidQuantityString("0000000")); // string of zeros
        assertFalse(Quantity.isValidQuantityString("quantity")); // non-numeric
        assertFalse(Quantity.isValidQuantityString("9011p041")); // alphabets within digits
        assertFalse(Quantity.isValidQuantityString("9312 1534")); // spaces within digits

        // valid quantity numbers
        assertTrue(Quantity.isValidQuantityString("9")); // exactly 1 number
        assertTrue(Quantity.isValidQuantityString("9312"));
        assertTrue(Quantity.isValidQuantityString("0009312")); // leading zeros
        assertTrue(Quantity.isValidQuantityString("124293899")); // 9 quantity numbers
        assertTrue(Quantity.isValidQuantityString("1.2979")); // fractions
    }

    @Test
    public void hashCodeTest() {
        Quantity qty = new Quantity("90kg");
        Quantity qtySame = new Quantity("90kg");
        Quantity qtyDiff = new Quantity("0.2g");

        assertEquals(qty.hashCode(), qty.hashCode());
        assertEquals(qty.hashCode(), qtySame.hashCode());
        assertNotEquals(qty.hashCode(), qtyDiff.hashCode());
    }

    @Test
    public void getUnitTest() {
        Quantity qtyMassUnit1 = new Quantity("90kg");
        Quantity qtyMassUnit2 = new Quantity("9kg");
        Quantity qtyLiquidUnit1 = new Quantity("0.2l");
        Quantity qtyLiquidUnit2 = new Quantity("2ml");
        Quantity noUnit1 = new Quantity("5");
        Quantity noUnit2 = new Quantity("10");


        // All mass units should be standardised to grams
        assertEquals(qtyMassUnit1.getUnits(), qtyMassUnit1.getUnits());
        assertEquals(qtyMassUnit1.getUnits(), qtyMassUnit2.getUnits());
        assertEquals("g", qtyMassUnit1.getUnits());
        assertEquals("g", qtyMassUnit2.getUnits());
        // Mass and liquid units should differ
        assertNotEquals(qtyMassUnit1.getUnits(), qtyLiquidUnit1.getUnits());
        assertNotEquals(qtyMassUnit1.getUnits(), qtyLiquidUnit2.getUnits());
        assertNotEquals(qtyMassUnit2.getUnits(), qtyLiquidUnit2.getUnits());
        // Mass and no units should differ
        assertNotEquals(qtyMassUnit1.getUnits(), noUnit1.getUnits());
        assertNotEquals(qtyMassUnit1.getUnits(), noUnit2.getUnits());
        assertNotEquals(qtyMassUnit2.getUnits(), noUnit2.getUnits());
        // All liquid units should be standardised to litres
        assertEquals(qtyLiquidUnit1.getUnits(), qtyLiquidUnit1.getUnits());
        assertEquals(qtyLiquidUnit1.getUnits(), qtyLiquidUnit2.getUnits());
        assertEquals(qtyLiquidUnit2.getUnits(), qtyLiquidUnit2.getUnits());
        assertEquals("l", qtyLiquidUnit1.getUnits());
        assertEquals("l", qtyLiquidUnit2.getUnits());
        // All no units should have empty unit string
        assertEquals(noUnit1.getUnits(), noUnit1.getUnits());
        assertEquals(noUnit1.getUnits(), noUnit2.getUnits());
        assertEquals(noUnit2.getUnits(), noUnit2.getUnits());
        assertEquals("", noUnit1.getUnits());
        assertEquals("", noUnit2.getUnits());
        // Liquid and no units should differ
        assertNotEquals(qtyLiquidUnit1.getUnits(), noUnit1.getUnits());
        assertNotEquals(qtyLiquidUnit2.getUnits(), noUnit1.getUnits());
        assertNotEquals(qtyLiquidUnit1.getUnits(), noUnit2.getUnits());
        assertNotEquals(qtyLiquidUnit2.getUnits(), noUnit2.getUnits());
    }

    @Test
    public void getValueTest() {
        Quantity qtyMassUnit = new Quantity("9kg"); // 9000g
        Quantity qtyLiquidUnit = new Quantity("9l"); // 9l
        Quantity noUnit1 = new Quantity("9"); // 9

        assertEquals(9000, qtyMassUnit.getValue());
        assertEquals(9, qtyLiquidUnit.getValue());
        assertEquals(9, noUnit1.getValue());
    }

    @Test
    public void compareQtyWithSameUnitsTest() {
        Quantity qtyMassUnit = new Quantity("0.009kg"); // should be 9g
        Quantity qtyLiquidUnit = new Quantity("9l"); // 9l
        Quantity noUnit = new Quantity("9"); // 9

        assertEquals(0, qtyMassUnit.compareQtyWithSameUnits(qtyMassUnit));
        assertEquals(0, qtyLiquidUnit.compareQtyWithSameUnits(qtyLiquidUnit));
        assertEquals(0, noUnit.compareQtyWithSameUnits(noUnit));
    }
}
