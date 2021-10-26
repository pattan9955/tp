package fridgy.ui;

import java.util.function.Function;

import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Panel containing an active {@code Ingredient} or {@code Recipe} for display in the main window.
 */
public class ActiveItemPanel extends UiPart<Region> implements Observer {

    private static final String FXML = "ActiveItemPanel.fxml";
    private final Function<BaseIngredient, Boolean> isEnough;

    @FXML
    private VBox activeBox;

    /**
     * Instantiates a new Active  Panel.
     *
     * @param activeObservable the active observable that notifies the observer of changes
     */
    public ActiveItemPanel(Observable activeObservable, Function<BaseIngredient, Boolean> isEnough) {
        super(FXML);
        activeObservable.setObserver(this);
        this.isEnough = isEnough;
    }

    @Override
    public void update(Ingredient newItem) {
        activeBox.getChildren().clear();
        IngredientDisplay ingredientDisplay = new IngredientDisplay(newItem, 1);
        activeBox.getChildren().add(ingredientDisplay.getRoot());
    }

    @Override
    public void update(Recipe newItem) {
        activeBox.getChildren().clear();
        RecipeDisplay recipeDisplay = new RecipeDisplay(newItem, isEnough);
        activeBox.getChildren().add(recipeDisplay.getRoot());
    }
}
