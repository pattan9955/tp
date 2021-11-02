package fridgy.ui;

import java.util.logging.Logger;

import fridgy.commons.core.GuiSettings;
import fridgy.commons.core.LogsCenter;
import fridgy.logic.Logic;
import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;
import fridgy.ui.event.ActiveItemChangeEvent;
import fridgy.ui.event.TabSwitchEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> implements Observer {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private TabListPanel tabListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private VBox tabListPanelPlaceholder;

    @FXML
    private ScrollPane viewDisplayPlaceholder;

    @FXML
    private VBox displayContainer;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.logic.setUiState(new UiState(this));

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        ActiveItemPanel activeItemPanel = new ActiveItemPanel(logic::isEnough);
        viewDisplayPlaceholder.vvalueProperty().bind(displayContainer.heightProperty());
        viewDisplayPlaceholder.hvalueProperty().bind(displayContainer.widthProperty());
        displayContainer.getChildren().add(activeItemPanel.getRoot());

        tabListPanel = new TabListPanel(
            new IngredientListPanel(logic.getFilteredIngredientList(), activeItemPanel),
            new RecipeListPanel(logic.getFilteredRecipeList(), activeItemPanel, logic::isEnough)
        );
        tabListPanelPlaceholder.getChildren().add(tabListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getInventoryFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());


        // Initialise event listeners.
        this.getRoot().addEventFilter(ActiveItemChangeEvent.RECIPE, activeItemPanel::handleRecipeEvent);
        this.getRoot().addEventFilter(ActiveItemChangeEvent.INGREDIENT, activeItemPanel::handleIngredientEvent);
        this.getRoot().addEventFilter(ActiveItemChangeEvent.CLEAR, activeItemPanel::handleClearEvent);

        this.getRoot().addEventFilter(TabSwitchEvent.CHANGE, tabListPanel::handleTabChange);
        this.getRoot().addEventFilter(ActiveItemChangeEvent.RECIPE, tabListPanel::handleActiveRecipe);
        this.getRoot().addEventFilter(ActiveItemChangeEvent.INGREDIENT, tabListPanel::handleActiveIngredient);
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Fires an {@code ActiveItemChangeEvent} to update active recipe.
     * and a {@code TabSwitchEvent} to switch to the correct tab.
     * @param recipe the recipe to be displayed.
     */
    @Override
    public void update(Recipe recipe) {
        this.getRoot().fireEvent(new ActiveItemChangeEvent<Recipe>(ActiveItemChangeEvent.RECIPE, recipe));
    }

    /**
     * Fires an {@code ActiveItemChangeEvent} to update active ingredient.
     * and a {@code TabSwitchEvent} to switch to the correct tab.
     * @param ingredient the ingredient to be displayed.
     */
    @Override
    public void update(Ingredient ingredient) {
        this.getRoot().fireEvent(new ActiveItemChangeEvent<Ingredient>(ActiveItemChangeEvent.INGREDIENT, ingredient));
    }

    /**
     * Fires a {@code TabSwitchEvent} to switch to the correct tab.
     * @param tab the tab to switch to
     */
    @Override
    public void update(TabEnum tab) {
        this.getRoot().fireEvent(new TabSwitchEvent(TabSwitchEvent.CHANGE, tab));
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
