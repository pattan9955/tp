package fridgy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import fridgy.model.recipe.Recipe;
import fridgy.model.recipe.UniqueRecipeList;
import javafx.collections.ObservableList;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameRecipe comparison)
 */
public class RecipeBook implements ReadOnlyRecipeBook {

    private final UniqueRecipeList recipes;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        recipes = new UniqueRecipeList();
    }

    public RecipeBook() {}

    /**
     * Creates an Inventory using the Recipes in the {@code toBeCopied}
     */
    public RecipeBook(ReadOnlyRecipeBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the recipe list with {@code recipes}.
     * {@code recipes} must not contain duplicate recipes.
     */
    public void setRecipes(List<Recipe> recipes) {
        this.recipes.setRecipes(recipes);
    }

    /**
     * Resets the existing data of this {@code Inventory} with {@code newData}.
     */
    public void resetData(ReadOnlyRecipeBook newData) {
        requireNonNull(newData);

        setRecipes(newData.getRecipeList());
    }

    //// recipe-level operations

    /**
     * Returns true if a recipe with the same identity as {@code recipe} exists in the address book.
     */
    public boolean hasRecipe(Recipe recipe) {
        requireNonNull(recipe);
        return recipes.contains(recipe);
    }

    /**
     * Adds a recipe to the address book.
     * The recipe must not already exist in the address book.
     */
    public void addRecipe(Recipe p) {
        recipes.add(p);
    }

    /**
     * Replaces the given recipe {@code target} in the list with {@code editedRecipe}.
     * {@code target} must exist in the address book.
     * The recipe identity of {@code editedRecipe} must not be the same as another existing recipe in the address book.
     */
    public void setRecipe(Recipe target, Recipe editedRecipe) {
        requireNonNull(editedRecipe);

        recipes.setRecipe(target, editedRecipe);
    }

    /**
     * Removes {@code key} from this {@code Inventory}.
     * {@code key} must exist in the address book.
     */
    public void removeRecipe(Recipe key) {
        recipes.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return recipes.asUnmodifiableObservableList().size() + " recipes";
        // TODO: refine later
    }

    @Override
    public ObservableList<Recipe> getRecipeList() {
        return recipes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeBook // instanceof handles nulls
                && recipes.equals(((RecipeBook) other).recipes));
    }

    @Override
    public int hashCode() {
        return recipes.hashCode();
    }
}
