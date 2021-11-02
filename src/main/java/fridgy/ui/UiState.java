package fridgy.ui;

import static java.util.Objects.requireNonNull;

import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;

public class UiState {

    private Recipe activeRecipe;
    private Ingredient activeIngredient;
    private final Observer mainWindow;

    public Recipe getActiveRecipe() {
        return activeRecipe;
    }

    public Ingredient getActiveIngredient() {
        return activeIngredient;
    }


    /**
     * Constructs a UI state object that is observable by an Observer.
     * @param mainWindow the Observer
     */
    public UiState(Observer mainWindow) {
        requireNonNull(mainWindow);
        this.mainWindow = mainWindow;
    }

    /**
     * Set a recipe as active, erase current activeIngredient
     */
    public void setActive(Recipe active) {
        requireNonNull(active);
        if (activeRecipe == null || !activeRecipe.equals(active)) {
            this.activeRecipe = active;
            this.activeIngredient = null;
        }
        mainWindow.update(active);
    }

    /**
     * Set an ingredient as active, erase current activeRecipe
     */
    public void setActive(Ingredient active) {
        requireNonNull(active);
        if (activeIngredient == null || !activeIngredient.equals(active)) {
            this.activeIngredient = active;
            this.activeRecipe = null;
        }
        mainWindow.update(active);
    }

    /**
     * Resend {@code ActiveItemChangeEvent}. To be used after a filtered list change as
     * old selected objects are destroyed, this will reselect them.
     */
    public void refreshActive() {
        if (activeIngredient != null) {
            mainWindow.update(activeIngredient);
        } else if (activeRecipe != null) {
            mainWindow.update(activeRecipe);
        }
    }

    /**
     * Switch to a target tab programmatically.
     * @param tab the tab to switch to.
     */
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
