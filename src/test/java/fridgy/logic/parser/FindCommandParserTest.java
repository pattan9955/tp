package fridgy.logic.parser;

import java.util.Arrays;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.FindCommand;
import fridgy.model.ingredient.NameContainsKeywordsPredicate;
import org.junit.jupiter.api.Test;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Basil")));
        CommandParserTestUtil.assertParseSuccess(parser, "Alice Basil", expectedFindCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, " \n Alice \n \t Basil  \t", expectedFindCommand);
    }

}
