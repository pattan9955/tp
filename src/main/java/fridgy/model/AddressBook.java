package fridgy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.UniqueIngredientList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameIngredient comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueIngredientList ingredients;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        ingredients = new UniqueIngredientList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Ingredients in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the ingredient list with {@code ingredients}.
     * {@code ingredients} must not contain duplicate ingredients.
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients.setIngredients(ingredients);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setIngredients(newData.getIngredientList());
    }

    //// ingredient-level operations

    /**
     * Returns true if a ingredient with the same identity as {@code ingredient} exists in the address book.
     */
    public boolean hasIngredient(Ingredient ingredient) {
        requireNonNull(ingredient);
        return ingredients.contains(ingredient);
    }

    /**
     * Adds a ingredient to the address book.
     * The ingredient must not already exist in the address book.
     */
    public void addIngredient(Ingredient p) {
        ingredients.add(p);
    }

    /**
     * Replaces the given ingredient {@code target} in the list with {@code editedIngredient}.
     * {@code target} must exist in the address book.
     * The ingredient identity of {@code editedIngredient} must not be the same as another existing ingredient in the address book.
     */
    public void setIngredient(Ingredient target, Ingredient editedIngredient) {
        requireNonNull(editedIngredient);

        ingredients.setIngredient(target, editedIngredient);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeIngredient(Ingredient key) {
        ingredients.remove(key);
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
                || (other instanceof AddressBook // instanceof handles nulls
                && ingredients.equals(((AddressBook) other).ingredients));
    }

    @Override
    public int hashCode() {
        return ingredients.hashCode();
    }
}
