package fridgy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fridgy.model.base.Database;
import fridgy.model.base.ReadOnlyDatabase;
import fridgy.model.base.UniqueDataList;
import fridgy.model.base.exceptions.DuplicateItemException;
import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.IngredientDefaultComparator;
import fridgy.model.ingredient.Quantity;
import fridgy.model.ingredient.exceptions.DuplicateIngredientException;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the Inventory level
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
     *
     * @param toBeCopied the to be copied
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


    /**
     * Deduct a set of ingredients from the inventory if possible.
     * Returns true if the deduction is successful, false if otherwise.
     *
     * @param baseIngredients the set of {@code BaseIngredient} to deduct from inventory
     * @return the boolean that indicates the deduction is successful
     */
    public boolean deductIngredients(Set<BaseIngredient> baseIngredients) {
        requireNonNull(baseIngredients);
        for (BaseIngredient ingr : baseIngredients) {
            if (!isDeductible(ingr)) {
                return false;
            }
        }
        ObservableList<Ingredient> inventory = getList();
        for (BaseIngredient ingrToDeduct : baseIngredients) {
            for (Ingredient currIngr : inventory) {
                if (currIngr.getExpiryDate().isExpired()) {
                    continue;
                }
                if (!currIngr.isComparable(ingrToDeduct)) {
                    continue;
                }
                String units = currIngr.getQuantity().getUnits();
                Double remainingQty = currIngr.getQuantityDiff(ingrToDeduct);
                if (remainingQty > 0) {
                    Quantity newQty = new Quantity(remainingQty + units);
                    Ingredient ingrWithNewQty = new Ingredient(currIngr.getName(), newQty, currIngr.getDescription(),
                            currIngr.getTags(), currIngr.getExpiryDate());
                    set(currIngr, ingrWithNewQty);
                    break;
                } else if (remainingQty == 0) {
                    remove(currIngr);
                    break;
                } else {
                    remove(currIngr);
                    Quantity newQty = new Quantity(Math.abs(remainingQty) + units);
                    ingrToDeduct = new BaseIngredient(ingrToDeduct.getName(), newQty);
                }
            }
        }
        return true;
    }

    /**
     * Checks through the inventory to see if the full quantity of the baseIngredient is deductible.
     * Returns true if the full quantity is deductible, false if otherwise.
     *
     * @param baseIngredient to deduct from the inventory
     * @return boolean that indicates if the full quantity of baseIngredient is deductible from the inventory
     */
    protected boolean isDeductible(BaseIngredient baseIngredient) {
        requireNonNull(baseIngredient);
        ObservableList<Ingredient> inventory = getList();
        List<Ingredient> deductibleIngredients = inventory.stream()
                .filter(x -> !x.getExpiryDate().isExpired() && baseIngredient.isComparable(x))
                .collect(Collectors.toList());

        double sum = 0;
        double minQty = baseIngredient.getQuantity().getValue();
        for (Ingredient currIngr : deductibleIngredients) {
            sum += currIngr.getQuantity().getValue();
            if (sum >= minQty) {
                return true;
            }
        }
        return false;
    }

}
