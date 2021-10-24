package fridgy.logic.parser.recipe;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fridgy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static fridgy.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static fridgy.logic.parser.CliSyntax.PREFIX_NAME;
import static fridgy.logic.parser.CliSyntax.PREFIX_STEP;
import static java.util.Objects.requireNonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fridgy.commons.core.index.Index;
import fridgy.logic.commands.recipe.EditRecipeCommand;
import fridgy.logic.commands.recipe.EditRecipeCommand.EditRecipeDescriptor;
import fridgy.logic.parser.ArgumentMultimap;
import fridgy.logic.parser.ArgumentTokenizer;
import fridgy.logic.parser.ParserUtil;
import fridgy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditRecipeCommand object.
 */
public class EditRecipeCommandParser implements RecipeCommandParser<EditRecipeCommand> {

    private static final Pattern RECIPE_EDIT_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile(EditRecipeCommand.RECIPE_KEYWORD + "(?<arguments>.*)");

    @Override
    public EditRecipeCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        final Matcher matcher = RECIPE_EDIT_COMMAND_ARGUMENT_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditRecipeCommand.MESSAGE_USAGE));
        }

        final String args = matcher.group("arguments");
        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, PREFIX_NAME, PREFIX_INGREDIENT, PREFIX_STEP, PREFIX_DESCRIPTION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, EditRecipeCommand.MESSAGE_USAGE), pe);
        }

        EditRecipeDescriptor descriptor = new EditRecipeDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            descriptor.setName(ParserUtil.parseRecipeName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_INGREDIENT).isPresent()) {
            descriptor.setIngredients(ParserUtil
                    .parseIngredients(argMultimap.getAllValues(PREFIX_INGREDIENT)));
        }
        if (argMultimap.getValue(PREFIX_STEP).isPresent()) {
            descriptor.setSteps(ParserUtil.parseSteps(argMultimap.getAllValues(PREFIX_STEP)));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            descriptor.setDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        }

        if (!descriptor.isAnyFieldEdited()) {
            throw new ParseException(EditRecipeCommand.MESSAGE_NOT_EDITED);
        }

        return new EditRecipeCommand(index, descriptor);
    }
}
