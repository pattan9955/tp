package fridgy.ui;

import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;
import fridgy.ui.event.ActiveItemChangeEvent;
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

    private Recipe activeRecipe;
    private Ingredient activeIngredient;

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
                ingredientListPanel.changeSelected(activeIngredient);
                recipeListPanel.changeSelected(activeRecipe);
            }
        );
    }

    public void handleTabChange(TabSwitchEvent event) {
        tabPane.getSelectionModel().select(event.getItem().ordinal());
    }

    public void handleActiveRecipe(ActiveItemChangeEvent<Recipe> event) {
        this.activeRecipe = event.getItem();
        this.activeIngredient = null;
        recipeListPanel.changeSelected(activeRecipe);
    }

    public void handleActiveIngredient(ActiveItemChangeEvent<Ingredient> event) {
        this.activeIngredient = event.getItem();
        this.activeRecipe = null;
        ingredientListPanel.changeSelected(activeIngredient);
    }


}
