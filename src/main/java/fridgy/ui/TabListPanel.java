package fridgy.ui;

import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;
import fridgy.ui.event.TabSwitchEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class TabListPanel extends UiPart<Region> {
    private static final String FXML = "TabListPanel.fxml";


    @FXML
    private StackPane ingredientListPanelPlaceholder;

    @FXML
    private StackPane recipeListPanelPlaceholder;

    @FXML
    private TabPane tabPane;

    private RecipeListPanel recipeListPanel;
    private IngredientListPanel ingredientListPanel;

    /**
     * Creates a {@code TabListPanel} with the given {@code IngredientListPanel} and {@code RecipeListPanel}.
     */
    public TabListPanel(IngredientListPanel ingredientListPanel, RecipeListPanel recipeListPanel) {
        super(FXML);
        ingredientListPanelPlaceholder.getChildren().add(ingredientListPanel.getRoot());
        recipeListPanelPlaceholder.getChildren().add(recipeListPanel.getRoot());

        this.ingredientListPanel = ingredientListPanel;
        this.recipeListPanel = recipeListPanel;

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                ingredientListPanel.clearSelection();
                recipeListPanel.clearSelection();
            }
        );
    }

    public void handleIngredientTabSwitchEvent(TabSwitchEvent<Ingredient> event) {
        tabPane.getSelectionModel().select(TabEnum.INGREDIENT.ordinal());
        ingredientListPanel.changeSelected(event.getItem());
    }

    public void handleRecipeTabSwitchEvent(TabSwitchEvent<Recipe> event) {
        tabPane.getSelectionModel().select(TabEnum.RECIPE.ordinal());
        recipeListPanel.changeSelected(event.getItem());
    }
}
