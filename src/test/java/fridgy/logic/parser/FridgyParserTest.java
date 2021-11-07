package fridgy.logic.parser;

import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_ALL_PREFIX_PRESENT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_DEL_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_VIEW_COMMAND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.logic.commands.AddCommand;
import fridgy.logic.commands.ClearCommand;
import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.DeleteCommand;
import fridgy.logic.commands.ExitCommand;
import fridgy.logic.commands.HelpCommand;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.logic.commands.recipe.AddRecipeCommand;
import fridgy.logic.commands.recipe.DeleteRecipeCommand;
import fridgy.logic.commands.recipe.ViewRecipeCommand;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.Inventory;
import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.RecipeBook;
import fridgy.model.ingredient.ExpiryDate;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Quantity;
import fridgy.model.recipe.Recipe;
import fridgy.model.tag.Tag;
import fridgy.testutil.RecipeBuilder;
import fridgy.testutil.TypicalBaseIngredients;
import fridgy.ui.Observer;
import fridgy.ui.TabEnum;
import fridgy.ui.UiState;


public class FridgyParserTest {
    private static final String EMPTY_COMMAND = "";
    private static final String INVALID_SINGLE_WORD_COMMAND = "kekw";
    private static final String VALID_SINGLE_WORD_HELP_COMMAND = "help";
    private static final String VALID_SINGLE_WORD_EXIT_COMMAND = "exit";
    private static final String INVALID_DOUBLE_WORD_RECIPE_COMMAND = "add recipe";
    private static final String INVALID_DOUBLE_WORD_INGREDIENT_COMMAND = "delete ingredient";
    private static final String INVALID_DOUBLE_WORD_GENERAL_COMMAND = "why tho";
    private static final String VALID_DOUBLE_WORD_CLEAR_COMMAND = "clear ingredient";
    private static final String INVALID_TRIPLE_WORD_RECIPE_COMMAND = "kek recipe why";
    private static final String INVALID_TRIPLE_WORD_INGREDIENT_COMMAND = "oh ingredient hello";
    private static final String INVALID_TRIPLE_WORD_GENERAL_COMMAND = "ooga la booga";
    private static final String VALID_TRIPLE_WORD_ADD_INGREDIENT_COMMAND = "add ingredient -n ingr1 -q 20g"
            + " -t tag -e 20-10-2021";
    private static final String VALID_TRIPLE_WORD_DEL_INGREDIENT_COMMAND = "delete ingredient 1";
    private static final String INVALID_TYPE = "rando";
    private static final String COOK_COMMAND_WORD_WITH_INGREDIENT_TYPE = "cook ingredient";
    private static final String COOK_COMMAND_WORD_WITH_INVALID_TYPE = "cook " + INVALID_TYPE;
    private static final String COOK_COMMAND_WORD_WITH_MISSING_TYPE = "cook";
    private static final String VALID_COMMAND_WORD_WITH_INVALID_TYPE = "add " + INVALID_TYPE;
    private static final String VALID_COMMAND_WORD_WITH_MISSING_TYPE = "add";

    private static final FridgyParser testParser = new FridgyParser();

