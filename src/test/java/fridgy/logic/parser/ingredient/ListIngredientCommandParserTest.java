package fridgy.logic.parser;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.ingredient.ListIngredientCommand;
import fridgy.logic.parser.ingredient.ListCommandParser;

public class ListIngredientCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_wrongOrEmptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, fridgy.logic.commands.ingredient.ListIngredientCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser, "ooga",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListIngredientCommand.MESSAGE_USAGE));
    }

}
