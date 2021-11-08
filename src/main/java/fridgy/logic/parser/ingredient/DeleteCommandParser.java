package fridgy.logic.parser.ingredient;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.logic.commands.ingredient.DeleteCommand;
import fridgy.logic.parser.Parser;
import fridgy.logic.parser.ParserUtil;
import fridgy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object.
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private static final Pattern DELETE_INGREDIENT_COMMAND_ARGUMENT_FORMAT =
            Pattern.compile(DeleteCommand.INGREDIENT_KEYWORD + "(?<arguments>.*?)");

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        final Matcher matcher = DELETE_INGREDIENT_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteCommand.MESSAGE_USAGE));
        }

        final String arguments = matcher.group("arguments");
        final String[] argList = arguments.trim().split("\\h");
        final List<Index>indicesList = new ArrayList<>();

        // parse the indices
        try {
            for (String idxString : argList) {
                Index index = ParserUtil.parseIndex(idxString);
                indicesList.add(index);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
        return new DeleteCommand(indicesList.toArray(Index[]::new));
    }
}
