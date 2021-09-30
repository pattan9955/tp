package fridgy.model;

import static fridgy.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import fridgy.commons.core.GuiSettings;
import fridgy.commons.core.LogsCenter;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Inventory addressBook;
    private final RecipeBook recipeBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Ingredient> filteredIngredients;
    private final FilteredList<Recipe> filteredRecipes;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyInventory addressBook, ReadOnlyRecipeBook recipeBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook
                + " Initializing with recipe book: " + addressBook
                + " and user prefs " + userPrefs);

        this.addressBook = new Inventory(addressBook);
        this.recipeBook = new RecipeBook(recipeBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredIngredients = new FilteredList<>(this.addressBook.getIngredientList());
        filteredRecipes = new FilteredList<>(this.recipeBook.getRecipeList());
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
    public void setInventoryFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setInventoryFilePath(addressBookFilePath);
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
    public void setInventory(ReadOnlyInventory addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyInventory getInventory() {
        return addressBook;
    }

    @Override
    public boolean hasIngredient(Ingredient person) {
        requireNonNull(person);
        return addressBook.hasIngredient(person);
    }

    @Override
    public void deleteIngredient(Ingredient target) {
        addressBook.removeIngredient(target);
    }

    @Override
    public void addIngredient(Ingredient person) {
        addressBook.addIngredient(person);
        updateFilteredIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
    }

    @Override
    public void setIngredient(Ingredient target, Ingredient editedIngredient) {
        requireAllNonNull(target, editedIngredient);

        addressBook.setIngredient(target, editedIngredient);
    }

    //=========== RecipeBook ================================================================================

    @Override
    public void setRecipeBook(ReadOnlyRecipeBook recipeBook) {
        this.recipeBook.resetData(recipeBook);
    }

    @Override
    public ReadOnlyRecipeBook getRecipeBook() {
        return recipeBook;
    }

    @Override
    public boolean hasRecipe(Recipe recipe) {
        requireNonNull(recipe);
        return recipeBook.hasRecipe(recipe);
    }

    @Override
    public void deleteRecipe(Recipe target) {
        recipeBook.removeRecipe(target);
    }

    @Override
    public void addRecipe(Recipe recipe) {
        recipeBook.addRecipe(recipe);
        updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
    }

    @Override
    public void setRecipe(Recipe target, Recipe editedRecipe) {
        requireAllNonNull(target, editedRecipe);

        recipeBook.setRecipe(target, editedRecipe);
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
        return addressBook.equals(other.addressBook)
                && recipeBook.equals(other.recipeBook)
                && userPrefs.equals(other.userPrefs)
                && filteredIngredients.equals(other.filteredIngredients)
                && filteredRecipes.equals(other.filteredRecipes);
    }

}
