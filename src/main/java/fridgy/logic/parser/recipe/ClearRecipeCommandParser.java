package fridgy.logic.parser.recipe;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fridgy.logic.commands.recipe.ClearRecipeCommand;
import fridgy.logic.parser.exceptions.ParseException;

/**
 * Parses input argument and creates a new ClearRecipeCommand object
 */
public class ClearRecipeCommandParser implements RecipeCommandParser<ClearRecipeCommand> {
    private static final Pattern RECIPE_CLEAR_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile(ClearRecipeCommand.RECIPE_KEYWORD);

    /**
     * Parses the given {@code userInput} of arguments in the context of the ClearRecipeCommand
     * and returns a ClearRecipeCommand object for execution.
     *
     * @param userInput The arguments to be parsed.
     * @return A ClearRecipeCommand for execution.
     * @throws ParseException if {@code userInput} does not conform to the expected format.
     */
    @Override
    public ClearRecipeCommand parse(String userInput) throws ParseException {
        final Matcher matcher = RECIPE_CLEAR_COMMAND_ARGUMENT_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ClearRecipeCommand.MESSAGE_USAGE));
        }

        return new ClearRecipeCommand();
    }
}
