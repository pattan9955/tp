package fridgy.logic.parser.recipe;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.recipe.ViewRecipeCommand;

public class ViewRecipeCommandParserTest {

    private ViewRecipeCommandParser parser = new ViewRecipeCommandParser();

    @Test
    public void parse_wrongOrEmptyArg_throwsParseException() {

        RecipeCommandParserTestUtil.assertParseFailure(parser, "     ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ViewRecipeCommand.MESSAGE_USAGE));

        RecipeCommandParserTestUtil.assertParseFailure(parser, "ooga",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ViewRecipeCommand.MESSAGE_USAGE));
    }
}
