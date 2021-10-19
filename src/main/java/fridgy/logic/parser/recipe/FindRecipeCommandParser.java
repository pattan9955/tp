package fridgy.logic.parser.recipe;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.recipe.FindRecipeCommand;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.recipe.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindRecipeCommand object
 */
public class FindRecipeCommandParser implements RecipeCommandParser<FindRecipeCommand> {

    private static final Pattern FIND_RECIPE_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile(FindRecipeCommand.RECIPE_KEYWORD + "(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the FindRecipeCommand
     * and returns a FindRecipeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindRecipeCommand parse(String userInput) throws ParseException {

        final Matcher matcher = FIND_RECIPE_COMMAND_ARGUMENT_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindRecipeCommand.MESSAGE_USAGE));
        }

        final String trimmedArgs = matcher.group("arguments").trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindRecipeCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        return new FindRecipeCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
