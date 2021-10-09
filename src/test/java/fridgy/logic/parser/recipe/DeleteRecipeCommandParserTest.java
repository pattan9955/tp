package fridgy.logic.parser.recipe;

import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_DEL_COMMAND_INVALID_INDEX;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_DEL_COMMAND_MESSAGE;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_DEL_COMMAND_WRONG_KEYWORD;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_DEL_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.assertParseFailure;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.index.Index;
import fridgy.logic.commands.recipe.DeleteRecipeCommand;

public class DeleteRecipeCommandParserTest {

    private DeleteRecipeCommandParser testParser = new DeleteRecipeCommandParser();

    @Test
    public void parse_invalidKeywordProvided_fails() {
        String testString = INVALID_DEL_COMMAND_WRONG_KEYWORD.replace("delete ", "");
        assertParseFailure(testParser, testString, INVALID_DEL_COMMAND_MESSAGE);
    }

    @Test
    public void parse_invalidTargetIndexProvided_fails() {
        String testString = INVALID_DEL_COMMAND_INVALID_INDEX.replace("delete ", "");
        assertParseFailure(testParser, testString, INVALID_DEL_COMMAND_MESSAGE);
    }

    @Test
    public void parse_validCommandProvided_passes() {
        String testString = VALID_DEL_COMMAND.replace("delete ", "");
        DeleteRecipeCommand expected = new DeleteRecipeCommand(Index.fromOneBased(1));
        assertParseSuccess(testParser, testString, expected);
    }
}
