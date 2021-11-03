package fridgy.logic.commands;

import static fridgy.testutil.TypicalIndexes.INDEX_FIRST_INGREDIENT;
import static fridgy.testutil.TypicalIndexes.INDEX_SECOND_INGREDIENT;
import static fridgy.testutil.TypicalIndexes.INDEX_THIRD_INGREDIENT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.RecipeBook;
import fridgy.model.UserPrefs;
import fridgy.model.ingredient.Ingredient;
import fridgy.testutil.TypicalIndexes;
import fridgy.testutil.TypicalIngredients;

public class MultiDeleteCommandTest {

    private Model model = new ModelManager(TypicalIngredients.getTypicalInventory(), new RecipeBook(), new UserPrefs());
    // Inventory with APPLE, BANANA, CARROT, DUCK, EGG, FIGS, GRAPES

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoIngredient(Model model) {
        model.updateFilteredIngredientList(p -> false);

        assertTrue(model.getFilteredIngredientList().isEmpty());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Ingredient ingredientToDelete1 = model.getFilteredIngredientList()
                .get(INDEX_FIRST_INGREDIENT.getZeroBased());
        Ingredient ingredientToDelete2 = model.getFilteredIngredientList()
                .get(INDEX_SECOND_INGREDIENT.getZeroBased());
        Ingredient ingredientToDelete3 = model.getFilteredIngredientList()
                .get(INDEX_THIRD_INGREDIENT.getZeroBased());
        MultiDeleteCommand multiDeleteCommand = new MultiDeleteCommand(INDEX_FIRST_INGREDIENT,
                INDEX_SECOND_INGREDIENT, INDEX_THIRD_INGREDIENT);

        String expectedMessage = String.format(MultiDeleteCommand.MESSAGE_MULTIDELETE_INGREDIENT_SUCCESS, 3);

        ModelManager expectedModel = new ModelManager(model.getInventory(), new RecipeBook(), new UserPrefs());
        expectedModel.delete(ingredientToDelete1);
        expectedModel.delete(ingredientToDelete2);
        expectedModel.delete(ingredientToDelete3);

        CommandTestUtil.assertCommandSuccess(multiDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredIngredientList().size() + 1);
        MultiDeleteCommand multiDeleteCommand = new MultiDeleteCommand(outOfBoundIndex, INDEX_FIRST_INGREDIENT,
                INDEX_SECOND_INGREDIENT, INDEX_THIRD_INGREDIENT);

        CommandTestUtil.assertCommandFailure(multiDeleteCommand, model,
                Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showIngredientAtIndex(model, INDEX_FIRST_INGREDIENT);

        Ingredient ingredientToDelete = model.getFilteredIngredientList()
                .get(INDEX_FIRST_INGREDIENT.getZeroBased());
        MultiDeleteCommand deleteCommand = new MultiDeleteCommand(INDEX_FIRST_INGREDIENT);

        String expectedMessage = String.format(MultiDeleteCommand.MESSAGE_MULTIDELETE_INGREDIENT_SUCCESS,
                1);

        Model expectedModel = new ModelManager(model.getInventory(), new RecipeBook(), new UserPrefs());
        expectedModel.delete(ingredientToDelete);
        showNoIngredient(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showIngredientAtIndex(model, TypicalIndexes.INDEX_FIRST_INGREDIENT);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_INGREDIENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInventory().getList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noArgs_deleteNothing() {
        /* Trivial test case where nothing is deleted if there are no arguments passed to varargs. Parser should have
          filtered this out. */
        MultiDeleteCommand multiDeleteCommand = new MultiDeleteCommand();
        String expectedMessage = String.format(MultiDeleteCommand.MESSAGE_MULTIDELETE_INGREDIENT_SUCCESS, 0);

        ModelManager expectedModel = new ModelManager(model.getInventory(), new RecipeBook(), new UserPrefs());

        CommandTestUtil.assertCommandSuccess(multiDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        MultiDeleteCommand multiDeleteFirstCommand = new MultiDeleteCommand(INDEX_FIRST_INGREDIENT,
                INDEX_SECOND_INGREDIENT);
        MultiDeleteCommand multiDeleteSecondCommand = new MultiDeleteCommand(INDEX_SECOND_INGREDIENT,
                INDEX_THIRD_INGREDIENT);

        // same object -> returns true
        assertTrue(multiDeleteFirstCommand.equals(multiDeleteFirstCommand));

        // same values -> returns true
        MultiDeleteCommand multiDeleteFirstCommandCopy = new MultiDeleteCommand(INDEX_FIRST_INGREDIENT,
                INDEX_SECOND_INGREDIENT);
        assertTrue(multiDeleteFirstCommand.equals(multiDeleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(multiDeleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(multiDeleteFirstCommand.equals(null));

        // different Ingredient -> returns false
        assertFalse(multiDeleteFirstCommand.equals(multiDeleteSecondCommand));
    }

}
