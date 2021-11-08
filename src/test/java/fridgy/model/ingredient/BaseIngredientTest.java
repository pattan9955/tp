package fridgy.model.ingredient;

import static fridgy.logic.commands.CommandTestUtil.VALID_NAME_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_QUANTITY_BASIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.TypicalBaseIngredients;

public class BaseIngredientTest {

    @Test
    public void isSameIngredient() {
        // same object -> returns true
        assertTrue(TypicalBaseIngredients.APPLE.isSameIngredient(TypicalBaseIngredients.APPLE));
        assertTrue(TypicalBaseIngredients.APPLE.isSame(TypicalBaseIngredients.APPLE));

        // null -> returns false
        assertFalse(TypicalBaseIngredients.APPLE.isSameIngredient(null));

        // same name, all other attributes different -> returns true
        BaseIngredient editedAlmond = new IngredientBuilder(TypicalBaseIngredients.APPLE)
                .withQuantity(VALID_QUANTITY_BASIL).buildBaseIngredient();
        assertTrue(TypicalBaseIngredients.APPLE.isSameIngredient(editedAlmond));

        // different name, all other attributes same -> returns false
        editedAlmond = new IngredientBuilder(TypicalBaseIngredients.APPLE)
                .withName(VALID_NAME_BASIL).buildBaseIngredient();
        assertFalse(TypicalBaseIngredients.APPLE.isSameIngredient(editedAlmond));

        // name differs in case, all other attributes same -> returns true
        BaseIngredient editedBasil = new IngredientBuilder(TypicalBaseIngredients.BASIL)
                .withName(VALID_NAME_BASIL.toLowerCase()).buildBaseIngredient();
        assertTrue(TypicalBaseIngredients.BASIL.isSameIngredient(editedBasil));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BASIL + " ";
        editedBasil = new IngredientBuilder(TypicalBaseIngredients.BASIL)
                .withName(nameWithTrailingSpaces).buildBaseIngredient();
        assertFalse(TypicalBaseIngredients.BASIL.isSameIngredient(editedBasil));
    }

    @Test
    public void equals() {
        // same values -> returns true
        BaseIngredient almondCopy = new IngredientBuilder(TypicalBaseIngredients.APPLE).buildBaseIngredient();
        assertTrue(TypicalBaseIngredients.APPLE.equals(almondCopy));
        assertTrue(TypicalBaseIngredients.APPLE.toString().equals(almondCopy.toString()));

        // same object -> returns true
        assertTrue(TypicalBaseIngredients.APPLE.equals(TypicalBaseIngredients.APPLE));

        // null -> returns false
        assertFalse(TypicalBaseIngredients.APPLE.equals(null));

        // different type -> returns false
        assertFalse(TypicalBaseIngredients.APPLE.equals(5));

        // different base ingredient -> returns false
        assertFalse(TypicalBaseIngredients.APPLE.equals(TypicalBaseIngredients.BASIL));

        // different name -> returns false
        BaseIngredient editedAlmond = new IngredientBuilder(TypicalBaseIngredients.APPLE)
                .withName(VALID_NAME_BASIL).buildBaseIngredient();
        assertFalse(TypicalBaseIngredients.APPLE.equals(editedAlmond));

        // different quantity -> returns false
        editedAlmond = new IngredientBuilder(TypicalBaseIngredients.APPLE)
                .withQuantity(VALID_QUANTITY_BASIL).buildBaseIngredient();
        assertFalse(TypicalBaseIngredients.APPLE.equals(editedAlmond));
    }

    @Test
    public void hashCodeTest() {
        BaseIngredient ingredient = new BaseIngredient(new Name("A"), new Quantity("90"));
        BaseIngredient ingredientSame = new BaseIngredient(new Name("A"), new Quantity("90"));
        BaseIngredient ingredientDiff = new BaseIngredient(new Name("B"), new Quantity("45"));

        assertEquals(ingredient.hashCode(), ingredient.hashCode());
        assertEquals(ingredient.hashCode(), ingredientSame.hashCode());
        assertNotEquals(ingredient.hashCode(), ingredientDiff.hashCode());
    }

    @Test
    public void getQuantityDiffTest() {
        Quantity qtyMassUnit = new Quantity("0.009kg"); // should be 9g
        Quantity qtyLiquidUnit = new Quantity("9l"); // 9l
        Quantity noUnit = new Quantity("9"); // 9
        BaseIngredient massIngr = new BaseIngredient(new Name("A"), qtyMassUnit);
        BaseIngredient liquidIngr = new BaseIngredient(new Name("B"), qtyLiquidUnit);
        BaseIngredient noUnitIngr = new BaseIngredient(new Name("C"), noUnit);

        assertEquals(0, massIngr.getQuantityDiff(massIngr));
        assertEquals(0, liquidIngr.getQuantityDiff(liquidIngr));
        assertEquals(0, noUnitIngr.getQuantityDiff(noUnitIngr));
    }

    @Test
    public void isComparableTest() {
        BaseIngredient noUnitIngr = new BaseIngredient(new Name("A"), new Quantity("90"));
        BaseIngredient noUnitIngrSameName = new BaseIngredient(new Name("A"), new Quantity("88"));
        BaseIngredient massUnitIngr = new BaseIngredient(new Name("B"), new Quantity("42g"));
        BaseIngredient massUnitIngrSameName = new BaseIngredient(new Name("B"), new Quantity("69kg"));
        BaseIngredient liquidUnitIngr = new BaseIngredient(new Name("C"), new Quantity("45l"));
        BaseIngredient liquidUnitIngrSameName = new BaseIngredient(new Name("C"), new Quantity("45ml"));


        // No units
        assertTrue(noUnitIngr.isComparable(noUnitIngr));
        assertTrue(noUnitIngr.isComparable(noUnitIngrSameName));
        assertTrue(massUnitIngr.isComparable(massUnitIngr));
        assertTrue(massUnitIngr.isComparable(massUnitIngrSameName));
        assertTrue(liquidUnitIngr.isComparable(liquidUnitIngr));
        assertTrue(liquidUnitIngr.isComparable(liquidUnitIngrSameName));
    }
}
