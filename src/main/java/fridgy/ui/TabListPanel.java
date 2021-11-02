package fridgy.ui;


import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class TabListPanel extends UiPart<Region> {
    private static final String FXML = "TabListPanel.fxml";

    @FXML
    private StackPane ingredientListPanelPlaceholder;

    @FXML
    private StackPane recipeListPanelPlaceholder;

    public TabListPanel(IngredientListPanel ingredientListPanel, RecipeListPanel recipeListPanel) {
        super(FXML);
        ingredientListPanelPlaceholder.getChildren().add(ingredientListPanel.getRoot());
        recipeListPanelPlaceholder.getChildren().add(recipeListPanel.getRoot());
    }
}
