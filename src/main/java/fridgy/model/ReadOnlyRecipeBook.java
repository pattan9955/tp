package fridgy.model;

import fridgy.model.base.ReadOnlyDatabase;
import fridgy.model.recipe.Recipe;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyRecipeBook extends ReadOnlyDatabase<Recipe> {
    // this interface is here to avoid too many code breaking changes for now
}
