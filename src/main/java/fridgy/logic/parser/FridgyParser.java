package fridgy.logic.parser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.AddCommand;
import fridgy.logic.commands.ClearCommand;
import fridgy.logic.commands.Command;
import fridgy.logic.commands.DeleteCommand;
import fridgy.logic.commands.EditCommand;
import fridgy.logic.commands.ExitCommand;
import fridgy.logic.commands.FindCommand;
import fridgy.logic.commands.HelpCommand;
import fridgy.logic.commands.ListCommand;
import fridgy.logic.commands.ViewCommand;
import fridgy.logic.commands.recipe.CookRecipeCommand;
import fridgy.logic.commands.recipe.RecipeCommand;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.logic.parser.recipe.RecipeParser;

/**
 * Parses user input.
 */
public class FridgyParser {

    public static final List<String> COMMAND_NAMES = List.of(AddCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD,
            ViewCommand.COMMAND_WORD,
            CookRecipeCommand.COMMAND_WORD
    );
    private static final String RECIPE_TYPE = "recipe";
    private static final String INGREDIENT_TYPE = "ingredient";
    private static final Pattern TYPED_COMMAND_FORMAT = Pattern
            .compile("(?<commandWord>\\S+)\\s?(?<taskType>\\S*)?\\s?(?<arguments>.*)?");
    private static final Pattern GENERAL_COMMAND_FORMAT = Pattern
            .compile("(?<commandWord>^\\S*$)");

    private RecipeParser recipeParser;
    private InventoryParser inventoryParser;

    /**
     * Initializes the Command Parser for Fridgy.
     */
    public FridgyParser() {
        this.recipeParser = new RecipeParser();
        this.inventoryParser = new InventoryParser();
    }

    /**
     * Parses user input into a CommandExecutor executable that can be run to
     * produce a CommandResult.
     *
     * @param userInput The user input to be parsed.
     * @return A CommandExecutor that executes the command when provided a model.
     * @throws ParseException If user provides invalid input.
     */
    public CommandExecutor parseCommand(String userInput) throws ParseException {
        final Matcher taskMatcher = TYPED_COMMAND_FORMAT.matcher(userInput.trim());
        if (!taskMatcher.matches()) {
            return parseGeneralCommand(userInput);
        }
        final String commandWord = taskMatcher.group("commandWord");
        switch(commandWord) {

        case ExitCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_WORD: {
            return parseGeneralCommand(userInput);
        }
        default:
            return parseCommandWithType(userInput);
        }
    }

    /**
     * Parses user input with type into a CommandExecutor executable that can be run to
     * produce a CommandResult.
     *
     * @param userInput The user input to be parsed.
     * @return A CommandExecutor that executes the command when provided a model.
     * @throws ParseException If user provides invalid input.
     */
    private CommandExecutor parseCommandWithType(String userInput) throws ParseException {
        final Matcher taskMatcher = TYPED_COMMAND_FORMAT.matcher(userInput.trim());
        if (!taskMatcher.matches()) {
            return parseGeneralCommand(userInput);
        }
        final String commandWord = taskMatcher.group("commandWord");
        final String taskType = taskMatcher.group("taskType");
        switch(taskType) {

        case RECIPE_TYPE:
            RecipeCommand recipeCommand = recipeParser.parseCommand(userInput.trim());
            return recipeCommand::execute;
        case INGREDIENT_TYPE:
            Command ingredientCommand = inventoryParser.parseCommand(userInput.trim());
            return ingredientCommand::execute;
        case "": // no type
            return parseGeneralCommand(userInput);
        default: // invalid type
            if (COMMAND_NAMES.contains(commandWord)) {
                if (commandWord.equals(CookRecipeCommand.COMMAND_WORD)) {
                    recipeParser.parseCommand(userInput.trim());
                } else {
                    throw new ParseException(Messages.TYPE_INVALID_COMMAND_FORMAT + " "
                            + formatString(taskType)
                            + ". " + Messages.TYPE_EXPECTED);
                }
            }
            // invalid command
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND + " "
                    + formatString(commandWord) + ".");
        }
    }


    private CommandExecutor parseGeneralCommand(String userInput) throws ParseException {
        final Matcher matcher = GENERAL_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");

        switch (commandWord) {
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand()::execute;

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand()::execute;

        default:
            if (COMMAND_NAMES.contains(commandWord)) {
                if (commandWord.equals(CookRecipeCommand.COMMAND_WORD)) {
                    recipeParser.parseCommand(userInput.trim());
                } else {
                    throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                            Messages.MISSING_TYPE));
                }
            }
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND + " "
                    + formatString(commandWord) + ".");
        }
    }

    /**
     * Appends apostrophes to a string for error messages.
     */
    public String formatString(String input) {
        return "'" + input + "'";
    }
}
