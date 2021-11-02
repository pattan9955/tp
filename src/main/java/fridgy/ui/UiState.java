package fridgy.ui;

import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;

public class UiState {

    private Recipe activeRecipe;
    private Ingredient activeIngredient;
    private TabEnum activeTab;
    private Observer mainWindow;

    public UiState(Observer mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void setActive(Recipe active) {
        if (activeRecipe == null || !activeRecipe.equals(active)) {
            this.activeRecipe = active;
            this.activeIngredient = null;
            mainWindow.update(active);
        }
    }

    public void setActive(Ingredient active) {
        if (activeIngredient == null || !activeIngredient.equals(active)) {
            this.activeIngredient = active;
            this.activeRecipe = null;
            mainWindow.update(active);
        }
    }


}
