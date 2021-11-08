package fridgy;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import fridgy.commons.core.Config;
import fridgy.commons.core.LogsCenter;
import fridgy.commons.core.Version;
import fridgy.commons.exceptions.DataConversionException;
import fridgy.commons.util.ConfigUtil;
import fridgy.commons.util.StringUtil;
import fridgy.logic.Logic;
import fridgy.logic.LogicManager;
import fridgy.model.Inventory;
import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.ReadOnlyUserPrefs;
import fridgy.model.RecipeBook;
import fridgy.model.UserPrefs;
import fridgy.model.base.ReadOnlyDatabase;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;
import fridgy.model.util.SampleDataUtil;
import fridgy.storage.InventoryStorage;
import fridgy.storage.JsonInventoryStorage;
import fridgy.storage.JsonRecipeBookStorage;
import fridgy.storage.JsonUserPrefsStorage;
import fridgy.storage.RecipeBookStorage;
import fridgy.storage.Storage;
import fridgy.storage.StorageManager;
import fridgy.storage.UserPrefsStorage;
import fridgy.ui.Ui;
import fridgy.ui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Fridgy ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        InventoryStorage inventoryStorage = new JsonInventoryStorage(userPrefs.getInventoryFilePath());
        RecipeBookStorage recipeBookStorage = new JsonRecipeBookStorage(userPrefs.getRecipeBookFilePath());
        storage = new StorageManager(inventoryStorage, recipeBookStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**

     * Returns a {@code ModelManager} with the data from {@code storage}'s Inventory, recipe book
     * and {@code userPrefs}. <br>
     * The data from the sample Inventory will be used instead if {@code storage}'s Inventory is not found,
     * or an empty Inventory will be used instead if errors occur when reading {@code storage}'s Inventory.
     * The data from the sample recipe book will be used instead if {@code storage}'s recipe book is not found,
     * or an empty recipe book will be used instead if errors occur when reading {@code storage}'s recipe book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyDatabase<Ingredient>> inventoryOptional;
        ReadOnlyDatabase<Ingredient> initialInventory;
        try {
            inventoryOptional = storage.readInventory();
            if (!inventoryOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Inventory");
            }

            initialInventory = inventoryOptional.orElseGet(SampleDataUtil::getSampleInventory);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Inventory");
            initialInventory = new Inventory();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Inventory");
            initialInventory = new Inventory();
        }

        Optional<ReadOnlyDatabase<Recipe>> recipeBookOptional;
        ReadOnlyDatabase<Recipe> initialRecipeBook;
        try {
            recipeBookOptional = storage.readRecipeBook();
            if (!recipeBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Recipe Book");
            }
            initialRecipeBook = recipeBookOptional.orElseGet(SampleDataUtil::getSampleRecipeBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Recipe Book");
            initialRecipeBook = new RecipeBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Recipe Book");
            initialRecipeBook = new RecipeBook();
        }

        return new ModelManager(initialInventory, initialRecipeBook, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Inventory");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Inventory " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Fridgy ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
