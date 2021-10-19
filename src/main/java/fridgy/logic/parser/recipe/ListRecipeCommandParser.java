package fridgy.logic.parser.recipe;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fridgy.logic.commands.recipe.ListRecipeCommand;
import fridgy.logic.parser.exceptions.ParseException;

/**
 * Parses input argument and creates a new ListRecipeCommand object
 */
public class ListRecipeCommandParser implements RecipeCommandParser<ListRecipeCommand> {
    private static final Pattern RECIPE_LIST_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile(ListRecipeCommand.RECIPE_KEYWORD);

    /**
     * Parses the given {@code String} of arguments in the context of the ListRecipeCommand
     * and returns a ListRecipeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListRecipeCommand parse(String args) throws ParseException {
        final Matcher matcher = RECIPE_LIST_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListRecipeCommand.MESSAGE_USAGE));
        }

        return new ListRecipeCommand();
    }

}
