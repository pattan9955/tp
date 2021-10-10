package fridgy.model;

import fridgy.model.recipe.Recipe;
import javafx.collections.ObservableList;


/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyRecipeBook {

    /**
     * Returns an unmodifiable view of the RecipeBook.
     * This list will not contain any duplicate Recipes.
     */
    ObservableList<Recipe> getRecipeList();

}
