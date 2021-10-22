package fridgy.ui;

import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Panel containing an active {@code Ingredient} or {@code Recipe} for display in the main window.
 */
public class ActiveListPanel extends UiPart<Region> implements Observer {

    private static final String FXML = "ActiveListPanel.fxml";

    @FXML
    private VBox activeBox;

    public ActiveListPanel(Observable activeObservable) {
        super(FXML);
        activeObservable.setObserver(this);
    }

    @Override
    public void update(Ingredient newItem) {
        activeBox.getChildren().clear();
        IngredientCard ingredientCard = new IngredientCard(newItem, 1);
        activeBox.getChildren().add(ingredientCard.getRoot());
    }

    @Override
    public void update(Recipe newItem) {
        activeBox.getChildren().clear();
        RecipeCard recipeCard = new RecipeCard(newItem, 1, true);
        activeBox.getChildren().add(recipeCard.getRoot());
    }
}
