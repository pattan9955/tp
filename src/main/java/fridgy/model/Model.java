package fridgy.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import fridgy.commons.core.GuiSettings;
import fridgy.model.ingredient.Ingredient;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Ingredient> PREDICATE_SHOW_ALL_INGREDIENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' Inventory file path.
     */
    Path getInventoryFilePath();

    /**
     * Sets the user prefs' Inventory file path.
     */
    void setInventoryFilePath(Path addressBookFilePath);

    /**
     * Replaces Inventory data with the data in {@code addressBook}.
     */
    void setInventory(ReadOnlyInventory addressBook);

    /** Returns the Inventory */
    ReadOnlyInventory getInventory();

    /**
     * Returns true if an ingredient with the same identity as {@code ingredient} exists in the Inventory.
     * @param ingredient
     */
    boolean hasIngredient(Ingredient ingredient);

    /**
     * Deletes the given ingredient.
     * The ingredient must exist in the Inventory.
     */
    void deleteIngredient(Ingredient target);

    /**
     * Adds the given ingredient.
     * {@code ingredient} must not already exist in the Inventory.
     * @param ingredient
     */
    void addIngredient(Ingredient ingredient);

    /**
     * Sorts the inventory of ingredients using the specified comparator.
     */
    void sortIngredient(Comparator<Ingredient> comparator);

    /**
     * Replaces the given ingredient {@code target} with {@code editedIngredient}.
     * {@code target} must exist in the Inventory.
     * The ingredient identity of {@code editedIngredient} must not be the same as
     * another existing ingredient in the Inventory.
     */
    void setIngredient(Ingredient target, Ingredient editedIngredient);

    /** Returns an unmodifiable view of the filtered ingredient list */
    ObservableList<Ingredient> getFilteredIngredientList();

    /**
     * Updates the filter of the filtered ingredient list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredIngredientList(Predicate<Ingredient> predicate);
}
