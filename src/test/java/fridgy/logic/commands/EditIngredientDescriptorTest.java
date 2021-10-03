package fridgy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static fridgy.logic.commands.CommandTestUtil.DESC_AMY;
import static fridgy.logic.commands.CommandTestUtil.DESC_BOB;
import static fridgy.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static fridgy.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static fridgy.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static fridgy.logic.commands.CommandTestUtil.VALID_QUANTITY_BOB;
import static fridgy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import fridgy.testutil.EditIngredientDescriptorBuilder;
import org.junit.jupiter.api.Test;

import fridgy.logic.commands.EditCommand.EditIngredientDescriptor;

public class EditIngredientDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditIngredientDescriptor descriptorWithSameValues = new EditIngredientDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditIngredientDescriptor editedAmy = new EditIngredientDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different quantity -> returns false
        editedAmy = new EditIngredientDescriptorBuilder(DESC_AMY).withQuantity(VALID_QUANTITY_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditIngredientDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditIngredientDescriptorBuilder(DESC_AMY).withDescription(VALID_DESCRIPTION_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditIngredientDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
