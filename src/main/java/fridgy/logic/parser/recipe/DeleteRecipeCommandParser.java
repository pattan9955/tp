package fridgy.logic.parser.recipe;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fridgy.commons.core.index.Index;
import fridgy.logic.commands.recipe.DeleteRecipeCommand;
import fridgy.logic.parser.ParserUtil;
import fridgy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteRecipeCommand object.
 */
public class DeleteRecipeCommandParser implements RecipeCommandParser<DeleteRecipeCommand> {

    private static final Pattern DELETE_RECIPE_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile(DeleteRecipeCommand.RECIPE_KEYWORD + "(?<arguments>.*)");

    @Override
    public DeleteRecipeCommand parse(String userInput) throws ParseException {
        final Matcher matcher = DELETE_RECIPE_COMMAND_ARGUMENT_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteRecipeCommand.MESSAGE_USAGE));
        }
        final String args = matcher.group("arguments");

        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteRecipeCommand(index);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRecipeCommand.MESSAGE_USAGE), e
            );
        }
    }
}
