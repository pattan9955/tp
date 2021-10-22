package fridgy.model;

import fridgy.ui.Observable;

/**
 * The API of the Model component.
 */
public interface Model extends RecipeModel, IngredientModel {
    /** Returns an {@code Observable} that observes changes to an active {@code Recipe} or {@code Ingredient} */
    Observable getActiveObservable();
}
