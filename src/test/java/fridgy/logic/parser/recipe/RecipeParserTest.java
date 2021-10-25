package fridgy.logic.parser.recipe;

import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_ADD_COMMAND_NO_KEYWORD;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_ADD_COMMAND_WRONG_FORMAT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_DEL_COMMAND_NO_KEYWORD;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_DEL_COMMAND_WRONG_FORMAT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_DEL_COMMAND_WRONG_KEYWORD;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_EDIT_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_EDIT_TYPE_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_FIND_COMMAND_EMPTY_KEYWORD;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_FIND_COMMAND_WHITESPACE_KEYWORD;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_FIND_COMMAND_WRONG_FORMAT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_LIST_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_LIST_TYPE_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_ALL_PREFIX_PRESENT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_MISSING_DESCRIPTION;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_MULTIPLE_INGREDIENTS;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_MULTIPLE_STEPS;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_REPEATED_INGREDIENTS;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_DEL_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_EDIT_COMMAND_ALL_FIELDS_PRESENT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_FIND_COMMAND;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_FIND_COMMAND_WHITESPACES;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_LIST_COMMAND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fridgy.commons.core.index.Index;
import fridgy.logic.commands.recipe.AddRecipeCommand;
import fridgy.logic.commands.recipe.DeleteRecipeCommand;
import fridgy.logic.commands.recipe.EditRecipeCommand;
import fridgy.logic.commands.recipe.EditRecipeCommand.EditRecipeDescriptor;
import fridgy.logic.commands.recipe.FindRecipeCommand;
import fridgy.logic.commands.recipe.ListRecipeCommand;
import fridgy.logic.commands.recipe.RecipeCommand;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.recipe.NameContainsKeywordsPredicate;
import fridgy.model.recipe.Recipe;
import fridgy.testutil.EditRecipeDescriptorBuilder;
import fridgy.testutil.RecipeBuilder;
import fridgy.testutil.TypicalBaseIngredients;

public class RecipeParserTest {
    private RecipeParser testParser = new RecipeParser();

