package fridgy.model;

import fridgy.model.ingredient.BaseIngredient;
import fridgy.ui.UiState;

/**
 * The API of the Model component.
 */
public interface Model extends RecipeModel, IngredientModel {
    /** Returns if there's enough of an {@code BaseIngredient} in the inventory */
    Boolean isEnough(BaseIngredient ingredient);

    void setUiState(UiState uiState);
}
