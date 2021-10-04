package fridgy.logic.parser;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.CommandTestUtil;
import fridgy.logic.commands.DeleteCommand;
import fridgy.testutil.TypicalIndexes;
import org.junit.jupiter.api.Test;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + "1", new DeleteCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + "a", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
