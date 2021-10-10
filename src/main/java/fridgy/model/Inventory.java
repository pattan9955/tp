package fridgy.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import fridgy.model.base.UniqueDataList;
import fridgy.model.base.exceptions.DuplicateItemException;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.IngredientDefaultComparator;
import fridgy.model.ingredient.exceptions.DuplicateIngredientException;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameIngredient comparison)
 */
public class Inventory implements ReadOnlyInventory {

    private final UniqueDataList<Ingredient> ingredients;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        ingredients = new UniqueDataList<>(new IngredientDefaultComparator());
    }

    public Inventory() {}

    /**
     * Creates an Inventory using the Ingredients in the {@code toBeCopied}
     */
    public Inventory(ReadOnlyInventory toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the ingredient list with {@code ingredients}.
     * {@code ingredients} must not contain duplicate ingredients.
     */
    public void setIngredients(List<Ingredient> ingredients) {
        try {
            this.ingredients.replace(ingredients);
        } catch (DuplicateItemException e) {
            // throw a more specific exception
            throw new DuplicateIngredientException();
        }
    }

    /**
     * Resets the existing data of this {@code Inventory} with {@code newData}.
     */
    public void resetData(ReadOnlyInventory newData) {
        requireNonNull(newData);

        setIngredients(newData.getIngredientList());
    }

    //// ingredient-level operations

    /**
     * Returns true if an ingredient with the same identity as {@code ingredient} exists in the Inventory.
     */
    public boolean hasIngredient(Ingredient ingredient) {
        requireNonNull(ingredient);
        return ingredients.contains(ingredient);
    }

    /**
     * Adds an ingredient to the Inventory.
     * The ingredient must not already exist in the Inventory.
     */
    public void addIngredient(Ingredient p) {
        ingredients.add(p);
    }

    /**
     * Replaces the given ingredient {@code target} in the list with {@code editedIngredient}.
     * {@code target} must exist in the Inventory.
     * The ingredient identity of {@code editedIngredient} must not be the same as
     * another existing ingredient in the Inventory.
     */
    public void setIngredient(Ingredient target, Ingredient editedIngredient) {
        requireNonNull(editedIngredient);

        ingredients.set(target, editedIngredient);
    }

    /**
     * Removes {@code key} from this {@code Inventory}.
     * {@code key} must exist in the Inventory.
     */
    public void removeIngredient(Ingredient key) {
        ingredients.remove(key);
    }

    /**
     * Sorts the inventory by expiry dates of ingredients.
     */
    public void sort(Comparator<Ingredient> comparator) {
        ingredients.sort(comparator);
    }

    //// util methods

    @Override
    public String toString() {
        return ingredients.asUnmodifiableObservableList().size() + " ingredients";
        // TODO: refine later
    }

    @Override
    public ObservableList<Ingredient> getIngredientList() {
        return ingredients.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Inventory // instanceof handles nulls
                && ingredients.equals(((Inventory) other).ingredients));
    }

    @Override
    public int hashCode() {
        return ingredients.hashCode();
    }
}
