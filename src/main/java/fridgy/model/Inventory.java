package fridgy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

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
                if (!matchBaseIngredients(currIngr, ingrToDeduct)) {
                    continue;
                }
                String units = getUnitsFromIngr(currIngr);
                Double remainingQty = compareIngrQuantitiesOfSameUnit(currIngr, ingrToDeduct);
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
    private boolean isDeductible(BaseIngredient baseIngredient) {
        ObservableList<Ingredient> inventory = getList();
        for (Ingredient currIngr : inventory) {
            String units = getUnitsFromIngr(currIngr);
            if (currIngr.getExpiryDate().isExpired()) {
                continue;
            }
            if (!matchBaseIngredients(currIngr, baseIngredient)) {
                continue;
            }
            double comparedResult = compareIngrQuantitiesOfSameUnit(currIngr, baseIngredient);
            if (comparedResult >= 0) {
                return true;
            } else {
                Quantity newQty = new Quantity(Math.abs(comparedResult) + units);
                baseIngredient = new BaseIngredient(currIngr.getName(), newQty);
            }
        }
        return false;
    }

    /**
     * Matches 2 {@code BaseIngredient} to see if they match in {@code Name} and {@code Quantity} units.
     * {@code Name} is matched ignoring case.
     * Returns true if they are matched, false if they do not match.
     *
     * @param ingredient1 to be compared
     * @param ingredient2 to be compared
     * @return boolean that indicates if the two ingredients have the same name and quantity units
     */
    private boolean matchBaseIngredients(BaseIngredient ingredient1, BaseIngredient ingredient2) {
        requireNonNull(ingredient1);
        requireNonNull(ingredient2);
        // check name of ingredients ignoring case
        if (!ingredient1.getName().toString().equalsIgnoreCase(ingredient2.getName().toString())) {
            return false;
        }
        // check units of quantity
        String ingr1Units = getUnitsFromIngr(ingredient1);
        String ingr2Units = getUnitsFromIngr(ingredient2);
        return ingr1Units.equals(ingr2Units);
    }

    /**
     * Deducts quantity of ingredient2 from ingredient1.
     * Returns a {@code double}, regardless of units of {@code Quantity}.
     *
     * @param ingredient1
     * @param ingredient2
     * @return double (quantity of ingredient1 - quantity of ingredient2)
     */
    private double compareIngrQuantitiesOfSameUnit(BaseIngredient ingredient1, BaseIngredient ingredient2) {
        requireNonNull(ingredient1);
        requireNonNull(ingredient2);
        Double qty1 = getQtyValueFromIngr(ingredient1);
        Double qty2 = getQtyValueFromIngr(ingredient2);
        return qty1 - qty2;

    }

    /**
     * Gets units of the quantity of a {@code BaseIngredient}
     * @param ingr ingredient to be checked
     * @return String representation of the units
     */
    private String getUnitsFromIngr(BaseIngredient ingr) {
        requireNonNull(ingr);
        String[] valueAndUnit = ingr.getQuantity().toString().split("\\h");
        return valueAndUnit.length <= 1 ? "" : valueAndUnit[1];
    }

    /**
     * Gets units of the quantity of a {@code BaseIngredient}
     * @param ingr ingredient to be checked
     * @return String representation of the units
     */
    private Double getQtyValueFromIngr(BaseIngredient ingr) {
        requireNonNull(ingr);
        return Double.parseDouble(ingr.getQuantity().toString().split("\\h")[0]);
    }
}
