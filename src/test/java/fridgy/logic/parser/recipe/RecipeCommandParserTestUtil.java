package fridgy.logic.parser.recipe;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;

import fridgy.logic.commands.recipe.AddRecipeCommand;
import fridgy.logic.commands.recipe.DeleteRecipeCommand;
import fridgy.logic.commands.recipe.RecipeCommand;
import fridgy.logic.parser.exceptions.ParseException;

/**
 * Contains helper methods for testing command parsers.
 */
public class RecipeCommandParserTestUtil {

    /**
     * Collection of user commands for testing purposes.
     */

    //---------------------------------INVALID ADD RECIPE COMMANDS---------------------------------------------------
    public static final String INVALID_ADD_COMMAND_WRONG_KEYWORD = "add kekw -n monke -i ingr1 -s why tho -d optional";
    public static final String INVALID_ADD_COMMAND_MISSING_NAME = "add recipe -i ingr1 -s why tho -d optional";
    public static final String INVALID_ADD_COMMAND_MISSING_INGREDIENT = "add recipe -n monke -s why tho -d optional";
    public static final String INVALID_ADD_COMMAND_MISSING_STEPS = "add recipe -n monke -i ingr1 -d optional";
    public static final String INVALID_ADD_COMMAND_WRONG_FORMAT = "-n monke add recipe -i ingr1 -s why tho -d optional";
    public static final String INVALID_ADD_COMMAND_NO_KEYWORD = "add -n monke -i ingr1 -s why tho -d optional";

    //-----------------------------------VALID ADD RECIPE COMMANDS----------------------------------------------------
    public static final String VALID_ADD_COMMAND_MISSING_DESCRIPTION = "add recipe -n monke -i ingr1 -s why tho";
    public static final String VALID_ADD_COMMAND_ALL_PREFIX_PRESENT = "add recipe -n monke -i ingr1 " +
            "-s why tho -d optional";
    public static final String VALID_ADD_COMMAND_MULTIPLE_STEPS = "add recipe -n monke -i ingr1 -s why tho " +
            "-s but why tho -d optional";
    public static final String VALID_ADD_COMMAND_MULTIPLE_INGREDIENTS = "add recipe -n monke -i ingr1 -i ingr2 " +
            "-s why tho -d optional";
    public static final String VALID_ADD_COMMAND_REPEATED_INGREDIENTS = "add recipe -n monke -i ingr1 -i ingr1 " +
            "-s why tho -d optional";

    //--------------------------------INVALID DELETE RECIPE COMMANDS-------------------------------------------------
    public static final String INVALID_DEL_COMMAND_WRONG_KEYWORD = "delete kekw 2";
    public static final String INVALID_DEL_COMMAND_INVALID_INDEX = "delete recipe -3";
    public static final String INVALID_DEL_COMMAND_WRONG_FORMAT = "recipe delete 0";
    public static final String INVALID_DEL_COMMAND_NO_KEYWORD = "delete 0";

    //---------------------------------VALID DELETE RECIPE COMMANDS---------------------------------------------------
    public static final String VALID_DEL_COMMAND = "delete recipe 1";

    public static final String INVALID_ADD_COMMAND_MESSAGE = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddRecipeCommand.MESSAGE_USAGE);
    public static final String INVALID_DEL_COMMAND_MESSAGE = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeleteRecipeCommand.MESSAGE_USAGE);

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(RecipeCommandParser parser, String userInput, RecipeCommand expectedCommand) {
        try {
            RecipeCommand command = parser.parse(userInput);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseFailure(RecipeCommandParser parser, String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}

