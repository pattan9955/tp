package fridgy.logic.parser;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.CommandTestUtil;
import fridgy.logic.commands.ingredient.FindIngredientCommand;
import fridgy.logic.parser.ingredient.FindCommandParser;
import fridgy.model.ingredient.NameContainsKeywordsPredicate;

public class FindIngredientCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // empty command
        CommandParserTestUtil.assertParseFailure(parser, "     ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, fridgy.logic.commands.ingredient.FindIngredientCommand.MESSAGE_USAGE));

        // empty keyword find ingredient command
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindIngredientCommand.MESSAGE_USAGE));

        // empty keyword (whitespaces) find ingredient command
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                        + "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, fridgy.logic.commands.ingredient.FindIngredientCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindIngredientCommand expectedFindCommand =
                new FindIngredientCommand(new NameContainsKeywordsPredicate(Arrays.asList("Almond", "Basil")));
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + "Almond Basil", expectedFindCommand);

        // multiple whitespaces (excluding \n) between keywords
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + " Almond \t Basil  \t", expectedFindCommand);
    }
}
