package fridgy.logic.parser.recipe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.HelpCommand;
import fridgy.logic.commands.recipe.AddRecipeCommand;
import fridgy.logic.commands.recipe.ClearRecipeCommand;
import fridgy.logic.commands.recipe.CookRecipeCommand;
import fridgy.logic.commands.recipe.DeleteRecipeCommand;
import fridgy.logic.commands.recipe.EditRecipeCommand;
import fridgy.logic.commands.recipe.FindRecipeCommand;
import fridgy.logic.commands.recipe.ListRecipeCommand;
import fridgy.logic.commands.recipe.RecipeCommand;
import fridgy.logic.commands.recipe.ViewRecipeCommand;
import fridgy.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class RecipeParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecipeCommand parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case AddRecipeCommand.COMMAND_WORD:
            return new AddRecipeCommandParser().parse(arguments);
        case FindRecipeCommand.COMMAND_WORD:
            return new FindRecipeCommandParser().parse(arguments);
        case ListRecipeCommand.COMMAND_WORD:
            return new ListRecipeCommandParser().parse(arguments);
        case ViewRecipeCommand.COMMAND_WORD:
            return new ViewRecipeCommandParser().parse(arguments);
        case CookRecipeCommand.COMMAND_WORD:
            return new CookRecipeCommandParser().parse(arguments);
        case EditRecipeCommand.COMMAND_WORD:
            return new EditRecipeCommandParser().parse(arguments);
        case DeleteRecipeCommand.COMMAND_WORD:
            return new DeleteRecipeCommandParser().parse(arguments);
        case ClearRecipeCommand.COMMAND_WORD:
            return new ClearRecipeCommandParser().parse(arguments);
        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
