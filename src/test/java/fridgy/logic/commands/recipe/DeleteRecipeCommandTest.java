package fridgy.logic.commands.recipe;

import static fridgy.logic.commands.recipe.RecipeCommandTestUtil.assertRecipeCommandSuccess;
import static fridgy.testutil.Assert.assertThrows;
import static fridgy.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static fridgy.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static fridgy.testutil.TypicalIndexes.INDEX_THIRD_RECIPE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.model.Inventory;
import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.UserPrefs;
import fridgy.model.recipe.Recipe;
import fridgy.testutil.TypicalRecipes;

public class DeleteRecipeCommandTest {

    private Model model = new ModelManager(new Inventory(), TypicalRecipes.getTypicalRecipeBook(), new UserPrefs());
    // RecipeBook with BURGER, MAGGIE, FRIES

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoRecipe(Model model) {
        model.updateFilteredRecipeList(p -> false);

        assertTrue(model.getFilteredRecipeList().isEmpty());
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteRecipeCommand(null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        DeleteRecipeCommand testCommand = new DeleteRecipeCommand(Index.fromZeroBased(1));
        assertThrows(NullPointerException.class, () -> testCommand.execute(null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Recipe recipeToDelete1 = model.getFilteredRecipeList()
                .get(INDEX_FIRST_RECIPE.getZeroBased());
        Recipe recipeToDelete2 = model.getFilteredRecipeList()
                .get(INDEX_SECOND_RECIPE.getZeroBased());
        Recipe recipeToDelete3 = model.getFilteredRecipeList()
                .get(INDEX_THIRD_RECIPE.getZeroBased());
        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand(INDEX_FIRST_RECIPE,
                INDEX_SECOND_RECIPE, INDEX_THIRD_RECIPE);

        String expectedMessage = String.format(DeleteRecipeCommand.MESSAGE_DELETE_RECIPE_SUCCESS, 3, "s");

        ModelManager expectedModel = new ModelManager(new Inventory(), model.getRecipeBook(), new UserPrefs());
        expectedModel.delete(recipeToDelete1);
        expectedModel.delete(recipeToDelete2);
        expectedModel.delete(recipeToDelete3);

        assertRecipeCommandSuccess(deleteRecipeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand(outOfBoundIndex,
                INDEX_FIRST_RECIPE, INDEX_SECOND_RECIPE, INDEX_THIRD_RECIPE);

        RecipeCommandTestUtil.assertRecipeCommandFailure(deleteRecipeCommand, model,
                Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        RecipeCommandTestUtil.showRecipeAtIndex(model, INDEX_FIRST_RECIPE);

        Recipe recipeToDelete = model.getFilteredRecipeList()
                .get(INDEX_FIRST_RECIPE.getZeroBased());
        DeleteRecipeCommand deleteCommand = new DeleteRecipeCommand(INDEX_FIRST_RECIPE);

        String expectedMessage = String.format(DeleteRecipeCommand.MESSAGE_DELETE_RECIPE_SUCCESS,
                1, "");

        Model expectedModel = new ModelManager(new Inventory(), model.getRecipeBook(), new UserPrefs());
        expectedModel.delete(recipeToDelete);
        showNoRecipe(expectedModel);

        assertRecipeCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        RecipeCommandTestUtil.showRecipeAtIndex(model, INDEX_FIRST_RECIPE);

        Index outOfBoundIndex = INDEX_SECOND_RECIPE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecipeBook().getList().size());

        DeleteRecipeCommand deleteCommand = new DeleteRecipeCommand(outOfBoundIndex);

        RecipeCommandTestUtil.assertRecipeCommandFailure(deleteCommand, model,
                Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noArgs_deleteNothing() {
        /* Trivial test case where nothing is deleted if there are no arguments passed to varargs. Parser should have
          filtered this out. */
        DeleteRecipeCommand deleteRecipeCommand = new DeleteRecipeCommand();
        String expectedMessage = String.format(DeleteRecipeCommand.MESSAGE_DELETE_RECIPE_SUCCESS, 0, "");

        ModelManager expectedModel = new ModelManager(new Inventory(), model.getRecipeBook(), new UserPrefs());

        RecipeCommandTestUtil.assertRecipeCommandSuccess(deleteRecipeCommand, model,
                expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteRecipeCommand deleteRecipeFirstCommand = new DeleteRecipeCommand(INDEX_FIRST_RECIPE,
                INDEX_SECOND_RECIPE);
        DeleteRecipeCommand deleteRecipeSecondCommand = new DeleteRecipeCommand(INDEX_SECOND_RECIPE,
                INDEX_THIRD_RECIPE);

        // same object -> returns true
        assertTrue(deleteRecipeFirstCommand.equals(deleteRecipeFirstCommand));

        // same values -> returns true
        DeleteRecipeCommand deleteFirstCommandCopy = new DeleteRecipeCommand(INDEX_FIRST_RECIPE,
                INDEX_SECOND_RECIPE);
        assertTrue(deleteRecipeFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteRecipeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteRecipeFirstCommand.equals(null));

        // different Ingredient -> returns false
        assertFalse(deleteRecipeFirstCommand.equals(deleteRecipeSecondCommand));
    }

}
