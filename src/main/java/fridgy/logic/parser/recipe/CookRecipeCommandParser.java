package fridgy.logic.parser.recipe;

import static fridgy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fridgy.commons.core.index.Index;
import fridgy.logic.commands.recipe.CookRecipeCommand;
import fridgy.logic.parser.ParserUtil;
import fridgy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CookRecipeCommand object
 */
public class CookRecipeCommandParser implements RecipeCommandParser<CookRecipeCommand> {

    private static final Pattern COOK_RECIPE_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile(CookRecipeCommand.RECIPE_KEYWORD + "(?<arguments>.*)");

    @Override
    public CookRecipeCommand parse(String userInput) throws ParseException {
        final Matcher matcher = COOK_RECIPE_COMMAND_ARGUMENT_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CookRecipeCommand.MESSAGE_USAGE));
        }
        final String args = matcher.group("arguments");

        try {
            Index index = ParserUtil.parseIndex(args);
            return new CookRecipeCommand(index);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CookRecipeCommand.MESSAGE_USAGE), e
            );
        }
    }
}
