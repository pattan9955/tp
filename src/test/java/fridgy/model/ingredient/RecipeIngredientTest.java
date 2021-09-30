package fridgy.model.ingredient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static fridgy.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static fridgy.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static fridgy.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static fridgy.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static fridgy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static fridgy.testutil.Assert.assertThrows;

import fridgy.model.ingredient.Ingredient;
import fridgy.testutil.Assert;
import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.TypicalIngredients;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RecipeIngredientTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Ingredient ingredient = new IngredientBuilder().build();
        Assert.assertThrows(UnsupportedOperationException.class, () -> ingredient.getTags().remove(0));
    }

    @Test
    public void isSameIngredient() {
        // same object -> returns true
        Assertions.assertTrue(TypicalIngredients.ALICE.isSameIngredient(TypicalIngredients.ALICE));

        // null -> returns false
        Assertions.assertFalse(TypicalIngredients.ALICE.isSameIngredient(null));

        // same name, all other attributes different -> returns true
        Ingredient editedAlice = new IngredientBuilder(TypicalIngredients.ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(TypicalIngredients.ALICE.isSameIngredient(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new IngredientBuilder(TypicalIngredients.ALICE).withName(VALID_NAME_BOB).build();
        Assertions.assertFalse(TypicalIngredients.ALICE.isSameIngredient(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Ingredient editedBob = new IngredientBuilder(TypicalIngredients.BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        Assertions.assertFalse(TypicalIngredients.BOB.isSameIngredient(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new IngredientBuilder(TypicalIngredients.BOB).withName(nameWithTrailingSpaces).build();
        Assertions.assertFalse(TypicalIngredients.BOB.isSameIngredient(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Ingredient aliceCopy = new IngredientBuilder(TypicalIngredients.ALICE).build();
        Assertions.assertTrue(TypicalIngredients.ALICE.equals(aliceCopy));

        // same object -> returns true
        Assertions.assertTrue(TypicalIngredients.ALICE.equals(TypicalIngredients.ALICE));

        // null -> returns false
        Assertions.assertFalse(TypicalIngredients.ALICE.equals(null));

        // different type -> returns false
        Assertions.assertFalse(TypicalIngredients.ALICE.equals(5));

        // different ingredient -> returns false
        Assertions.assertFalse(TypicalIngredients.ALICE.equals(TypicalIngredients.BOB));

        // different name -> returns false
        Ingredient editedAlice = new IngredientBuilder(TypicalIngredients.ALICE).withName(VALID_NAME_BOB).build();
        Assertions.assertFalse(TypicalIngredients.ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new IngredientBuilder(TypicalIngredients.ALICE).withPhone(VALID_PHONE_BOB).build();
        Assertions.assertFalse(TypicalIngredients.ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new IngredientBuilder(TypicalIngredients.ALICE).withEmail(VALID_EMAIL_BOB).build();
        Assertions.assertFalse(TypicalIngredients.ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new IngredientBuilder(TypicalIngredients.ALICE).withAddress(VALID_ADDRESS_BOB).build();
        Assertions.assertFalse(TypicalIngredients.ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new IngredientBuilder(TypicalIngredients.ALICE).withTags(VALID_TAG_HUSBAND).build();
        Assertions.assertFalse(TypicalIngredients.ALICE.equals(editedAlice));
    }
}
