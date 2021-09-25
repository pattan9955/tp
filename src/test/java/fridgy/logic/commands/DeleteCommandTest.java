package fridgy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static fridgy.logic.commands.CommandTestUtil.assertCommandSuccess;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.UserPrefs;
import fridgy.model.ingredient.Ingredient;
import fridgy.testutil.TypicalIndexes;
import fridgy.testutil.TypicalIngredients;
import org.junit.jupiter.api.Test;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(TypicalIngredients.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Ingredient IngredientToDelete = model.getFilteredIngredientList().get(TypicalIndexes.INDEX_FIRST_INGREDIENT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_INGREDIENT_SUCCESS, IngredientToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteIngredient(IngredientToDelete);

        CommandTestUtil.assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredIngredientList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showIngredientAtIndex(model, TypicalIndexes.INDEX_FIRST_INGREDIENT);

        Ingredient IngredientToDelete = model.getFilteredIngredientList().get(TypicalIndexes.INDEX_FIRST_INGREDIENT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_INGREDIENT_SUCCESS, IngredientToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteIngredient(IngredientToDelete);
        showNoIngredient(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showIngredientAtIndex(model, TypicalIndexes.INDEX_FIRST_INGREDIENT);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_INGREDIENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getIngredientList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT);
        DeleteCommand deleteSecondCommand = new DeleteCommand(TypicalIndexes.INDEX_SECOND_INGREDIENT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different Ingredient -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoIngredient(Model model) {
        model.updateFilteredIngredientList(p -> false);

        assertTrue(model.getFilteredIngredientList().isEmpty());
    }
}
