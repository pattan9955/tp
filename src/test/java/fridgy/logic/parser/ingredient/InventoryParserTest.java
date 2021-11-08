package fridgy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.CommandTestUtil;
import fridgy.logic.commands.ingredient.AddIngredientCommand;
import fridgy.logic.commands.ingredient.ClearIngredientCommand;
import fridgy.logic.commands.ingredient.DeleteIngredientCommand;
import fridgy.logic.commands.ingredient.EditIngredientCommand;
import fridgy.logic.commands.ingredient.EditIngredientCommand.EditIngredientDescriptor;
import fridgy.logic.commands.ingredient.FindIngredientCommand;
import fridgy.logic.commands.HelpIngredientCommand;
import fridgy.logic.commands.ingredient.ListIngredientCommand;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.logic.parser.ingredient.AddCommandParser;
import fridgy.logic.parser.ingredient.InventoryParser;
import fridgy.model.ingredient.ExpiryDate;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.NameContainsKeywordsPredicate;
import fridgy.model.ingredient.Quantity;
import fridgy.testutil.Assert;
import fridgy.testutil.EditIngredientDescriptorBuilder;
import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.IngredientUtil;
import fridgy.testutil.TypicalIndexes;

public class InventoryParserTest {

    private static final String VALID_ADD_INGREDIENT_ARGUMENT =
            fridgy.logic.commands.ingredient.AddIngredientCommand.INGREDIENT_KEYWORD + " -n Ingredient -q 5 -e 20-08-2010";
    private static final String INVALID_ADD_INGREDIENT_ARGUMENT =
            CommandTestUtil.INVALID_INGREDIENT_ARGUMENT_FORMAT
                    + " -n Ingredient -q 5 -e 20-08-2010";
    private static final Ingredient VALID_INGREDIENT = new Ingredient(new Name("Ingredient"),
            new Quantity("5"), new HashSet<>(), new ExpiryDate("20-08-2010"));
    private final InventoryParser parser = new InventoryParser();

    @Test
    public void parseCommand_add() throws Exception {
        AddCommandParser c = new AddCommandParser();
        assertEquals(c.parse(VALID_ADD_INGREDIENT_ARGUMENT), new AddIngredientCommand(VALID_INGREDIENT));
    }

    @Test
    public void parseCommand_addCommand() throws Exception {
        AddCommandParser c = new AddCommandParser();
        assertEquals(c.parse(VALID_ADD_INGREDIENT_ARGUMENT), new AddIngredientCommand(VALID_INGREDIENT));

        Assert.assertThrows(ParseException.class,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, fridgy.logic.commands.ingredient.AddIngredientCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(fridgy.logic.commands.ingredient.AddIngredientCommand.COMMAND_WORD + " " + INVALID_ADD_INGREDIENT_ARGUMENT));
    }

    @Test
    public void parseCommand_clear() throws Exception {
        ClearIngredientCommand clearExpiredCommand = (ClearIngredientCommand) parser.parseCommand(fridgy.logic.commands.ingredient.ClearIngredientCommand.COMMAND_WORD + " "
                + fridgy.logic.commands.ingredient.ClearIngredientCommand.INGREDIENT_KEYWORD + " " + fridgy.logic.commands.ingredient.ClearIngredientCommand.EXPIRED_KEYWORD);
        ClearIngredientCommand clearCommand = (ClearIngredientCommand) parser.parseCommand(fridgy.logic.commands.ingredient.ClearIngredientCommand.COMMAND_WORD + " "
                + fridgy.logic.commands.ingredient.ClearIngredientCommand.INGREDIENT_KEYWORD);
        assertEquals(new ClearIngredientCommand(true), clearExpiredCommand);
        assertEquals(new ClearIngredientCommand(false), clearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteIngredientCommand command = (DeleteIngredientCommand) parser.parseCommand(
                fridgy.logic.commands.ingredient.DeleteIngredientCommand.COMMAND_WORD + " " + DeleteIngredientCommand.INGREDIENT_KEYWORD + " "
                        + TypicalIndexes.INDEX_FIRST_INGREDIENT.getOneBased());
        assertEquals(new DeleteIngredientCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Ingredient ingredient = new IngredientBuilder().build();
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder(ingredient).build();
        EditIngredientCommand command = (EditIngredientCommand) parser.parseCommand(EditIngredientCommand.COMMAND_WORD + " "
                + fridgy.logic.commands.ingredient.EditIngredientCommand.INGREDIENT_KEYWORD + " " + TypicalIndexes.INDEX_FIRST_INGREDIENT.getOneBased()
                + " " + IngredientUtil.getEditIngredientDescriptorDetails(descriptor));
        assertEquals(new EditIngredientCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT, descriptor), command);

        Assert.assertThrows(ParseException.class,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, fridgy.logic.commands.ingredient.EditIngredientCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(fridgy.logic.commands.ingredient.EditIngredientCommand.COMMAND_WORD
                        + " " + fridgy.logic.commands.ingredient.EditIngredientCommand.INGREDIENT_KEYWORD + " -oogabooga"));
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindIngredientCommand command = (FindIngredientCommand) parser.parseCommand(
                FindIngredientCommand.COMMAND_WORD + " " + fridgy.logic.commands.ingredient.FindIngredientCommand.INGREDIENT_KEYWORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindIngredientCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(fridgy.logic.commands.ingredient.ListIngredientCommand.COMMAND_WORD
                + " " + fridgy.logic.commands.ingredient.ListIngredientCommand.INGREDIENT_KEYWORD) instanceof ListIngredientCommand);
        Assert.assertThrows(ParseException.class,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListIngredientCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(fridgy.logic.commands.ingredient.ListIngredientCommand.COMMAND_WORD
                        + " " + fridgy.logic.commands.ingredient.ListIngredientCommand.INGREDIENT_KEYWORD + " 3"));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        Assert.assertThrows(ParseException.class,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpIngredientCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        Assert.assertThrows(ParseException.class, Messages.MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand("unknownCommand"));
    }
}
