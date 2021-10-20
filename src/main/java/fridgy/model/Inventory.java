package fridgy.model;

import java.util.List;

import fridgy.model.base.Database;
import fridgy.model.base.ReadOnlyDatabase;
import fridgy.model.base.UniqueDataList;
import fridgy.model.base.exceptions.DuplicateItemException;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.IngredientDefaultComparator;
import fridgy.model.ingredient.exceptions.DuplicateIngredientException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameIngredient comparison)
 */
public class Inventory extends Database<Ingredient> {
    /**
     * Creates a default Inventory with default comparator.
     */
    public Inventory() {
        super(new UniqueDataList<>(new IngredientDefaultComparator()));
    }

    /**
     * Creates an Inventory using the Ingredients in the {@code toBeCopied}
     */
    public Inventory(ReadOnlyDatabase<Ingredient> toBeCopied) {
        super(toBeCopied);
    }

    @Override
    public void setItems(List<Ingredient> items) {
        try {
            super.setItems(items);
        } catch (DuplicateItemException e) {
            // throw a more specific exception
            throw new DuplicateIngredientException();
        }
    }
}
