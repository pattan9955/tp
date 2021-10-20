package fridgy.logic.parser.recipe;

import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_LIST_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_LIST_COMMAND_MESSAGE;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_LIST_TYPE_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_LIST_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.assertParseFailure;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import fridgy.logic.commands.recipe.ListRecipeCommand;

public class ListRecipeParserTest {
    private ListRecipeCommandParser parser = new ListRecipeCommandParser();

    @Test
    public void parse_missingRecipeKeyword_throwsParseException() {
        String testString = INVALID_LIST_COMMAND.replace("list ", "");
        assertParseFailure(parser, testString, INVALID_LIST_COMMAND_MESSAGE);
    }
    @Test
    public void parse_wrongKeywordType_throwsParseException() {
        String testString = INVALID_LIST_TYPE_COMMAND.replace("list ", "");
        assertParseFailure(parser, testString, INVALID_LIST_COMMAND_MESSAGE);
    }

    @Test
    public void parse_validCommand_success() {
        String testString = VALID_LIST_COMMAND.replace("list ", "");
        ListRecipeCommand expected = new ListRecipeCommand();
        assertParseSuccess(parser, testString, expected);
    }
}
