package fridgy.logic.parser.ingredient;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fridgy.logic.commands.ingredient.ListCommand;
import fridgy.logic.parser.Parser;
import fridgy.logic.parser.exceptions.ParseException;

/**
 * Parses input argument and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    private static final Pattern INGREDIENT_LIST_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile(ListCommand.INGREDIENT_KEYWORD);

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        final Matcher matcher = INGREDIENT_LIST_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListCommand.MESSAGE_USAGE));
        }

        return new ListCommand();
    }

}
