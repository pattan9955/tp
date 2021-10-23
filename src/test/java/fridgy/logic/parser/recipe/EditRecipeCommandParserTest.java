package fridgy.logic.parser.recipe;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_BLANK_FIELDS_EDIT_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_EDIT_TYPE_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_INDEX_EDIT_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_EDIT_COMMAND_ALL_FIELDS_PRESENT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_EDIT_COMMAND_DESC_PRESENT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_EDIT_COMMAND_DESC_STEPS_PRESENT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_EDIT_COMMAND_INGR_NAME_PRESENT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_EDIT_COMMAND_INGR_PRESENT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_EDIT_COMMAND_NAME_PRESENT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_EDIT_COMMAND_STEPS_PRESENT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.assertParseFailure;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.assertParseSuccess;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.index.Index;
import fridgy.logic.commands.recipe.EditRecipeCommand;
import fridgy.logic.commands.recipe.EditRecipeCommand.EditRecipeDescriptor;
import fridgy.testutil.EditRecipeDescriptorBuilder;

public class EditRecipeCommandParserTest {

    private EditRecipeCommandParser testParser = new EditRecipeCommandParser();

    private static String removeEditCommandWord(String fullCommand) {
        return fullCommand.replace("edit ", "");
    }

    @Test
    public void parse_nullUserInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> testParser.parse(null));
    }

    @Test
    public void parse_invalidFormatCommand_failure() {
        String expectedMsg = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditRecipeCommand.MESSAGE_USAGE);

        // Empty input
        assertParseFailure(testParser, "", expectedMsg);

        // Not a recipe command
        assertParseFailure(testParser, removeEditCommandWord(INVALID_EDIT_TYPE_COMMAND), expectedMsg);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMsg = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditRecipeCommand.MESSAGE_USAGE);

        assertParseFailure(testParser, removeEditCommandWord(INVALID_INDEX_EDIT_COMMAND), expectedMsg);
    }

    @Test
    public void parse_noFieldsEdited_failure() {
        String expectedMsg = EditRecipeCommand.MESSAGE_NOT_EDITED;

        assertParseFailure(testParser, removeEditCommandWord(INVALID_BLANK_FIELDS_EDIT_COMMAND), expectedMsg);
    }

    @Test
    public void parse_allFieldsPresent_success() {
        EditRecipeDescriptor expectedDescriptor = new EditRecipeDescriptorBuilder()
                .withName("new Test")
                .withDescription("new optional description")
                .withSteps("new step 1", "new step 2")
                .withIngredients("new ingr1 100mg", "new ingr2 200mg")
                .build();
        EditRecipeCommand expectedCommand = new EditRecipeCommand(Index.fromOneBased(1), expectedDescriptor);

        assertParseSuccess(testParser,
                removeEditCommandWord(VALID_EDIT_COMMAND_ALL_FIELDS_PRESENT),
                expectedCommand);
    }

    @Test
    public void parse_oneFieldPresent_success() {
        EditRecipeDescriptor baseDescriptor = new EditRecipeDescriptor();

        // Name present
        EditRecipeDescriptor nameDescriptor = new EditRecipeDescriptorBuilder(baseDescriptor)
                .withName("new Test")
                .build();
        EditRecipeCommand nameCommand = new EditRecipeCommand(Index.fromOneBased(1), nameDescriptor);
        assertParseSuccess(testParser, removeEditCommandWord(VALID_EDIT_COMMAND_NAME_PRESENT), nameCommand);

        // Ingredient present
        EditRecipeDescriptor ingrDescriptor = new EditRecipeDescriptorBuilder(baseDescriptor)
                .withIngredients("new ingr1 100mg", "new ingr2 200mg")
                .build();
        EditRecipeCommand ingrCommand = new EditRecipeCommand(Index.fromOneBased(1), ingrDescriptor);
        assertParseSuccess(testParser, removeEditCommandWord(VALID_EDIT_COMMAND_INGR_PRESENT), ingrCommand);

        // Steps present
        EditRecipeDescriptor stepDescriptor = new EditRecipeDescriptorBuilder(baseDescriptor)
                .withSteps("new step 1", "new step 2")
                .build();
        EditRecipeCommand stepCommand = new EditRecipeCommand(Index.fromOneBased(1), stepDescriptor);
        assertParseSuccess(testParser, removeEditCommandWord(VALID_EDIT_COMMAND_STEPS_PRESENT), stepCommand);

        // Description present
        EditRecipeDescriptor descDescriptor = new EditRecipeDescriptorBuilder(baseDescriptor)
                .withDescription("new optional description")
                .build();
        EditRecipeCommand descCommand = new EditRecipeCommand(Index.fromOneBased(1), descDescriptor);
        assertParseSuccess(testParser, removeEditCommandWord(VALID_EDIT_COMMAND_DESC_PRESENT), descCommand);
    }

    @Test
    public void parse_multipleFieldsPresent_success() {
        EditRecipeDescriptor baseDescriptor = new EditRecipeDescriptor();

        // Description + Steps present
        EditRecipeDescriptor descStepsDescriptor = new EditRecipeDescriptorBuilder()
                .withDescription("new optional description")
                .withSteps("new step 1", "new step 2")
                .build();
        EditRecipeCommand descStepsCommand = new EditRecipeCommand(Index.fromOneBased(1), descStepsDescriptor);
        assertParseSuccess(testParser, removeEditCommandWord(VALID_EDIT_COMMAND_DESC_STEPS_PRESENT),
                descStepsCommand);

        // Ingredients + Name present
        EditRecipeDescriptor ingrNameDescriptor = new EditRecipeDescriptorBuilder()
                .withIngredients("new ingr1 100mg", "new ingr2 200mg")
                .withName("new Test")
                .build();
        EditRecipeCommand ingrNameCommand = new EditRecipeCommand(Index.fromOneBased(1), ingrNameDescriptor);
        assertParseSuccess(testParser, removeEditCommandWord(VALID_EDIT_COMMAND_INGR_NAME_PRESENT),
                ingrNameCommand);
    }
}
