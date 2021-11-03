package fridgy.logic.parser;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fridgy.logic.commands.ClearCommand;
import fridgy.logic.parser.exceptions.ParseException;

public class ClearCommandParser implements Parser<ClearCommand> {

    private static final Pattern CLEAR_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile(ClearCommand.INGREDIENT_KEYWORD + "(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * and returns a ClearCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearCommand parse(String args) throws ParseException {
        final Matcher matcher = CLEAR_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ClearCommand.MESSAGE_USAGE));
        }
        final String arguments = matcher.group("arguments").trim();
        if (arguments.equals(ClearCommand.EXPIRED_KEYWORD)) {
            return new ClearCommand(true);
        } else if (arguments.isEmpty()) {
            return new ClearCommand(false);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        }
    }
}
