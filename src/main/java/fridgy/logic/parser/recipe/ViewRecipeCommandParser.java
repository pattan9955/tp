package fridgy.logic.parser.recipe;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fridgy.commons.core.index.Index;
import fridgy.logic.commands.recipe.ViewRecipeCommand;
import fridgy.logic.parser.ParserUtil;
import fridgy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewRecipeCommand object.
 */
public class ViewRecipeCommandParser implements RecipeCommandParser<ViewRecipeCommand> {

    private static final Pattern VIEW_RECIPE_COMMAND_ARGUMENT_FORMAT = Pattern
        .compile(ViewRecipeCommand.RECIPE_KEYWORD + "(?<arguments>.*)");

    @Override
    public ViewRecipeCommand parse(String userInput) throws ParseException {
        final Matcher matcher = VIEW_RECIPE_COMMAND_ARGUMENT_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewRecipeCommand.MESSAGE_USAGE));
        }
        final String args = matcher.group("arguments");

        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewRecipeCommand(index);
        } catch (ParseException e) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewRecipeCommand.MESSAGE_USAGE), e
            );
        }
    }
}
