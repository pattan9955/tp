package fridgy.logic.parser.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fridgy.logic.commands.recipe.AddRecipeCommand;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.recipe.Recipe;
import fridgy.testutil.RecipeBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_ADD_COMMAND_WRONG_FORMAT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_ADD_COMMAND_NO_KEYWORD;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_ALL_PREFIX_PRESENT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_MISSING_DESCRIPTION;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_MULTIPLE_INGREDIENTS;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_MULTIPLE_STEPS;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_REPEATED_INGREDIENTS;

public class RecipeParserTest {
    public RecipeParser testParser = new RecipeParser();

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
        AddRecipeCommand missingDescCommand= new AddRecipeCommand(missingDesc);

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
}
