package fridgy.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import fridgy.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path inventoryFilePath = Paths.get("data" , "inventory.json");
    private Path recipeBookFilePath = Paths.get("data" , "recipebook.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setInventoryFilePath(newUserPrefs.getInventoryFilePath());
        setRecipeBookFilePath(newUserPrefs.getRecipeBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getInventoryFilePath() {
        return inventoryFilePath;
    }
    public Path getRecipeBookFilePath() {
        return recipeBookFilePath;
    }

    public void setInventoryFilePath(Path inventoryFilePath) {
        requireNonNull(inventoryFilePath);
        this.inventoryFilePath = inventoryFilePath;
    }

    public void setRecipeBookFilePath(Path recipeBookFilePath) {
        requireNonNull(recipeBookFilePath);
        this.recipeBookFilePath = recipeBookFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && inventoryFilePath.equals(o.inventoryFilePath)
                && recipeBookFilePath.equals(o.recipeBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, inventoryFilePath, recipeBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal address data file location : " + inventoryFilePath);
        sb.append("\nLocal recipe data file location : " + recipeBookFilePath);
        return sb.toString();
    }

}
