package fridgy.model;

import fridgy.model.recipe.Recipe;
import javafx.collections.ObservableList;


/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyRecipeBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Recipe> getRecipeList();

}
