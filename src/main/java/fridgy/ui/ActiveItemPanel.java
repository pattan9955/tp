package fridgy.ui;

import java.util.function.Function;

import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;
import fridgy.ui.event.ActiveItemChangeEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Panel containing an active {@code Ingredient} or {@code Recipe} for display in the main window.
 */
public class ActiveItemPanel extends UiPart<Region> {

    private static final String FXML = "ActiveItemPanel.fxml";
    private final Function<BaseIngredient, Boolean> isEnough;

    @FXML
    private VBox activeBox;

    /**
     * Instantiates a new Active Panel.
     */
    public ActiveItemPanel(Function<BaseIngredient, Boolean> isEnough) {
        super(FXML);
        this.isEnough = isEnough;
    }

    /**
     * Update current active item displayed to an Ingredient.
     */
    public void handleIngredientEvent(ActiveItemChangeEvent<Ingredient> event) {
        Ingredient newItem = event.getItem();
        if (newItem != null) {
            activeBox.getChildren().clear();
            IngredientDisplay ingredientDisplay = new IngredientDisplay(newItem);
            activeBox.getChildren().add(ingredientDisplay.getRoot());
        }
    }

    /**
     * Update current active item displayed to a Recipe.
     */
    public void handleRecipeEvent(ActiveItemChangeEvent<Recipe> event) {
        Recipe newItem = event.getItem();
        if (newItem != null) {
            activeBox.getChildren().clear();
            RecipeDisplay recipeDisplay = new RecipeDisplay(newItem, isEnough);
            activeBox.getChildren().add(recipeDisplay.getRoot());
        }
    }

    /**
     * Clear the active window.
     */
    public void handleClearEvent(ActiveItemChangeEvent<?> unused) {
        activeBox.getChildren().clear();
    }
}
