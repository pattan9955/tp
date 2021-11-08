package fridgy.logic.parser.recipe;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_CLEAR_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_CLEAR_COMMAND_ARGS_PROVIDED;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_CLEAR_COMMAND_WRONG_TYPE;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_CLEAR_RECIPE_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.assertParseFailure;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import fridgy.logic.commands.recipe.ClearRecipeCommand;

public class ClearRecipeCommandParserTest {
    private static final ClearRecipeCommandParser testParser = new ClearRecipeCommandParser();
    private static final String failureMsg = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ClearRecipeCommand.MESSAGE_USAGE);

    @Test
    public void parse_missingRecipeKeyword_failure() {
        String testCommand = INVALID_CLEAR_COMMAND.replace("clear", "");

        assertParseFailure(testParser, testCommand, failureMsg);
    }

    @Test
    public void parse_wrongKeywordType_failure() {
        String testCommand = INVALID_CLEAR_COMMAND_WRONG_TYPE.replace("clear ", "");

        assertParseFailure(testParser, testCommand, failureMsg);
    }

    @Test
    public void parse_argsProvided_failure() {
        String testCommand = INVALID_CLEAR_COMMAND_ARGS_PROVIDED.replace("clear ", "");

        assertParseFailure(testParser, testCommand, failureMsg);
    }

    @Test
    public void parse_validCommand_success() {
        String testCommand = VALID_CLEAR_RECIPE_COMMAND.replace("clear ", "");

        assertParseSuccess(testParser, testCommand, new ClearRecipeCommand());
    }
}
