package fridgy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fridgy.commons.core.index.Index;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.logic.commands.ingredient.EditCommand;
import fridgy.logic.parser.CliSyntax;
import fridgy.model.Inventory;
import fridgy.model.Model;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.NameContainsKeywordsPredicate;
import fridgy.testutil.Assert;
import fridgy.testutil.EditIngredientDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_INGREDIENT_ARGUMENT_FORMAT = "ingredient";

    public static final String VALID_NAME_ALMOND = "Almond Bee";
    public static final String VALID_NAME_BASIL = "Basil Choo";
    public static final String VALID_NAME_FISH = "Fish Choo";
    public static final String VALID_QUANTITY_ALMOND = "11111111";
    public static final String VALID_QUANTITY_BASIL = "22222222";
    public static final String VALID_QUANTITY_FISH = "22222222";
    public static final String VALID_DESCRIPTION_ALMOND = "Block 312, Almond Street 1";
    public static final String VALID_DESCRIPTION_BASIL = "Block 123, Basilby Street 3";
    public static final String VALID_DESCRIPTION_FISH = " ";
    public static final String VALID_TAG_VEGETABLE = "vegetable";
    public static final String VALID_TAG_SNACK = "snack";
    public static final String VALID_EXPIRY_DATE = "20-08-2099";

    public static final String NAME_DESC_ALMOND = " " + CliSyntax.PREFIX_NAME + VALID_NAME_ALMOND;
    public static final String NAME_DESC_BASIL = " " + CliSyntax.PREFIX_NAME + VALID_NAME_BASIL;
    public static final String NAME_DESC_FISH = " " + CliSyntax.PREFIX_NAME + VALID_NAME_FISH;
    public static final String QUANTITY_DESC_ALMOND = " " + CliSyntax.PREFIX_QUANTITY + VALID_QUANTITY_ALMOND;
    public static final String QUANTITY_DESC_BASIL = " " + CliSyntax.PREFIX_QUANTITY + VALID_QUANTITY_BASIL;
    public static final String QUANTITY_DESC_FISH = " " + CliSyntax.PREFIX_QUANTITY + VALID_QUANTITY_FISH;
    public static final String DESCRIPTION_DESC_ALMOND = " " + CliSyntax.PREFIX_DESCRIPTION + VALID_DESCRIPTION_ALMOND;
    public static final String DESCRIPTION_DESC_BASIL = " " + CliSyntax.PREFIX_DESCRIPTION + VALID_DESCRIPTION_BASIL;
    public static final String DESCRIPTION_DESC_FISH = " " + CliSyntax.PREFIX_DESCRIPTION + VALID_DESCRIPTION_FISH;
    public static final String EXPIRY_DATE_DESC = " " + CliSyntax.PREFIX_EXPIRY + VALID_EXPIRY_DATE;
    public static final String TAG_DESC_SNACK = " " + CliSyntax.PREFIX_TAG + VALID_TAG_SNACK;
    public static final String TAG_DESC_VEGETABLE = " " + CliSyntax.PREFIX_TAG + VALID_TAG_VEGETABLE;

    public static final String INVALID_INGREDIENT_ARGUMENT_FORMAT = "monkegredient";
    public static final String INVALID_NAME_DESC = " " + CliSyntax.PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_QUANTITY_DESC = " "
            + CliSyntax.PREFIX_QUANTITY + "911a"; // 'a' not allowed in quantities
    public static final String INVALID_DESCRIPTION_DESC = " "
            + CliSyntax.PREFIX_DESCRIPTION; // empty string not allowed for description
    public static final String INVALID_DESCRIPTION_DESC_TWO = " " + CliSyntax.PREFIX_DESCRIPTION
            + "ƗØƀĐǤ "; // non-ascii characters not allowed for description
    public static final String INVALID_TAG_DESC = " " + CliSyntax.PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditIngredientDescriptor DESC_ALMOND;
    public static final EditCommand.EditIngredientDescriptor DESC_BASIL;
    public static final EditCommand.EditIngredientDescriptor DESC_FISH;

    static {
        DESC_ALMOND = new EditIngredientDescriptorBuilder().withName(VALID_NAME_ALMOND)
                .withQuantity(VALID_QUANTITY_ALMOND)
                .withDescription(VALID_DESCRIPTION_ALMOND).withTags(VALID_TAG_SNACK)
                .withExpiry(VALID_EXPIRY_DATE).build();
        DESC_BASIL = new EditIngredientDescriptorBuilder().withName(VALID_NAME_BASIL)
                .withQuantity(VALID_QUANTITY_BASIL)
                .withDescription(VALID_DESCRIPTION_BASIL)
                .withExpiry(VALID_EXPIRY_DATE)
                .withTags(VALID_TAG_VEGETABLE, VALID_TAG_SNACK).build();
        DESC_FISH = new EditIngredientDescriptorBuilder().withName(VALID_NAME_FISH)
                .withQuantity(VALID_QUANTITY_FISH)
                .withDescription(VALID_DESCRIPTION_FISH)
                .withExpiry(VALID_EXPIRY_DATE).build();
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
     * - the inventory, filtered ingredient list and selected ingredient in {@code actualModel} remain unchanged
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
     * {@code model}'s inventory.
     */
    public static void showIngredientAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredIngredientList().size());

        Ingredient ingredient = model.getFilteredIngredientList().get(targetIndex.getZeroBased());
        final String[] splitName = ingredient.getName().fullName.split("\\s+");
        model.updateFilteredIngredientList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredIngredientList().size());
    }
}
