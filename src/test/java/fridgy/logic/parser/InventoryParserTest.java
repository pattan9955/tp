package fridgy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static fridgy.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import fridgy.commons.core.Messages;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.ingredient.Email;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.NameContainsKeywordsPredicate;
import fridgy.model.ingredient.Quantity;
import fridgy.testutil.Assert;
import fridgy.testutil.EditIngredientDescriptorBuilder;
import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.IngredientUtil;
import fridgy.testutil.TypicalIndexes;
import org.junit.jupiter.api.Test;

import fridgy.logic.commands.AddCommand;
import fridgy.logic.commands.ClearCommand;
import fridgy.logic.commands.DeleteCommand;
import fridgy.logic.commands.EditCommand;
import fridgy.logic.commands.EditCommand.EditIngredientDescriptor;
import fridgy.logic.commands.ExitCommand;
import fridgy.logic.commands.FindCommand;
import fridgy.logic.commands.HelpCommand;
import fridgy.logic.commands.ListCommand;

public class InventoryParserTest {

    private final InventoryParser parser = new InventoryParser();
    private static final String VALID_INGREDIENT_ARGUMENT =
            AddCommand.INGREDIENT_KEYWORD + " -n Ingredient -q 5 -m almond@gmail.com";
    private static final Ingredient VALID_INGREDIENT = new Ingredient(new Name("Ingredient"),
            new Quantity("5"), new Email("almond@gmail.com"), new HashSet<>());

    @Test
    public void parseCommand_add() throws Exception {
        AddCommandParser c = new AddCommandParser();
        assertEquals(c.parse(VALID_INGREDIENT_ARGUMENT), new AddCommand(VALID_INGREDIENT));
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + DeleteCommand.INGREDIENT_KEYWORD + " " + TypicalIndexes.INDEX_FIRST_INGREDIENT.getOneBased());
        assertEquals(new DeleteCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Ingredient ingredient = new IngredientBuilder().build();
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder(ingredient).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " " + EditCommand.INGREDIENT_KEYWORD + " "
                + TypicalIndexes.INDEX_FIRST_INGREDIENT.getOneBased() + " " + IngredientUtil.getEditIngredientDescriptorDetails(descriptor));
        assertEquals(new EditCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        Assert.assertThrows(ParseException.class, Messages.MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
