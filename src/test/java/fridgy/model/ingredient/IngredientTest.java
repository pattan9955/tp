package fridgy.model.ingredient;

import static fridgy.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_NAME_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_QUANTITY_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fridgy.testutil.Assert;
import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.TypicalIngredients;

public class IngredientTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Ingredient ingredient = new IngredientBuilder().build();
        Assert.assertThrows(UnsupportedOperationException.class, () -> ingredient.getTags().remove(0));
    }

    @Test
    public void isSameIngredient() {
        // same object -> returns true
        Assertions.assertTrue(TypicalIngredients.APPLE.isSameIngredient(TypicalIngredients.APPLE));

        // null -> returns false
        Assertions.assertFalse(TypicalIngredients.APPLE.isSameIngredient(null));

        // same name, all other attributes different -> returns true
        Ingredient editedAlmond = new IngredientBuilder(TypicalIngredients.APPLE)
                .withQuantity(VALID_QUANTITY_BASIL)
                .withDescription(VALID_DESCRIPTION_BASIL).withTags(VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(TypicalIngredients.APPLE.isSameIngredient(editedAlmond));

        // different name, all other attributes same -> returns false
        editedAlmond = new IngredientBuilder(TypicalIngredients.APPLE).withName(VALID_NAME_BASIL).build();
        Assertions.assertFalse(TypicalIngredients.APPLE.isSameIngredient(editedAlmond));

        // name differs in case, all other attributes same -> returns false
        Ingredient editedBasil = new IngredientBuilder(TypicalIngredients.BASIL)
                .withName(VALID_NAME_BASIL.toLowerCase()).build();
        Assertions.assertFalse(TypicalIngredients.BASIL.isSameIngredient(editedBasil));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BASIL + " ";
        editedBasil = new IngredientBuilder(TypicalIngredients.BASIL).withName(nameWithTrailingSpaces).build();
        Assertions.assertFalse(TypicalIngredients.BASIL.isSameIngredient(editedBasil));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Ingredient almondCopy = new IngredientBuilder(TypicalIngredients.APPLE).build();
        Assertions.assertTrue(TypicalIngredients.APPLE.equals(almondCopy));

        // same object -> returns true
        Assertions.assertTrue(TypicalIngredients.APPLE.equals(TypicalIngredients.APPLE));

        // null -> returns false
        Assertions.assertFalse(TypicalIngredients.APPLE.equals(null));

        // different type -> returns false
        Assertions.assertFalse(TypicalIngredients.APPLE.equals(5));

        // different ingredient -> returns false
        Assertions.assertFalse(TypicalIngredients.APPLE.equals(TypicalIngredients.BASIL));

        // different name -> returns false
        Ingredient editedAlmond = new IngredientBuilder(TypicalIngredients.APPLE).withName(VALID_NAME_BASIL).build();
        Assertions.assertFalse(TypicalIngredients.APPLE.equals(editedAlmond));

        // different quantity -> returns false
        editedAlmond = new IngredientBuilder(TypicalIngredients.APPLE).withQuantity(VALID_QUANTITY_BASIL).build();
        Assertions.assertFalse(TypicalIngredients.APPLE.equals(editedAlmond));

        // different description -> returns false
        editedAlmond = new IngredientBuilder(TypicalIngredients.APPLE).withDescription(VALID_DESCRIPTION_BASIL).build();
        Assertions.assertFalse(TypicalIngredients.APPLE.equals(editedAlmond));

        // different tags -> returns false
        editedAlmond = new IngredientBuilder(TypicalIngredients.APPLE).withTags(VALID_TAG_HUSBAND).build();
        Assertions.assertFalse(TypicalIngredients.APPLE.equals(editedAlmond));
    }
}
