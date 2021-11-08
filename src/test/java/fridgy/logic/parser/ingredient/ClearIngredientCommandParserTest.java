package fridgy.logic.parser;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.CommandTestUtil;
import fridgy.logic.commands.ingredient.ClearIngredientCommand;

public class ClearIngredientCommandParserTest {

    private ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parse_validArgs_returnsClearCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT,
                new ClearIngredientCommand(false));
    }

    @Test
    public void parse_validArgs_returnsClearExpiredCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                        + " " + fridgy.logic.commands.ingredient.ClearIngredientCommand.EXPIRED_KEYWORD, new ClearIngredientCommand(true));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + "a",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, fridgy.logic.commands.ingredient.ClearIngredientCommand.MESSAGE_USAGE));
    }
}
