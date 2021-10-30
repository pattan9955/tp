package fridgy.ui;

import java.util.function.Function;
import java.util.logging.Logger;

import fridgy.commons.core.LogsCenter;
import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.recipe.Recipe;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    public RecipeListPanel(ObservableList<Recipe> recipeList, ActiveItemPanel activeItemPanel,
                           Function<BaseIngredient, Boolean> isEnough) {
        super(FXML);
        this.isEnough = isEnough;
        recipeListView.setItems(recipeList);
        recipeListView.setCellFactory(listView -> new RecipeListViewCell());
        recipeListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Recipe>() {
                    public void changed(ObservableValue<? extends Recipe> ov,
                                        Recipe oldVal, Recipe newVal) {
                        activeItemPanel.update(newVal);
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
                setGraphic(new RecipeCard(recipe, getIndex() + 1, isEnough).getRoot());
            }
        }
    }

}
