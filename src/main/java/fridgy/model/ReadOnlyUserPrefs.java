package fridgy.model;

import java.nio.file.Path;

import fridgy.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();
    Path getInventoryFilePath();
    Path getRecipeBookFilePath();

}
