package fridgy.model.ingredient;

import static fridgy.logic.commands.CommandTestUtil.VALID_NAME_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_QUANTITY_BASIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.TypicalBaseIngredients;

public class BaseIngredientTest {

    @Test
    public void isSameIngredient() {
        // same object -> returns true
        Assertions.assertTrue(TypicalBaseIngredients.APPLE.isSameIngredient(TypicalBaseIngredients.APPLE));
        Assertions.assertTrue(TypicalBaseIngredients.APPLE.isSame(TypicalBaseIngredients.APPLE));

        // null -> returns false
        Assertions.assertFalse(TypicalBaseIngredients.APPLE.isSameIngredient(null));

        // same name, all other attributes different -> returns true
        BaseIngredient editedAlmond = new IngredientBuilder(TypicalBaseIngredients.APPLE)
                .withQuantity(VALID_QUANTITY_BASIL).buildBaseIngredient();
        Assertions.assertTrue(TypicalBaseIngredients.APPLE.isSameIngredient(editedAlmond));

        // different name, all other attributes same -> returns false
        editedAlmond = new IngredientBuilder(TypicalBaseIngredients.APPLE)
                .withName(VALID_NAME_BASIL).buildBaseIngredient();
        Assertions.assertFalse(TypicalBaseIngredients.APPLE.isSameIngredient(editedAlmond));

        // name differs in case, all other attributes same -> returns false
        BaseIngredient editedBasil = new IngredientBuilder(TypicalBaseIngredients.BASIL)
                .withName(VALID_NAME_BASIL.toLowerCase()).buildBaseIngredient();
        Assertions.assertFalse(TypicalBaseIngredients.BASIL.isSameIngredient(editedBasil));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BASIL + " ";
        editedBasil = new IngredientBuilder(TypicalBaseIngredients.BASIL)
                .withName(nameWithTrailingSpaces).buildBaseIngredient();
        Assertions.assertFalse(TypicalBaseIngredients.BASIL.isSameIngredient(editedBasil));
    }

    @Test
    public void equals() {
        // same values -> returns true
        BaseIngredient almondCopy = new IngredientBuilder(TypicalBaseIngredients.APPLE).buildBaseIngredient();
        Assertions.assertTrue(TypicalBaseIngredients.APPLE.equals(almondCopy));
        Assertions.assertTrue(TypicalBaseIngredients.APPLE.toString().equals(almondCopy.toString()));

        // same object -> returns true
        Assertions.assertTrue(TypicalBaseIngredients.APPLE.equals(TypicalBaseIngredients.APPLE));

        // null -> returns false
        Assertions.assertFalse(TypicalBaseIngredients.APPLE.equals(null));

        // different type -> returns false
        Assertions.assertFalse(TypicalBaseIngredients.APPLE.equals(5));

        // different base ingredient -> returns false
        Assertions.assertFalse(TypicalBaseIngredients.APPLE.equals(TypicalBaseIngredients.BASIL));

        // different name -> returns false
        BaseIngredient editedAlmond = new IngredientBuilder(TypicalBaseIngredients.APPLE)
                .withName(VALID_NAME_BASIL).buildBaseIngredient();
        Assertions.assertFalse(TypicalBaseIngredients.APPLE.equals(editedAlmond));

        // different quantity -> returns false
        editedAlmond = new IngredientBuilder(TypicalBaseIngredients.APPLE)
                .withQuantity(VALID_QUANTITY_BASIL).buildBaseIngredient();
        Assertions.assertFalse(TypicalBaseIngredients.APPLE.equals(editedAlmond));
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
}
