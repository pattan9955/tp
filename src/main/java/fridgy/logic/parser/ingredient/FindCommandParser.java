package fridgy.logic.parser.ingredient;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.ingredient.FindCommand;
import fridgy.logic.parser.Parser;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.ingredient.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Pattern INGREDIENT_FIND_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile(FindCommand.INGREDIENT_KEYWORD + "(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        final Matcher matcher = INGREDIENT_FIND_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        final String trimmedArgs = matcher.group("arguments").trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