    @Test
    public void parseCommand_emptyInput_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(EMPTY_COMMAND));
    }

    @Test
    public void parseCommand_cookCommandWordWithIngredientType_throwsParseException() {
        String expectedMessage = Messages.MESSAGE_WRONG_TYPE + " "
                + "'ingredient'"
                + ". " + Messages.TYPE_RECIPE_EXPECTED;
        System.out.println(expectedMessage);
        FridgyParserTestUtil.assertParseFailure(testParser, COOK_COMMAND_WORD_WITH_INGREDIENT_TYPE, expectedMessage);
    }

    @Test
    public void parseCommand_cookCommandWordWithInvalidType_throwsParseException() {
        String expectedMessage = Messages.TYPE_INVALID_COMMAND_FORMAT + " "
                + "'" + INVALID_TYPE + "'"
                + ". " + Messages.TYPE_RECIPE_EXPECTED;
        FridgyParserTestUtil.assertParseFailure(testParser, COOK_COMMAND_WORD_WITH_INVALID_TYPE, expectedMessage);
    }

    @Test
    public void parseCommand_cookCommandWordWithMissingType_throwsParseException() {
        String missingTypeMessage = String.format(Messages.MESSAGE_MISSING_TYPE, Messages.TYPE_RECIPE_EXPECTED);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                missingTypeMessage);
        FridgyParserTestUtil.assertParseFailure(testParser, COOK_COMMAND_WORD_WITH_MISSING_TYPE, expectedMessage);
    }

    @Test
    public void parseCommand_validCommandWordWithMissingType_throwsParseException() {
        String missingTypeMessage = String.format(Messages.MESSAGE_MISSING_TYPE, Messages.TYPE_EXPECTED);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                missingTypeMessage);
        FridgyParserTestUtil.assertParseFailure(testParser, VALID_COMMAND_WORD_WITH_MISSING_TYPE, expectedMessage);
    }

    @Test
    public void parseCommand_validCommandWordWithInvalidType_throwsParseException() {
        String expectedMessage = Messages.TYPE_INVALID_COMMAND_FORMAT + " "
                + "'" + INVALID_TYPE + "'"
                + ". " + Messages.TYPE_EXPECTED;
        FridgyParserTestUtil.assertParseFailure(testParser, VALID_COMMAND_WORD_WITH_INVALID_TYPE, expectedMessage);
    }

    @Test
    public void parseCommand_invalidSingleTokenCommand_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_SINGLE_WORD_COMMAND));
    }

    @Test
    public void parseCommand_validSingleTokenGeneralCommand_returnsCorrectCommandResult() {
        Model testModel = new ModelManager();
        CommandResult expected1 = new ExitCommand().execute(testModel);
        CommandResult expected2 = new HelpCommand().execute(testModel);
        try {
            CommandResult result1 = testParser.parseCommand(VALID_SINGLE_WORD_EXIT_COMMAND).apply(testModel);
            CommandResult result2 = testParser.parseCommand(VALID_SINGLE_WORD_HELP_COMMAND).apply(testModel);
            assertEquals(expected1, result1);
            assertEquals(expected2, result2);
        } catch (ParseException pe) {
            Assertions.fail("ParseException thrown!");
        } catch (Exception e) {
            Assertions.fail("Exception thrown!");
        }
    }

    @Test
    public void parseCommand_invalidDoubleTokenCommand_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_DOUBLE_WORD_RECIPE_COMMAND));
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_DOUBLE_WORD_INGREDIENT_COMMAND));
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_DOUBLE_WORD_GENERAL_COMMAND));
    }

    @Test
    public void parseCommand_validDoubleTokenCommand_returnsCorrectCommandResult() {
        Model testModel = new ModelManager();
        CommandResult expected = new ClearCommand(false).execute(testModel);
        try {
            CommandResult result = testParser.parseCommand(VALID_DOUBLE_WORD_CLEAR_COMMAND).apply(testModel);
            assertEquals(expected, result);
        } catch (CommandException ce) {
            Assertions.fail("CommandException thrown!");
        } catch (ParseException pe) {
            Assertions.fail("ParseException thrown!");
        }
    }

    @Test
    public void parseCommand_invalidTripleTokenCommand_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_TRIPLE_WORD_GENERAL_COMMAND));
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_TRIPLE_WORD_INGREDIENT_COMMAND));
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_TRIPLE_WORD_RECIPE_COMMAND));
    }

    @Test
    public void parseCommand_validTripleTokenRecipeCommand_returnsCorrectCommandResult() {
        Model testModel = new ModelManager();
        Recipe testRecipe = new RecipeBuilder()
                .withName("monke")
                .withIngredients(Arrays.asList(TypicalBaseIngredients.INGR1))
                .withSteps(Arrays.asList("why tho"))
                .withDescription("optional")
                .build();
        testModel.setActiveRecipe(testRecipe);
        testModel.setUiState(new UiState(new ObserverStub()));
        try {
            CommandResult expectedAdd = new AddRecipeCommand(testRecipe).execute(testModel);
            CommandResult expectedView = new ViewRecipeCommand(Index.fromZeroBased(0)).execute(testModel);
            CommandResult expectedDelete = new DeleteRecipeCommand(Index.fromZeroBased(0)).execute(testModel);

            // Reinitialize testModel
            testModel.setRecipeBook(new RecipeBook());

            CommandResult resultAdd = testParser.parseCommand(VALID_ADD_COMMAND_ALL_PREFIX_PRESENT).apply(testModel);
            CommandResult resultView = testParser.parseCommand(VALID_VIEW_COMMAND).apply(testModel);
            CommandResult resultDelete = testParser.parseCommand(VALID_DEL_COMMAND).apply(testModel);
            assertTrue(resultAdd.equals(expectedAdd)
                    && resultDelete.equals(expectedDelete)
                    && resultView.equals(expectedView));
        } catch (CommandException ce) {
            Assertions.fail("CommandException thrown!");
        } catch (ParseException pe) {
            Assertions.fail("ParseException thrown!");
        }
    }

    @Test
    public void parseCommand_validTripleTokenIngredientCommand_returnsCorrectCommandResult() {
        Model testModel = new ModelManager();
        Ingredient testIngredient = new Ingredient(new Name("ingr1"), new Quantity("20g"),
                Set.of(new Tag("tag")), new ExpiryDate("20-10-2021"));

        try {
            CommandResult expectedAdd = new AddCommand(testIngredient).execute(testModel);
            CommandResult expectedDelete = new DeleteCommand(Index.fromZeroBased(0)).execute(testModel);

            // Reinitialize testModel
            testModel.setInventory(new Inventory());

            CommandResult resultAdd = testParser
                    .parseCommand(VALID_TRIPLE_WORD_ADD_INGREDIENT_COMMAND).apply(testModel);
            CommandResult resultDelete = testParser
                    .parseCommand(VALID_TRIPLE_WORD_DEL_INGREDIENT_COMMAND).apply(testModel);
            assertTrue(resultAdd.equals(expectedAdd)
                    && resultDelete.equals(expectedDelete));

        } catch (CommandException ce) {
            Assertions.fail("CommandException thrown!");
        } catch (ParseException pe) {
            Assertions.fail("ParseException thrown!");
        }
    }

    class ObserverStub implements Observer {

        @Override
        public void update(Ingredient newItem) {

        }

        @Override
        public void update(Recipe newItem) {

        }

        @Override
        public void update(TabEnum tab) {

        }

        @Override
        public void clearWindow() {}
    }
}
