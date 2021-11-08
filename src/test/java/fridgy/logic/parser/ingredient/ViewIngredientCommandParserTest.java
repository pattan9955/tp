package fridgy.logic.parser;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.ingredient.ViewIngredientCommand;
import fridgy.logic.parser.ingredient.ViewCommandParser;

public class ViewIngredientCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_wrongOrEmptyArg_throwsParseException() {

        CommandParserTestUtil.assertParseFailure(parser, "     ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ViewIngredientCommand.MESSAGE_USAGE));

        CommandParserTestUtil.assertParseFailure(parser, "ooga",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, fridgy.logic.commands.ingredient.ViewIngredientCommand.MESSAGE_USAGE));
    }
}
