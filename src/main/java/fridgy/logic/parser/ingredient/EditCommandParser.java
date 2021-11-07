package fridgy.logic.parser.ingredient;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fridgy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static fridgy.logic.parser.CliSyntax.PREFIX_EXPIRY;
import static fridgy.logic.parser.CliSyntax.PREFIX_NAME;
import static fridgy.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static fridgy.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.logic.commands.ingredient.EditCommand;
import fridgy.logic.commands.ingredient.EditCommand.EditIngredientDescriptor;
import fridgy.logic.parser.ArgumentMultimap;
import fridgy.logic.parser.ArgumentTokenizer;
import fridgy.logic.parser.Parser;
import fridgy.logic.parser.ParserUtil;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    private static final Pattern INGREDIENT_EDIT_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile(EditCommand.INGREDIENT_KEYWORD + "(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        final Matcher matcher = INGREDIENT_EDIT_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE));
        }

        final String arguments = matcher.group("arguments");
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(arguments, PREFIX_NAME, PREFIX_QUANTITY, PREFIX_TAG,
                        PREFIX_DESCRIPTION, PREFIX_EXPIRY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditIngredientDescriptor editIngredientDescriptor = new EditIngredientDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editIngredientDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            editIngredientDescriptor.setQuantity(ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editIngredientDescriptor.setDescription(
                    ParserUtil.parseDescription(Optional.of(argMultimap.getValue(PREFIX_DESCRIPTION).get())));
        }
        if (argMultimap.getValue(PREFIX_EXPIRY).isPresent()) {
            editIngredientDescriptor.setExpiry(ParserUtil.parseExpiry(argMultimap.getValue(PREFIX_EXPIRY).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editIngredientDescriptor::setTags);

        if (!editIngredientDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editIngredientDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
