package fridgy.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import fridgy.commons.core.GuiSettings;
import fridgy.commons.core.LogsCenter;
import fridgy.commons.util.CollectionUtil;
import fridgy.model.ingredient.Ingredient;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the Inventory data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Inventory inventory;
    private final UserPrefs userPrefs;
    private final FilteredList<Ingredient> filteredIngredients;

    /**
     * Initializes a ModelManager with the given inventory and userPrefs.
     */
    public ModelManager(ReadOnlyInventory inventory, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(inventory, userPrefs);

        logger.fine("Initializing with Inventory: " + inventory + " and user prefs " + userPrefs);

        this.inventory = new Inventory(inventory);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredIngredients = new FilteredList<>(this.inventory.getIngredientList());
    }

    public ModelManager() {
        this(new Inventory(), new UserPrefs());
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
        CollectionUtil.requireAllNonNull(target, editedIngredient);

        inventory.setIngredient(target, editedIngredient);
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
                && userPrefs.equals(other.userPrefs)
                && filteredIngredients.equals(other.filteredIngredients);
    }

}
