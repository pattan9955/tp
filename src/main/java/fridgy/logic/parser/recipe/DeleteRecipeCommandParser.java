package fridgy.logic.parser.recipe;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.logic.commands.recipe.DeleteRecipeCommand;
import fridgy.logic.parser.ParserUtil;
import fridgy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteRecipeCommand object.
 */
public class DeleteRecipeCommandParser implements RecipeCommandParser<DeleteRecipeCommand> {

    private static final Pattern DELETE_RECIPE_COMMAND_ARGUMENT_FORMAT =
            Pattern.compile(DeleteRecipeCommand.RECIPE_KEYWORD + "(?<arguments>.*?)");

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRecipeCommand
     * and returns a DeleteRecipeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteRecipeCommand parse(String args) throws ParseException {
        final Matcher matcher = DELETE_RECIPE_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteRecipeCommand.MESSAGE_USAGE));
        }

        final String arguments = matcher.group("arguments");
        final String[] argList = arguments.trim().split("\\h");
        final List<Index> indicesList = new ArrayList<>();

        // parse the indices
        try {
            for (String idxString : argList) {
                Index index = ParserUtil.parseIndex(idxString);
                indicesList.add(index);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteRecipeCommand.MESSAGE_USAGE), pe);
        }
        return new DeleteRecipeCommand(indicesList.toArray(Index[]::new));
    }
}
