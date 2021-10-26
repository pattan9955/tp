package fridgy.logic.parser.recipe;

import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_MDEL_COMMAND_INVALID_INDEX;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_MDEL_COMMAND_MESSAGE;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_MDEL_COMMAND_WRONG_KEYWORD;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_MDEL_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.assertParseFailure;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.index.Index;
import fridgy.logic.commands.recipe.MultiDeleteRecipeCommand;

public class MultiDeleteRecipeCommandParserTest {

    private MultiDeleteRecipeCommandParser testParser = new MultiDeleteRecipeCommandParser();

    @Test
    public void parse_invalidKeywordProvided_fails() {
        String testString = INVALID_MDEL_COMMAND_WRONG_KEYWORD.replace("multidelete ", "");
        assertParseFailure(testParser, testString, INVALID_MDEL_COMMAND_MESSAGE);
    }

    @Test
    public void parse_invalidTargetIndexProvided_fails() {
        String testString = INVALID_MDEL_COMMAND_INVALID_INDEX.replace("multidelete ", "");
        assertParseFailure(testParser, testString, INVALID_MDEL_COMMAND_MESSAGE);
    }

    @Test
    public void parse_validCommandProvided_passes() {
        String testString = VALID_MDEL_COMMAND.replace("multidelete ", "");
        MultiDeleteRecipeCommand expected = new MultiDeleteRecipeCommand(Index.fromOneBased(1),
                Index.fromOneBased(2), Index.fromOneBased(3));
        assertParseSuccess(testParser, testString, expected);
    }
}
