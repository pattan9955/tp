package fridgy.logic.parser.recipe;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_ADD_COMMAND_MESSAGE;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_ADD_COMMAND_MISSING_INGREDIENT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_ADD_COMMAND_MISSING_NAME;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.INVALID_ADD_COMMAND_WRONG_KEYWORD;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_ALL_PREFIX_PRESENT;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_MISSING_DESCRIPTION;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_MULTIPLE_INGREDIENTS;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_MULTIPLE_STEPS;
import static fridgy.logic.parser.recipe.RecipeCommandParserTestUtil.VALID_ADD_COMMAND_REPEATED_INGREDIENTS;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import fridgy.logic.commands.recipe.AddRecipeCommand;
import fridgy.model.recipe.Recipe;
import fridgy.testutil.RecipeBuilder;
import fridgy.testutil.TypicalBaseIngredients;

public class AddRecipeCommandParserTest {

    @Test
    public void parse_invalidKeyword_throwsParseException() {
        String userInput = INVALID_ADD_COMMAND_WRONG_KEYWORD.replace("add ", "");
        String expectedMsg = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddRecipeCommand.MESSAGE_USAGE);
        AddRecipeCommandParser testParser = new AddRecipeCommandParser();
        RecipeCommandParserTestUtil.assertParseFailure(testParser,
                userInput, expectedMsg);
    }

    @Test
    public void parse_missingRequiredPrefixes_failure() {
        AddRecipeCommandParser testParser = new AddRecipeCommandParser();
        RecipeCommandParserTestUtil.assertParseFailure(testParser,
                INVALID_ADD_COMMAND_MISSING_NAME.replace("add ", ""), INVALID_ADD_COMMAND_MESSAGE);

        RecipeCommandParserTestUtil.assertParseFailure(testParser,
                INVALID_ADD_COMMAND_MISSING_INGREDIENT.replace("add ", ""), INVALID_ADD_COMMAND_MESSAGE);
    }

    @Test
    public void parse_singlePrefixesOnlyValidCommands_success() {
        AddRecipeCommandParser testParser = new AddRecipeCommandParser();

        Recipe noDescRecipe = new RecipeBuilder()
                .withName("monke")
                .withIngredients(Arrays.asList(TypicalBaseIngredients.INGR1))
                .withSteps(Arrays.asList("why tho"))
                .withDescription(null)
                .build();
        AddRecipeCommand expectedNoDescRecipe = new AddRecipeCommand(noDescRecipe);
        RecipeCommandParserTestUtil.assertParseSuccess(testParser,
                VALID_ADD_COMMAND_MISSING_DESCRIPTION.replace("add ", ""), expectedNoDescRecipe);

        Recipe allPrefixRecipe = new RecipeBuilder(noDescRecipe)
                .withDescription("optional")
                .build();
        AddRecipeCommand expectedAllPrefixRecipe = new AddRecipeCommand(allPrefixRecipe);
        RecipeCommandParserTestUtil.assertParseSuccess(testParser,
                VALID_ADD_COMMAND_ALL_PREFIX_PRESENT.replace("add ", ""), expectedAllPrefixRecipe);
    }

    @Test
    public void parse_multipleSteps_success() {
        AddRecipeCommandParser testParser = new AddRecipeCommandParser();

        Recipe multipleStepsRecipe = new RecipeBuilder()
                .withName("monke")
                .withSteps(Arrays.asList("why tho", "but why tho"))
                .withIngredients(Arrays.asList(TypicalBaseIngredients.INGR1))
                .withDescription("optional")
                .build();
        AddRecipeCommand expected = new AddRecipeCommand(multipleStepsRecipe);

        RecipeCommandParserTestUtil.assertParseSuccess(testParser,
                VALID_ADD_COMMAND_MULTIPLE_STEPS.replace("add ", ""), expected);
    }

    @Test
    public void parse_multipleIngredients_success() {
        AddRecipeCommandParser testParser = new AddRecipeCommandParser();

        Recipe multipleIngrRecipe = new RecipeBuilder()
                .withName("monke")
                .withSteps(Arrays.asList("why tho"))
                .withIngredients(Arrays.asList(
                    TypicalBaseIngredients.INGR1,
                    TypicalBaseIngredients.INGR2
                ))
                .withDescription("optional")
                .build();
        AddRecipeCommand expected = new AddRecipeCommand(multipleIngrRecipe);

        RecipeCommandParserTestUtil.assertParseSuccess(testParser,
                VALID_ADD_COMMAND_MULTIPLE_INGREDIENTS.replace("add ", ""), expected);
    }

    @Test
    public void parse_repeatedIngredients_success() {
        AddRecipeCommandParser testParser = new AddRecipeCommandParser();

        Recipe repeatedIngrRecipe = new RecipeBuilder()
                .withName("monke")
                .withSteps(Arrays.asList("why tho"))
                .withIngredients(Arrays.asList(TypicalBaseIngredients.INGR1, TypicalBaseIngredients.INGR1))
                .withDescription("optional")
                .build();
        AddRecipeCommand expected = new AddRecipeCommand(repeatedIngrRecipe);

        RecipeCommandParserTestUtil.assertParseSuccess(testParser,
                VALID_ADD_COMMAND_REPEATED_INGREDIENTS.replace("add ", ""), expected);
    }
}
