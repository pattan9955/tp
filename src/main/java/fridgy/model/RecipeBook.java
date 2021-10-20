package fridgy.model;

import fridgy.model.base.Database;
import fridgy.model.base.ReadOnlyDatabase;
import fridgy.model.base.UniqueDataList;
import fridgy.model.recipe.Recipe;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameRecipe comparison)
 */
public class RecipeBook extends Database<Recipe> {
    // Implement any operations on recipe beyond CRUD here.
    public RecipeBook() {
        super(new UniqueDataList<>());
    }
    public RecipeBook(ReadOnlyDatabase<Recipe> roBook) {
        super(roBook);
    }
}
