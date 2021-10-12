package fridgy.ui;

import java.util.logging.Logger;

import fridgy.commons.core.LogsCenter;
import fridgy.model.recipe.Recipe;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Panel containing the list of recipes.
 */
public class RecipeListPanel extends UiPart<Region> {
    private static final String FXML = "RecipeListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecipeListPanel.class);

    @FXML
    private ListView<Recipe> recipeListView;

    /**
     * Creates a {@code RecipeListPanel} with the given {@code ObservableList}.
     * isDetailed flag determines if the recipe card will show the steps. This is added to allow component reuse.
     */
    public RecipeListPanel(ObservableList<Recipe> recipeList, ScrollPane viewDisplay, VBox displayContainer) {
        super(FXML);
        recipeListView.setItems(recipeList);
        recipeListView.setCellFactory(listView -> new RecipeListViewCell());
        viewDisplay.vvalueProperty().bind(displayContainer.heightProperty());
        viewDisplay.hvalueProperty().bind(displayContainer.widthProperty());
        recipeListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Recipe>() {
                    public void changed(ObservableValue<? extends Recipe> ov,
                                        Recipe old_val, Recipe new_val) {
                        displayContainer.getChildren().clear();
                        displayContainer.getChildren().add(new RecipeDisplay(new_val,
                                recipeList.indexOf(new_val) + 1).getRoot());
                    }
                });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code RecipeIngredient} using a {@code RecipeCard}.
     */
    class RecipeListViewCell extends ListCell<Recipe> {
        @Override
        protected void updateItem(Recipe recipe, boolean empty) {
            super.updateItem(recipe, empty);

            if (empty || recipe == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RecipeCard(recipe, getIndex() + 1).getRoot());
            }
        }
    }

}
