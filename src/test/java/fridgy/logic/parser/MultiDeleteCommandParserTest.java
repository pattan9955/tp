package fridgy.logic.parser;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.CommandTestUtil;
import fridgy.logic.commands.MultiDeleteCommand;
import fridgy.testutil.TypicalIndexes;

public class MultiDeleteCommandParserTest {

    private MultiDeleteCommandParser parser = new MultiDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        // testing "multidelete ingredient 1 2 3"
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + "1 2 3",
                new MultiDeleteCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT, TypicalIndexes.INDEX_SECOND_INGREDIENT,
                        TypicalIndexes.INDEX_THIRD_INGREDIENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // testing "multidelete ingredient a 2 3 4"
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + "a 2 3 4",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MultiDeleteCommand.MESSAGE_USAGE));
    }
}
