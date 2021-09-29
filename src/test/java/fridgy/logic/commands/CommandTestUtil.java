package fridgy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static fridgy.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fridgy.commons.core.index.Index;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.logic.parser.CliSyntax;
import fridgy.model.Inventory;
import fridgy.model.Model;
import fridgy.model.ingredient.NameContainsKeywordsPredicate;
import fridgy.model.ingredient.Ingredient;
import fridgy.testutil.Assert;
import fridgy.testutil.EditIngredientDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_QUANTITY_AMY = "11111111";
    public static final String VALID_QUANTITY_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_DESCRIPTION_AMY = "Block 312, Amy Street 1";
    public static final String VALID_DESCRIPTION_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + CliSyntax.PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + CliSyntax.PREFIX_NAME + VALID_NAME_BOB;
    public static final String QUANTITY_DESC_AMY = " " + CliSyntax.PREFIX_QUANTITY + VALID_QUANTITY_AMY;
    public static final String QUANTITY_DESC_BOB = " " + CliSyntax.PREFIX_QUANTITY + VALID_QUANTITY_BOB;
    public static final String EMAIL_DESC_AMY = " " + CliSyntax.PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + CliSyntax.PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String DESCRIPTION_DESC_AMY = " " + CliSyntax.PREFIX_DESCRIPTION + VALID_DESCRIPTION_AMY;
    public static final String DESCRIPTION_DESC_BOB = " " + CliSyntax.PREFIX_DESCRIPTION + VALID_DESCRIPTION_BOB;
    public static final String TAG_DESC_FRIEND = " " + CliSyntax.PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + CliSyntax.PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + CliSyntax.PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_QUANTITY_DESC = " " + CliSyntax.PREFIX_QUANTITY + "911a"; // 'a' not allowed in quantitys
    public static final String INVALID_EMAIL_DESC = " " + CliSyntax.PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_DESCRIPTION_DESC = " " + CliSyntax.PREFIX_DESCRIPTION; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + CliSyntax.PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditIngredientDescriptor DESC_AMY;
    public static final EditCommand.EditIngredientDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditIngredientDescriptorBuilder().withName(VALID_NAME_AMY)
                .withQuantity(VALID_QUANTITY_AMY).withEmail(VALID_EMAIL_AMY).withDescription(VALID_DESCRIPTION_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditIngredientDescriptorBuilder().withName(VALID_NAME_BOB)
                .withQuantity(VALID_QUANTITY_BOB).withEmail(VALID_EMAIL_BOB).withDescription(VALID_DESCRIPTION_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered ingredient list and selected ingredient in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Inventory expectedInventory = new Inventory(actualModel.getInventory());
        List<Ingredient> expectedFilteredList = new ArrayList<>(actualModel.getFilteredIngredientList());

        Assert.assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedInventory, actualModel.getInventory());
        assertEquals(expectedFilteredList, actualModel.getFilteredIngredientList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the ingredient at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showIngredientAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredIngredientList().size());

        Ingredient ingredient = model.getFilteredIngredientList().get(targetIndex.getZeroBased());
        final String[] splitName = ingredient.getName().fullName.split("\\s+");
        model.updateFilteredIngredientList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredIngredientList().size());
    }

}
