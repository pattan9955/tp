package fridgy.logic.parser;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fridgy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static fridgy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static fridgy.logic.parser.CliSyntax.PREFIX_EXPIRY;
import static fridgy.logic.parser.CliSyntax.PREFIX_NAME;
import static fridgy.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static fridgy.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.AddCommand;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.ingredient.Description;
import fridgy.model.ingredient.Email;
import fridgy.model.ingredient.ExpiryDate;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Quantity;
import fridgy.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final Pattern INGREDIENT_ADD_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile(AddCommand.INGREDIENT_KEYWORD + "(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        final Matcher matcher = INGREDIENT_ADD_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddCommand.MESSAGE_USAGE));
        }

        final String arguments = matcher.group("arguments");

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(arguments, PREFIX_NAME, PREFIX_QUANTITY, PREFIX_EMAIL,
                        PREFIX_DESCRIPTION, PREFIX_TAG, PREFIX_EXPIRY);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_QUANTITY, PREFIX_EMAIL, PREFIX_EXPIRY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Quantity quantity = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        ExpiryDate expiryDate = ParserUtil.parseExpiry(argMultimap.getValue(PREFIX_EXPIRY).get());

        assert name != null;
        assert quantity != null;
        assert expiryDate != null;

        Ingredient ingredient = new Ingredient(name, quantity, email, tagList, expiryDate);

        if (arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION)) {
            Description description = ParserUtil.parseDescription(
                    Optional.of(argMultimap.getValue(PREFIX_DESCRIPTION).orElse(null)));
            assert description != null;
            ingredient = new Ingredient(name, quantity, email, description, tagList, expiryDate);
        }

        return new AddCommand(ingredient);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
