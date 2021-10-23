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
        Assert.assertThrows(NullPointerException.class, () -> Quantity.isValidQuantity(null));

        // invalid quantity numbers
        assertFalse(Quantity.isValidQuantity("")); // empty string
        assertFalse(Quantity.isValidQuantity(" ")); // spaces only
        assertFalse(Quantity.isValidQuantity("0")); // zero number
        assertFalse(Quantity.isValidQuantity("-23423")); // negative number
        assertFalse(Quantity.isValidQuantity("0000000")); // string of zeros
        assertFalse(Quantity.isValidQuantity("quantity")); // non-numeric
        assertFalse(Quantity.isValidQuantity("9011p041")); // alphabets within digits
        assertFalse(Quantity.isValidQuantity("9312 1534")); // spaces within digits

        // valid quantity numbers
        assertTrue(Quantity.isValidQuantity("9")); // exactly 1 number
        assertTrue(Quantity.isValidQuantity("9312"));
        assertTrue(Quantity.isValidQuantity("0009312")); // leading zeros
        assertTrue(Quantity.isValidQuantity("124293899")); // 9 quantity numbers
        assertTrue(Quantity.isValidQuantity("1.2979")); // fractions
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
}
