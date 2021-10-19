package fridgy.logic.parser.recipe;

import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_FIND_COMMAND_EMPTY_KEYWORD;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_FIND_COMMAND_MESSAGE;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_FIND_COMMAND_WHITESPACE_KEYWORD;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_FIND_COMMAND_WRONG_FORMAT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_FIND_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_FIND_COMMAND_WHITESPACES;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.assertParseFailure;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import fridgy.logic.commands.recipe.FindRecipeCommand;
import fridgy.model.recipe.NameContainsKeywordsPredicate;

public class FindRecipeCommandParserTest {

    private FindRecipeCommandParser testParser = new FindRecipeCommandParser();

    @Test
    public void parse_missingKeyword_failure() {
        assertParseFailure(testParser,
                INVALID_FIND_COMMAND_EMPTY_KEYWORD.replace("find ", ""), INVALID_FIND_COMMAND_MESSAGE);

        assertParseFailure(testParser,
                INVALID_FIND_COMMAND_WHITESPACE_KEYWORD.replace("find ", ""), INVALID_FIND_COMMAND_MESSAGE);
    }

    @Test
    public void parse_invalidFormat_failure() {
        assertParseFailure(testParser,
                INVALID_FIND_COMMAND_WRONG_FORMAT.replace("find ", ""), INVALID_FIND_COMMAND_MESSAGE);
    }

    @Test
    public void parse_validCommandWithoutEmptySpaces_success() {
        String testString = VALID_FIND_COMMAND.replace("find ", "");
        FindRecipeCommand expected =
                new FindRecipeCommand(new NameContainsKeywordsPredicate(Arrays.asList("chicken", "burger")));
        assertParseSuccess(testParser, testString, expected);
    }

    @Test
    public void parse_validCommandWithEmptySpaces_success() {
        String testString = VALID_FIND_COMMAND_WHITESPACES.replace("find ", "");
        FindRecipeCommand expected =
                new FindRecipeCommand(new NameContainsKeywordsPredicate(Arrays.asList("chicken", "burger")));
        assertParseSuccess(testParser, testString, expected);
    }
}
