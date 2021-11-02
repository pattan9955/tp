package fridgy.logic.parser.recipe;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.index.Index;
import fridgy.logic.commands.recipe.DeleteRecipeCommand;

import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.*;

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
        String multiDelString = VALID_MDEL_COMMAND.replace("delete ", "");
        DeleteRecipeCommand multiDelExpected = new DeleteRecipeCommand(Index.fromOneBased(1),
                Index.fromOneBased(2), Index.fromOneBased(3));
        assertParseSuccess(testParser, multiDelString, multiDelExpected);

        String singleDelString = VALID_DEL_COMMAND.replace("delete ", "");
        DeleteRecipeCommand singleDelExpected = new DeleteRecipeCommand(Index.fromOneBased(1));
        assertParseSuccess(testParser, singleDelString, singleDelExpected);
    }
}
