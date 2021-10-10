package fridgy.model;

import static fridgy.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import fridgy.commons.core.GuiSettings;
import fridgy.commons.core.LogsCenter;
import fridgy.model.base.ReadOnlyDatabase;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);


    private final Inventory inventory;
    private final RecipeBook recipeBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Ingredient> filteredIngredients;
    private final FilteredList<Recipe> filteredRecipes;

    /**
     * Initializes a ModelManager with the given inventory and userPrefs.
     */

    public ModelManager(ReadOnlyInventory inventory, ReadOnlyDatabase<Recipe> recipeBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(inventory, userPrefs);

        logger.fine("Initializing with address book: " + inventory
                + " Initializing with recipe book: " + inventory
                + " and user prefs " + userPrefs);

        this.inventory = new Inventory(inventory);
        this.recipeBook = new RecipeBook(recipeBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredIngredients = new FilteredList<>(this.inventory.getIngredientList());
        filteredRecipes = new FilteredList<>(this.recipeBook.getList());
    }

    public ModelManager() {
        this(new Inventory(), new RecipeBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getInventoryFilePath() {
        return userPrefs.getInventoryFilePath();
    }

    @Override
    public void setInventoryFilePath(Path inventoryFilePath) {
        requireNonNull(inventoryFilePath);
        userPrefs.setInventoryFilePath(inventoryFilePath);
    }

    @Override
    public Path getRecipeBookFilePath() {
        return userPrefs.getRecipeBookFilePath();
    }

    @Override
    public void setRecipeBookFilePath(Path recipeBookFilePath) {
        requireNonNull(recipeBookFilePath);
        userPrefs.setRecipeBookFilePath(recipeBookFilePath);
    }

    //=========== Inventory ================================================================================

    @Override
    public void setInventory(ReadOnlyInventory inventory) {
        this.inventory.resetData(inventory);
    }

    @Override
    public ReadOnlyInventory getInventory() {
        return inventory;
    }

    @Override
    public boolean hasIngredient(Ingredient ingredient) {
        requireNonNull(ingredient);
        return inventory.hasIngredient(ingredient);
    }

    @Override
    public void deleteIngredient(Ingredient target) {
        inventory.removeIngredient(target);
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        inventory.addIngredient(ingredient);
        updateFilteredIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
    }

    @Override
    public void setIngredient(Ingredient target, Ingredient editedIngredient) {
        requireAllNonNull(target, editedIngredient);

        inventory.setIngredient(target, editedIngredient);
    }

    //=========== RecipeBook ================================================================================

    @Override
    public void setRecipeBook(ReadOnlyRecipeBook recipeBook) {
        this.recipeBook.resetDatabase(recipeBook);
    }

    @Override
    public ReadOnlyRecipeBook getRecipeBook() {
        return recipeBook;
    }

    @Override
    public boolean hasRecipe(Recipe recipe) {
        requireNonNull(recipe);
        return recipeBook.has(recipe);
    }

    @Override
    public void deleteRecipe(Recipe target) {
        recipeBook.remove(target);
    }

    @Override
    public void addRecipe(Recipe recipe) {
        recipeBook.add(recipe);
        updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
    }

    @Override
    public void setRecipe(Recipe target, Recipe editedRecipe) {
        requireAllNonNull(target, editedRecipe);

        recipeBook.set(target, editedRecipe);
    }

    //=========== Filtered Ingredient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Ingredient} backed by the internal list of
     * {@code versionedInventory}
     */
    @Override
    public ObservableList<Ingredient> getFilteredIngredientList() {
        return filteredIngredients;
    }

    @Override
    public void updateFilteredIngredientList(Predicate<Ingredient> predicate) {
        requireNonNull(predicate);
        filteredIngredients.setPredicate(predicate);
    }

    //=========== Filtered Recipe List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Recipe} backed by the internal list of
     * {@code versionedInventory}
     */
    @Override
    public ObservableList<Recipe> getFilteredRecipeList() {
        return filteredRecipes;
    }

    @Override
    public void updateFilteredRecipeList(Predicate<Recipe> predicate) {
        requireNonNull(predicate);
        filteredRecipes.setPredicate(predicate);
    }

    @Override
    public void sortIngredient(Comparator<Ingredient> comparator) {
        this.inventory.sort(comparator);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;

        return inventory.equals(other.inventory)
                && recipeBook.equals(other.recipeBook)
                && userPrefs.equals(other.userPrefs)
                && filteredIngredients.equals(other.filteredIngredients)
                && filteredRecipes.equals(other.filteredRecipes);
    }

}
