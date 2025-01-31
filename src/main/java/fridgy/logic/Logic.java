package fridgy.logic;

import java.nio.file.Path;

import fridgy.commons.core.GuiSettings;
import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.Model;
import fridgy.model.ReadOnlyInventory;
import fridgy.model.ingredient.Ingredient;
import javafx.collections.ObservableList;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the Inventory.
     *
     * @see Model#getInventory()
     */
    ReadOnlyInventory getInventory();

    /** Returns an unmodifiable view of the filtered list of ingredients */
    ObservableList<Ingredient> getFilteredIngredientList();

    /**
     * Returns the user prefs' Inventory file path.
     */
    Path getInventoryFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
