package fridgy.ui;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

import fridgy.commons.core.LogsCenter;
import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.recipe.Recipe;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of recipes.
 */
public class RecipeListPanel extends UiPart<Region> {
    private static final String FXML = "RecipeListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecipeListPanel.class);

    private final Function<BaseIngredient, Boolean> isEnough;

    @FXML
    private ListView<Recipe> recipeListView;

    /**
     * Creates a {@code RecipeListPanel} with the given {@code ObservableList}.
     * isDetailed flag determines if the recipe card will show the steps. This is added to allow component reuse.
     */
    public RecipeListPanel(ObservableList<Recipe> recipeList,
                           Consumer<Recipe> changeActive,
                           Function<BaseIngredient, Boolean> isEnough) {
        super(FXML);
        this.isEnough = isEnough;
        recipeListView.setItems(recipeList);
        recipeListView.setCellFactory(listView -> new RecipeListViewCell());
        recipeListView.setOnMouseClicked(
            event -> {
                Recipe clicked = recipeListView.getSelectionModel().getSelectedItem();
                if (clicked != null) {
                    changeActive.accept(clicked);
                }
            }
        );
    }

    /**
     * Change the selected item to a target {@code Recipe}.
     */
    public void changeSelected(Recipe to) {
        if (to != null) {
            recipeListView.getSelectionModel().select(to);
        }
    }

    public void clearSelection() {
        recipeListView.getSelectionModel().clearSelection();
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
                setGraphic(new RecipeCard(recipe, getIndex() + 1, isEnough).getRoot());
            }
        }
    }

}
