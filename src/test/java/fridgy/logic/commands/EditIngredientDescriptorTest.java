package fridgy.logic.commands;

import static fridgy.logic.commands.CommandTestUtil.DESC_ALMOND;
import static fridgy.logic.commands.CommandTestUtil.DESC_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_NAME_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_QUANTITY_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_TAG_VEGETABLE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fridgy.logic.commands.ingredient.EditCommand.EditIngredientDescriptor;
import fridgy.testutil.EditIngredientDescriptorBuilder;

public class EditIngredientDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditIngredientDescriptor descriptorWithSameValues = new EditIngredientDescriptor(DESC_ALMOND);
        assertTrue(DESC_ALMOND.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ALMOND.equals(DESC_ALMOND));

        // null -> returns false
        assertFalse(DESC_ALMOND.equals(null));

        // different types -> returns false
        assertFalse(DESC_ALMOND.equals(5));

        // different values -> returns false
        assertFalse(DESC_ALMOND.equals(DESC_BASIL));

        // different name -> returns false
        EditIngredientDescriptor editedAlmond = new EditIngredientDescriptorBuilder(DESC_ALMOND)
                .withName(VALID_NAME_BASIL).build();
        assertFalse(DESC_ALMOND.equals(editedAlmond));

        // different quantity -> returns false
        editedAlmond = new EditIngredientDescriptorBuilder(DESC_ALMOND).withQuantity(VALID_QUANTITY_BASIL).build();
        assertFalse(DESC_ALMOND.equals(editedAlmond));


        // different address -> returns false
        editedAlmond = new EditIngredientDescriptorBuilder(DESC_ALMOND)
                .withDescription(VALID_DESCRIPTION_BASIL).build();
        assertFalse(DESC_ALMOND.equals(editedAlmond));

        // different tags -> returns false
        editedAlmond = new EditIngredientDescriptorBuilder(DESC_ALMOND).withTags(VALID_TAG_VEGETABLE).build();
        assertFalse(DESC_ALMOND.equals(editedAlmond));
    }
}
