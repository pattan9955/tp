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

    /**
     * Constructs a default UI state object.
     */
    public UiState() {}

    /**
     * Modify active recipe to editedRecipe if target {@code Recipe} is the same as the current activeRecipe.
     */
    public void modify(Recipe target, Recipe editedRecipe) {
        if (activeRecipe == target) {
            set(editedRecipe);
        }
    }

    /**
     * Modify active ingredient to editedIngredient if target {@code Ingredient}
     * is the same as the current activeIngredient.
     */
    public void modify(Ingredient target, Ingredient editedIngredient) {
        if (activeIngredient == target) {
            set(editedIngredient);
        }
    }

    /**
     * Delete active ingredient if it is the same as the target.
     */
    public void delete(Ingredient target) {
        if (target.equals(activeIngredient)) {
            activeIngredient = null;
            if (mainWindow != null) {
                mainWindow.clearWindow();
            }
        }
    }

    /**
     * Delete active recipe if it is the same as the target.
     */
    public void delete(Recipe target) {
        if (target.equals(activeRecipe)) {
            activeRecipe = null;
            if (mainWindow != null) {
                mainWindow.clearWindow();
            }
        }
    }

    /**
     * Clears active window if active item is from the list that was recently cleared.
     */
    public void clear(TabEnum tab) {
        if (mainWindow != null) {
            if (tab == TabEnum.RECIPE && activeRecipe != null) {
                mainWindow.clearWindow();
                activeRecipe = null;
            } else if (tab == TabEnum.INGREDIENT && activeIngredient != null) {
                mainWindow.clearWindow();
                activeIngredient = null;
            }
        }
    }

    /**
     * Set a recipe as active, erase current activeRecipe.
     */
    private void set(Recipe active) {
        if (activeRecipe == null || !activeRecipe.equals(active)) {
            this.activeRecipe = active;
            this.activeIngredient = null;
        }
    }

    /**
     * Set a recipe as active, erase current activeIngredient.
     */
    private void set(Ingredient active) {
        if (activeIngredient == null || !activeIngredient.equals(active)) {
            this.activeIngredient = active;
            this.activeRecipe = null;
        }
    }

    /**
     * Set a recipe as active, erase current activeRecipe. Inform MainWindow of change.
     */
    public void setActive(Recipe active) {
        requireNonNull(active);
        set(active);
        if (mainWindow != null) {
            mainWindow.update(active);
        }
    }

    /**
     * Set a recipe as active, erase current activeIngredient. Inform MainWindow of change.
     */
    public void setActive(Ingredient active) {
        requireNonNull(active);
        set(active);
        if (mainWindow != null) {
            mainWindow.update(active);
        }
    }

    /**
     * Resend {@code ActiveItemChangeEvent}. To be used after a filtered list change as
     * old selected objects are destroyed, this will reselect them.
     */
    public void refreshActive() {
        if (mainWindow != null) {
            if (activeIngredient != null) {
                mainWindow.update(activeIngredient);
            } else if (activeRecipe != null) {
                mainWindow.update(activeRecipe);
            }
        }
    }

    /**
     * Switch to a target tab programmatically.
     * @param tab the tab to switch to.
     */
    public void switchTab(TabEnum tab) {
        requireNonNull(tab);
        if (mainWindow != null) {
            mainWindow.update(tab);
        }
    }

    public void setMainWindow(Observer mainWindow) {
        this.mainWindow = mainWindow;
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
