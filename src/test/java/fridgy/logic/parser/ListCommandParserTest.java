package fridgy.logic.parser;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.ListCommand;
import org.junit.jupiter.api.Test;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_wrongOrEmptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser, "ooga",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

}
