package fridgy.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.AddCommand;
import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.CommandTestUtil;
import fridgy.logic.commands.ListCommand;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.ReadOnlyInventory;
import fridgy.model.ReadOnlyRecipeBook;
import fridgy.model.UserPrefs;
import fridgy.model.ingredient.Ingredient;
import fridgy.storage.JsonInventoryStorage;
import fridgy.storage.JsonRecipeBookStorage;
import fridgy.storage.JsonUserPrefsStorage;
import fridgy.storage.StorageManager;
import fridgy.testutil.Assert;
import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.TypicalIngredients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonInventoryStorage addressBookStorage =
                new JsonInventoryStorage(temporaryFolder.resolve("addressBook.json"));
        JsonRecipeBookStorage recipeBookStorage =
                new JsonRecipeBookStorage(temporaryFolder.resolve("recipeBook.json"));

        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, recipeBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonInventoryIoExceptionThrowingStub
        JsonInventoryStorage addressBookStorage =
                new JsonInventoryIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionInventory.json"));
        JsonRecipeBookStorage recipeBookStorage =
                new JsonRecipeBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionRecipeBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, recipeBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.PHONE_DESC_AMY + CommandTestUtil.EMAIL_DESC_AMY
                + CommandTestUtil.ADDRESS_DESC_AMY;
        Ingredient expectedIngredient = new IngredientBuilder(TypicalIngredients.AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addIngredient(expectedIngredient);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredIngredientList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredIngredientList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getInventory(), model.getRecipeBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        Assert.assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonInventoryIoExceptionThrowingStub extends JsonInventoryStorage {
        private JsonInventoryIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveInventory(ReadOnlyInventory addressBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonRecipeBookIoExceptionThrowingStub extends JsonRecipeBookStorage {
        private JsonRecipeBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveRecipeBook(ReadOnlyRecipeBook recipeBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
