package fridgy.model;

import fridgy.model.ingredient.Ingredient;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an Inventory
 */
public interface ReadOnlyInventory {

    /**
     * Returns an unmodifiable view of the ingredients list.
     * This list will not contain any duplicate ingredients.
     */
    ObservableList<Ingredient> getIngredientList();

}
