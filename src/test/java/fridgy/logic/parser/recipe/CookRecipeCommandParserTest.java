package fridgy.logic.parser.recipe;

import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_COOK_COMMAND_INVALID_INDEX;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_COOK_COMMAND_MESSAGE;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_COOK_COMMAND_WRONG_KEYWORD;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_COOK_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.assertParseFailure;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.index.Index;
import fridgy.logic.commands.recipe.CookRecipeCommand;

public class CookRecipeCommandParserTest {

    private CookRecipeCommandParser testParser = new CookRecipeCommandParser();

    @Test
    public void parse_invalidKeywordProvided_fails() {
        String testString = INVALID_COOK_COMMAND_WRONG_KEYWORD.replace("cook ", "");
        assertParseFailure(testParser, testString, INVALID_COOK_COMMAND_MESSAGE);
    }

    @Test
    public void parse_invalidTargetIndexProvided_fails() {
        String testString = INVALID_COOK_COMMAND_INVALID_INDEX.replace("cook ", "");
        assertParseFailure(testParser, testString, INVALID_COOK_COMMAND_MESSAGE);
    }

    @Test
    public void parse_validCommandProvided_passes() {
        String testString = VALID_COOK_COMMAND.replace("cook ", "");
        CookRecipeCommand expected = new CookRecipeCommand(Index.fromOneBased(1));
        assertParseSuccess(testParser, testString, expected);
    }
}
