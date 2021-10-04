package fridgy.model.ingredient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static fridgy.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_EMAIL_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_NAME_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_QUANTITY_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static fridgy.testutil.Assert.assertThrows;

import fridgy.testutil.Assert;
import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.TypicalIngredients;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        Ingredient editedAlice = new IngredientBuilder(TypicalIngredients.APPLE).withQuantity(VALID_QUANTITY_BASIL).withEmail(VALID_EMAIL_BASIL)
                .withDescription(VALID_DESCRIPTION_BASIL).withTags(VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(TypicalIngredients.APPLE.isSameIngredient(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new IngredientBuilder(TypicalIngredients.APPLE).withName(VALID_NAME_BASIL).build();
        Assertions.assertFalse(TypicalIngredients.APPLE.isSameIngredient(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Ingredient editedBasil = new IngredientBuilder(TypicalIngredients.BASIL).withName(VALID_NAME_BASIL.toLowerCase()).build();
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
        Ingredient editedAlice = new IngredientBuilder(TypicalIngredients.APPLE).withName(VALID_NAME_BASIL).build();
        Assertions.assertFalse(TypicalIngredients.APPLE.equals(editedAlice));

        // different quantity -> returns false
        editedAlice = new IngredientBuilder(TypicalIngredients.APPLE).withQuantity(VALID_QUANTITY_BASIL).build();
        Assertions.assertFalse(TypicalIngredients.APPLE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new IngredientBuilder(TypicalIngredients.APPLE).withEmail(VALID_EMAIL_BASIL).build();
        Assertions.assertFalse(TypicalIngredients.APPLE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new IngredientBuilder(TypicalIngredients.APPLE).withDescription(VALID_DESCRIPTION_BASIL).build();
        Assertions.assertFalse(TypicalIngredients.APPLE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new IngredientBuilder(TypicalIngredients.APPLE).withTags(VALID_TAG_HUSBAND).build();
        Assertions.assertFalse(TypicalIngredients.APPLE.equals(editedAlice));
    }
}
