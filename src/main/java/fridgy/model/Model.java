package fridgy.model;

import fridgy.model.ingredient.BaseIngredient;
import fridgy.ui.Observable;

/**
 * The API of the Model component.
 */
public interface Model extends RecipeModel, IngredientModel {
    /** Returns an {@code Observable} that observes changes to an active {@code Recipe} or {@code Ingredient} */
    Observable getActiveObservable();

    /** Returns if there's enough of an {@code BaseIngredient} in the inventory */
    Boolean isEnough(BaseIngredient ingredient);
}
