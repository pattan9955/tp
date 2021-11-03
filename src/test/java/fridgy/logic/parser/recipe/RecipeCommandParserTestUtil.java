package fridgy.logic.parser.recipe;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;

import fridgy.logic.commands.recipe.AddRecipeCommand;
import fridgy.logic.commands.recipe.CookRecipeCommand;
import fridgy.logic.commands.recipe.DeleteRecipeCommand;
import fridgy.logic.commands.recipe.FindRecipeCommand;
import fridgy.logic.commands.recipe.ListRecipeCommand;
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
    public static final String VALID_ADD_COMMAND_MISSING_DESCRIPTION = "add recipe -n monke -i ingr1 1kg -s why tho";
    public static final String VALID_ADD_COMMAND_ALL_PREFIX_PRESENT = "add recipe -n monke -i ingr1 1kg "
            + "-s why tho -d optional";
    public static final String VALID_ADD_COMMAND_MULTIPLE_STEPS = "add recipe -n monke -i ingr1 1kg -s why tho "
            + "-s but why tho -d optional";
    public static final String VALID_ADD_COMMAND_MULTIPLE_INGREDIENTS = "add recipe -n monke -i ingr1 1kg -i ingr2 1ml "
            + "-s why tho -d optional";
    public static final String VALID_ADD_COMMAND_REPEATED_INGREDIENTS = "add recipe -n monke -i ingr1 1kg -i ingr1 1kg "
            + "-s why tho -d optional";

    //--------------------------------INVALID DELETE RECIPE COMMANDS-------------------------------------------------
    public static final String INVALID_DEL_COMMAND_WRONG_KEYWORD = "delete kekw 2";
    public static final String INVALID_DEL_COMMAND_INVALID_INDEX = "delete recipe -3";
    public static final String INVALID_DEL_COMMAND_WRONG_FORMAT = "recipe delete 0";
    public static final String INVALID_DEL_COMMAND_NO_KEYWORD = "delete 0";
    public static final String VALID_MDEL_COMMAND = "delete recipe 1 2 3";

    //---------------------------------VALID DELETE RECIPE COMMANDS---------------------------------------------------
    public static final String VALID_DEL_COMMAND = "delete recipe 1";

    public static final String INVALID_ADD_COMMAND_MESSAGE = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddRecipeCommand.MESSAGE_USAGE);
    public static final String INVALID_DEL_COMMAND_MESSAGE = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeleteRecipeCommand.MESSAGE_USAGE);

    //------------------------------------VALID VIEW RECIPE COMMANDS-------------------------------------------------
    public static final String VALID_VIEW_COMMAND = "view recipe 1";

    //---------------------------------VALID FIND RECIPE COMMANDS---------------------------------------------------
    public static final String VALID_FIND_COMMAND = "find recipe chicken burger";
    public static final String VALID_FIND_COMMAND_WHITESPACES = "find recipe    chicken       \t burger";

    public static final String INVALID_FIND_COMMAND_MESSAGE = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindRecipeCommand.MESSAGE_USAGE);

    //--------------------------------INVALID FIND RECIPE COMMANDS-------------------------------------------------
    public static final String INVALID_FIND_COMMAND_EMPTY_KEYWORD = "find recipe";
    public static final String INVALID_FIND_COMMAND_WHITESPACE_KEYWORD = "find recipe   \t";
    public static final String INVALID_FIND_COMMAND_WRONG_FORMAT = "find chicken burger";

    //---------------------------------VALID LIST RECIPE COMMANDS---------------------------------------------------
    public static final String VALID_LIST_COMMAND = "list recipe";
    public static final String INVALID_LIST_COMMAND_MESSAGE = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ListRecipeCommand.MESSAGE_USAGE);

    //---------------------------------INVALID LIST RECIPE COMMANDS---------------------------------------------------
    public static final String INVALID_LIST_COMMAND = "list";
    public static final String INVALID_LIST_TYPE_COMMAND = "list ingredient";


    //---------------------------------VALID COOK RECIPE COMMANDS-----------------------------------------------------
    public static final String VALID_COOK_COMMAND = "cook recipe 1";
    //---------------------------------INVALID COOK RECIPE COMMANDS---------------------------------------------------
    public static final String INVALID_COOK_COMMAND = "cook recipe";
    public static final String INVALID_COOK_COMMAND_WRONG_KEYWORD = "cook kekw 2";
    public static final String INVALID_COOK_COMMAND_INVALID_INDEX = "cook recipe -3";
    public static final String INVALID_COOK_COMMAND_WRONG_FORMAT = "recipe cook 0";
    public static final String INVALID_COOK_COMMAND_NO_KEYWORD = "cook 0";


    public static final String INVALID_COOK_COMMAND_MESSAGE = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            CookRecipeCommand.MESSAGE_USAGE);
    //---------------------------------INVALID EDIT RECIPE COMMANDS---------------------------------------------------
    public static final String INVALID_EDIT_COMMAND = "edit";
    public static final String INVALID_BLANK_FIELDS_EDIT_COMMAND = "edit recipe 1";
    public static final String INVALID_EDIT_TYPE_COMMAND = "edit kekw -i ingr1";
    public static final String INVALID_INDEX_EDIT_COMMAND = "edit recipe -1 -i ingr1 -n test name";

    //-----------------------------------VALID EDIT RECIPE COMMAND----------------------------------------------------
    public static final String VALID_EDIT_COMMAND_ALL_FIELDS_PRESENT = "edit recipe 1 -n new Test -i new ingr1 100mg "
            + "-i new ingr2 200mg -s new step 1 -s new step 2 -d new optional description";
    public static final String VALID_EDIT_COMMAND_NAME_PRESENT = "edit recipe 1 -n new Test";
    public static final String VALID_EDIT_COMMAND_INGR_PRESENT = "edit recipe 1 -i new ingr1 100mg -i new ingr2 200mg";
    public static final String VALID_EDIT_COMMAND_DESC_PRESENT = "edit recipe 1 -d new optional description";
    public static final String VALID_EDIT_COMMAND_STEPS_PRESENT = "edit recipe 1 -s new step 1 -s new step 2";
    public static final String VALID_EDIT_COMMAND_DESC_STEPS_PRESENT = "edit recipe 1 -d new optional description "
            + "-s new step 1 -s new step 2";
    public static final String VALID_EDIT_COMMAND_INGR_NAME_PRESENT = "edit recipe 1 -n new Test -i new ingr1 100mg"
            + " -i new ingr2 200mg";

    //-------------------------------------INVALID CLEAR RECIPE COMMANDS----------------------------------------------
    public static final String INVALID_CLEAR_COMMAND = "clear";
    public static final String INVALID_CLEAR_COMMAND_WRONG_TYPE = "clear ingredient";
    public static final String INVALID_CLEAR_COMMAND_ARGS_PROVIDED = "clear recipe kekw";

    //---------------------------------------VALID CLEAR RECIPE COMMANDS-----------------------------------------------
    public static final String VALID_CLEAR_RECIPE_COMMAND = "clear recipe";

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

