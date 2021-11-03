package fridgy.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import fridgy.commons.core.GuiSettings;
import fridgy.commons.core.LogsCenter;
import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.logic.parser.CommandExecutor;
import fridgy.logic.parser.FridgyParser;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.Model;
import fridgy.model.base.ReadOnlyDatabase;
import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;
import fridgy.storage.Storage;
import fridgy.ui.UiState;
import javafx.collections.ObservableList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final FridgyParser fridgyParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        fridgyParser = new FridgyParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandExecutor commandExecutor = fridgyParser.parseCommand(commandText);
        CommandResult commandResult = commandExecutor.apply(model);

        try {
            storage.saveInventory(model.getInventory());
            storage.saveRecipeBook(model.getRecipeBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyDatabase<Ingredient> getInventory() {
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

    @Override
    public Boolean isEnough(BaseIngredient ingredient) {
        return model.isEnough(ingredient);
    }

    @Override
    public void setUiState(UiState uiState) {
        this.model.setUiState(uiState);
    }
}
