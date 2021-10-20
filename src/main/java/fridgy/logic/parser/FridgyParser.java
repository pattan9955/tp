package fridgy.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.Command;
import fridgy.logic.commands.ExitCommand;
import fridgy.logic.commands.HelpCommand;
import fridgy.logic.commands.recipe.RecipeCommand;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.logic.parser.recipe.RecipeParser;

public class FridgyParser {

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
        final String taskType = taskMatcher.group("taskType");
        switch(taskType) {

        case RECIPE_TYPE:
            RecipeCommand recipeCommand = recipeParser.parseCommand(userInput.trim());
            return recipeCommand::execute;
        case INGREDIENT_TYPE:
            Command ingredientCommand = inventoryParser.parseCommand(userInput.trim());
            return ingredientCommand::execute;
        default:
            return parseGeneralCommand(userInput);
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
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
