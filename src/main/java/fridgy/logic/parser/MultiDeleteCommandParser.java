package fridgy.logic.parser;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.logic.commands.MultiDeleteCommand;
import fridgy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MultiDeleteCommand object.
 */
public class MultiDeleteCommandParser implements Parser<MultiDeleteCommand> {

    private static final Pattern MULTIDELETE_INGREDIENT_COMMAND_ARGUMENT_FORMAT =
            Pattern.compile(MultiDeleteCommand.INGREDIENT_KEYWORD + "(?<arguments>.*?)");

    /**
     * Parses the given {@code String} of arguments in the context of the MultiDeleteCommand
     * and returns a MultiDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MultiDeleteCommand parse(String args) throws ParseException {
        final Matcher matcher = MULTIDELETE_INGREDIENT_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MultiDeleteCommand.MESSAGE_USAGE));
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
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MultiDeleteCommand.MESSAGE_USAGE), pe);
        }

        Index first = indicesList.remove(0);
        return new MultiDeleteCommand(first, indicesList.toArray(Index[]::new));
    }
}
