package fridgy.logic.commands.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fridgy.commons.core.index.Index;
import fridgy.model.Model;
import fridgy.model.recipe.Recipe;

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
}
