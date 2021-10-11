package fridgy.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import fridgy.commons.core.GuiSettings;
import fridgy.commons.core.LogsCenter;
import fridgy.logic.commands.Command;
import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.logic.commands.recipe.RecipeCommand;
import fridgy.logic.parser.InventoryParser;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.logic.parser.recipe.RecipeParser;
import fridgy.model.Model;
import fridgy.model.ReadOnlyInventory;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;
import fridgy.storage.Storage;
import javafx.collections.ObservableList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final InventoryParser addressBookParser;
    private final RecipeParser recipeBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new InventoryParser();
        recipeBookParser = new RecipeParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;

        // This is a hacked together way of supporting both recipe and ingredient from frontend.
        // Ideally this routing should be done in parser / use generic command classes.
        if (commandText.split(" ").length > 1) {
            switch (commandText.split(" ")[1]) {
            case "recipe":
                RecipeCommand recipeCommand = recipeBookParser.parseCommand(commandText);
                commandResult = recipeCommand.execute(model);
                break;
            default:
                Command command = addressBookParser.parseCommand(commandText);
                commandResult = command.execute(model);
            }
        } else {
            Command command = addressBookParser.parseCommand(commandText);
            commandResult = command.execute(model);
        }

        try {
            storage.saveInventory(model.getInventory());
            storage.saveRecipeBook(model.getRecipeBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyInventory getInventory() {
        return model.getInventory();
    }

    @Override
    public ObservableList<Ingredient> getFilteredIngredientList() {
        return model.getFilteredIngredientList();
    }

    @Override
    public ObservableList<Recipe> getFilteredRecipeList() {
        return model.getFilteredRecipeList();
    }

    @Override
    public Path getInventoryFilePath() {
        return model.getInventoryFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
