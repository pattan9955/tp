package fridgy.ui;

import static java.util.Objects.requireNonNull;

import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;

public class UiState {

    private Recipe activeRecipe;
    private Ingredient activeIngredient;
    private Observer mainWindow;

    /**
     * Constructs a UI state object that is observable by an Observer.
     * @param mainWindow the Observer
     */
    public UiState(Observer mainWindow) {
        requireNonNull(mainWindow);
        this.mainWindow = mainWindow;
    }

    public void setActive(Recipe active) {
        requireNonNull(active);
        if (activeRecipe == null || !activeRecipe.equals(active)) {
            this.activeRecipe = active;
            this.activeIngredient = null;
        }
        mainWindow.update(active);
    }

    public void setActive(Ingredient active) {
        requireNonNull(active);
        if (activeIngredient == null || !activeIngredient.equals(active)) {
            this.activeIngredient = active;
            this.activeRecipe = null;
        }
        mainWindow.update(active);
    }

    public void switchTab(TabEnum tab) {
        requireNonNull(tab);
        mainWindow.update(tab);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof UiState) {
            UiState o = (UiState) other;
            return o.activeIngredient == this.activeIngredient
                && o.activeRecipe == this.activeRecipe
                && o.mainWindow.equals(this.mainWindow);
        }
        return false;
    }

}
