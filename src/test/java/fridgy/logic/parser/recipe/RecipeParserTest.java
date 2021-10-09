package fridgy.logic.parser.recipe;

import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_ADD_COMMAND_NO_KEYWORD;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_ADD_COMMAND_WRONG_FORMAT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_DEL_COMMAND_NO_KEYWORD;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_DEL_COMMAND_WRONG_FORMAT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_DEL_COMMAND_WRONG_KEYWORD;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_ALL_PREFIX_PRESENT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_MISSING_DESCRIPTION;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_MULTIPLE_INGREDIENTS;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_MULTIPLE_STEPS;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_REPEATED_INGREDIENTS;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_DEL_COMMAND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fridgy.commons.core.index.Index;
import fridgy.logic.commands.recipe.AddRecipeCommand;
import fridgy.logic.commands.recipe.DeleteRecipeCommand;
import fridgy.logic.commands.recipe.RecipeCommand;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.recipe.Recipe;
import fridgy.testutil.RecipeBuilder;

public class RecipeParserTest {
    private RecipeParser testParser = new RecipeParser();

    @Test
    public void parse_emptyInput_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(""));
    }

    @Test
    public void parse_AddRecipeInvalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_ADD_COMMAND_WRONG_FORMAT));
    }

    @Test
    public void parse_AddRecipeNoKeywordSpecified_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_ADD_COMMAND_NO_KEYWORD));
    }

    @Test
    public void parse_AddRecipeCommandValid_returnsRecipeCommand() {
        Recipe base = new RecipeBuilder()
                .withName("monke")
                .withIngredients(Arrays.asList("ingr1"))
                .withSteps(Arrays.asList("why tho"))
                .withDescription("optional")
                .build();
        Recipe allPrefPresent = base;
        Recipe missingDesc = new RecipeBuilder(base).withDescription(null).build();
        Recipe multipleSteps = new RecipeBuilder(base)
                .withSteps(Arrays.asList("why tho", "but why tho"))
                .build();
        Recipe repeatedIngr = new RecipeBuilder(base)
                .withIngredients(Arrays.asList("ingr1", "ingr1"))
                .build();
        Recipe multipleIngr = new RecipeBuilder(base)
                .withIngredients(Arrays.asList("ingr1", "ingr2"))
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
    public void parse_DeleteRecipeCommandInvalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_DEL_COMMAND_WRONG_FORMAT));
    }

    @Test
    public void parse_DeleteRecipeNoKeywordSpecified_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_DEL_COMMAND_NO_KEYWORD));
    }

    @Test
    public void parse_DeleteRecipeWrongKeywordSpecified_throwsParseException() {
        assertThrows(ParseException.class, () -> testParser.parseCommand(INVALID_DEL_COMMAND_WRONG_KEYWORD));
    }

    @Test
    public void parse_DeleteRecipeValidCommand_returnsRecipeCommand() {
        DeleteRecipeCommand expected = new DeleteRecipeCommand(Index.fromOneBased(1));
        try {
            RecipeCommand result = testParser.parseCommand(VALID_DEL_COMMAND);
            assertTrue(result.equals(expected));
        } catch (ParseException e) {
            Assertions.fail("ParseException thrown!");
        }
    }
}
