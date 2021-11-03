package fridgy.logic.parser.recipe;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fridgy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static fridgy.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static fridgy.logic.parser.CliSyntax.PREFIX_NAME;
import static fridgy.logic.parser.CliSyntax.PREFIX_STEP;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.recipe.AddRecipeCommand;
import fridgy.logic.parser.ArgumentMultimap;
import fridgy.logic.parser.ArgumentTokenizer;
import fridgy.logic.parser.ParserUtil;
import fridgy.logic.parser.Prefix;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.recipe.Name;
import fridgy.model.recipe.Recipe;
import fridgy.model.recipe.Step;

/**
 * Parses input arguments and creates a new AddRecipeCommand object.
 */
public class AddRecipeCommandParser implements RecipeCommandParser<AddRecipeCommand> {

    private static final Pattern ADD_RECIPE_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile(AddRecipeCommand.RECIPE_KEYWORD
                    + "(?<arguments>.*)");

    @Override
    public AddRecipeCommand parse(String userInput) throws ParseException {
        final Matcher matcher = ADD_RECIPE_COMMAND_ARGUMENT_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddRecipeCommand.MESSAGE_USAGE));
        }
        final String args = matcher.group("arguments");

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_INGREDIENT, PREFIX_STEP, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_INGREDIENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddRecipeCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseRecipeName(argMultimap.getValue(PREFIX_NAME).get());

        Set<BaseIngredient> ingredientSet = ParserUtil.parseIngredients(argMultimap.getAllValues(PREFIX_INGREDIENT));

        List<Step> stepList = ParserUtil.parseSteps(argMultimap.getAllValues(PREFIX_STEP));

        Optional<String> description = argMultimap.getValue(PREFIX_DESCRIPTION);

        Recipe recipe = new Recipe(name, ingredientSet, stepList, description);

        return new AddRecipeCommand(recipe);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
