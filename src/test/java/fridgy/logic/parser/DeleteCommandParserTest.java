package fridgy.logic.parser;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.CommandTestUtil;
import fridgy.logic.commands.ingredient.DeleteCommand;
import fridgy.logic.parser.ingredient.DeleteCommandParser;
import fridgy.testutil.TypicalIndexes;

public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_passes() {
        // testing "delete ingredient 1 2 3"
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + "1 2 3",
                new DeleteCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT, TypicalIndexes.INDEX_SECOND_INGREDIENT,
                        TypicalIndexes.INDEX_THIRD_INGREDIENT));

        // testing "delete ingredients 1"
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + "1",
                new DeleteCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT));
    }

    @Test
    public void parse_invalidArgs_fails() {
        // testing "delete ingredient a 2 3 4"
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + "a 2 3 4",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidKeyword_fails() {
        String testString = "delete ingr 1 2 3";
        CommandParserTestUtil.assertParseFailure(parser, testString,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_fails() {
        String testString = "delete ingredient -69";
        CommandParserTestUtil.assertParseFailure(parser, testString,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
