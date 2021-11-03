package fridgy.logic.commands.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import fridgy.commons.core.index.Index;
import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.Model;
import fridgy.model.RecipeBook;
import fridgy.model.recipe.Recipe;
import fridgy.testutil.Assert;

public class RecipeCommandTestUtil {
    /**
     * Updates {@code model}'s filtered list to show only the recipe at the given {@code targetIndex} in the
     * {@code model}'s recipe book.
     */
    public static void showRecipeAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredRecipeList().size());

        Recipe recipe = model.getFilteredRecipeList().get(targetIndex.getZeroBased());
        final String[] splitName = recipe.getName().fullName.split("\\s+");
        model.updateFilteredRecipeList((item) -> item.getName().fullName.contains(splitName[0]));

        assertEquals(1, model.getFilteredRecipeList().size());
    }


    /**
     * Executes the given {@code recipeCommand}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertRecipeCommandSuccess(RecipeCommand recipeCommand, Model actualModel,
                                                  String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertRecipeCommandSuccess(recipeCommand, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code recipeCommand}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertRecipeCommandSuccess(RecipeCommand recipeCommand, Model actualModel,
                                                  CommandResult expectedCommandResult, Model expectedModel) {
        try {
            CommandResult result = recipeCommand.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of recipeCommand should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code recipeCommand}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the recipe book, filtered recipe list and selected recipe in {@code actualModel} remain unchanged
     */
    public static void assertRecipeCommandFailure(RecipeCommand recipeCommand, Model actualModel,
                                                  String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        RecipeBook expectedRecipeBook = new RecipeBook(actualModel.getRecipeBook());
        List<Recipe> expectedFilteredList = new ArrayList<>(actualModel.getFilteredRecipeList());

        Assert.assertThrows(CommandException.class, expectedMessage, () -> recipeCommand.execute(actualModel));
        assertEquals(expectedRecipeBook, actualModel.getRecipeBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredRecipeList());
    }
}
