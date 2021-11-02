package fridgy.ui;

import java.util.logging.Logger;

import fridgy.commons.core.LogsCenter;
import fridgy.model.ingredient.Ingredient;
import fridgy.ui.event.ActiveItemChangeEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of ingredients.
 */
public class IngredientListPanel extends UiPart<Region> {
    private static final String FXML = "IngredientListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(IngredientListPanel.class);

    @FXML
    private ListView<Ingredient> ingredientListView;

    /**
     * Creates a {@code IngredientListPanel} with the given {@code ObservableList}.
     */
    public IngredientListPanel(ObservableList<Ingredient> ingredientList, ActiveItemPanel activeItemPanel) {
        super(FXML);
        ingredientListView.setItems(ingredientList);
        ingredientListView.setCellFactory(listView -> new IngredientListViewCell());
        ingredientListView.setOnMouseClicked(
            event -> {
                ingredientListView.fireEvent(
                    new ActiveItemChangeEvent<>(ActiveItemChangeEvent.INGREDIENT,
                        ingredientListView.getSelectionModel().getSelectedItem())
                );
            }
        );
    }

    public void changeSelected(Ingredient to) {
        if (to != null) {
            ingredientListView.getSelectionModel().select(to);
        }
    }

    public void clearSelection() {
        ingredientListView.getSelectionModel().clearSelection();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code BaseIngredient} using a {@code IngredientCard}.
     */
    class IngredientListViewCell extends ListCell<Ingredient> {
        @Override
        protected void updateItem(Ingredient ingredient, boolean empty) {
            super.updateItem(ingredient, empty);

            if (empty || ingredient == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new IngredientCard(ingredient, getIndex() + 1).getRoot());
            }
        }
    }


}