    @Test
    public void parse_emptyInput_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(""));
    }

    @Test
    public void parse_addRecipeInvalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_ADD_COMMAND_WRONG_FORMAT));
    }

    @Test
    public void parse_addRecipeNoKeywordSpecified_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_ADD_COMMAND_NO_KEYWORD));
    }

    @Test
    public void parse_addRecipeCommandValid_returnsRecipeCommand() {
        Recipe base = new RecipeBuilder()
                .withName("monke")
                .withIngredients(Arrays.asList(TypicalBaseIngredients.INGR1))
                .withSteps(Arrays.asList("why tho"))
                .withDescription("optional")
                .build();
        Recipe allPrefPresent = base;
        Recipe missingDesc = new RecipeBuilder(base).withDescription(null).build();
        Recipe multipleSteps = new RecipeBuilder(base)
                .withSteps(Arrays.asList("why tho", "but why tho"))
                .build();
        Recipe repeatedIngr = new RecipeBuilder(base)
                .withIngredients(Arrays.asList(TypicalBaseIngredients.INGR1, TypicalBaseIngredients.INGR1))
                .build();
        Recipe multipleIngr = new RecipeBuilder(base)
                .withIngredients(Arrays.asList(TypicalBaseIngredients.INGR1, TypicalBaseIngredients.INGR2))
                .build();

        AddRecipeCommand allPrefPresentCommand = new AddRecipeCommand(allPrefPresent);
        AddRecipeCommand multipleIngrCommand = new AddRecipeCommand(multipleIngr);
        AddRecipeCommand repeatedIngrCommand = new AddRecipeCommand(repeatedIngr);
        AddRecipeCommand multipleStepsCommand = new AddRecipeCommand(multipleSteps);
        AddRecipeCommand missingDescCommand = new AddRecipeCommand(missingDesc);

        try {
            assertEquals(testParser.parseCommand(VALID_ADD_COMMAND_ALL_PREFIX_PRESENT), allPrefPresentCommand);
            assertEquals(testParser.parseCommand(VALID_ADD_COMMAND_MISSING_DESCRIPTION), missingDescCommand);
            assertEquals(testParser.parseCommand(VALID_ADD_COMMAND_MULTIPLE_INGREDIENTS), multipleIngrCommand);
            assertEquals(testParser.parseCommand(VALID_ADD_COMMAND_MULTIPLE_STEPS), multipleStepsCommand);
            assertEquals(testParser.parseCommand(VALID_ADD_COMMAND_REPEATED_INGREDIENTS), repeatedIngrCommand);
        } catch (ParseException e) {
            Assertions.fail("ParseException thrown!");
        }
    }

    @Test
    public void parse_deleteRecipeCommandInvalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_DEL_COMMAND_WRONG_FORMAT));
    }

    @Test
    public void parse_deleteRecipeNoKeywordSpecified_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_DEL_COMMAND_NO_KEYWORD));
    }

    @Test
    public void parse_deleteRecipeWrongKeywordSpecified_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_DEL_COMMAND_WRONG_KEYWORD));
    }

    @Test
    public void parse_deleteRecipeValidCommand_returnsRecipeCommand() {
        DeleteRecipeCommand expected = new DeleteRecipeCommand(Index.fromOneBased(1));
        try {
            RecipeCommand result = testParser.parseCommand(VALID_DEL_COMMAND);
            assertTrue(result.equals(expected));
        } catch (ParseException e) {
            Assertions.fail("ParseException thrown!");
        }
    }

    @Test
    public void parse_findRecipeEmptyKeyword_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_FIND_COMMAND_EMPTY_KEYWORD));
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_FIND_COMMAND_WHITESPACE_KEYWORD));
    }

    @Test
    public void parse_findRecipeInvalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_FIND_COMMAND_WRONG_FORMAT));
    }

    @Test
    public void parse_findRecipeValidCommand_returnsRecipeCommand() {
        List<String> keywords = Arrays.asList("chicken", "burger");
        FindRecipeCommand expectedCommand = new FindRecipeCommand(new NameContainsKeywordsPredicate(keywords));
        try {
            assertEquals(expectedCommand, testParser.parseCommand(VALID_FIND_COMMAND));
            assertEquals(expectedCommand, testParser.parseCommand(VALID_FIND_COMMAND_WHITESPACES));
        } catch (ParseException e) {
            Assertions.fail("ParseException thrown!");
        }
    }

    @Test
    public void parse_listRecipeInvalidCommand_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_LIST_COMMAND));
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_LIST_TYPE_COMMAND));
    }

    @Test
    public void parse_listRecipeValidCommand_returnsRecipeCommand() {
        ListRecipeCommand expectedCommand = new ListRecipeCommand();
        try {
            RecipeCommand result = testParser.parseCommand(VALID_LIST_COMMAND);
            assertEquals(expectedCommand, result);
        } catch (ParseException e) {
            Assertions.fail("ParseException thrown!");
        }
    }

    @Test
    public void parse_editRecipeInvalidCommand_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_EDIT_COMMAND));
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_EDIT_TYPE_COMMAND));
    }

    @Test
    public void parse_editRecipeValidCommand_returnsRecipeCommand() {
        EditRecipeDescriptor expectedDescriptor = new EditRecipeDescriptorBuilder()
                .withName("new Test")
                .withDescription("new optional description")
                .withIngredients("new ingr1 100mg", "new ingr2 200mg")
                .withSteps("new step 1", "new step 2")
                .build();
        EditRecipeCommand expectedCommand = new EditRecipeCommand(Index.fromOneBased(1), expectedDescriptor);
        try {
            RecipeCommand result = testParser.parseCommand(VALID_EDIT_COMMAND_ALL_FIELDS_PRESENT);
            assertEquals(result, expectedCommand);
        } catch (ParseException pe) {
            Assertions.fail("ParseException thrown!");
        }
    }
}
