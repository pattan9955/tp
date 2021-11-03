package fridgy.logic.commands.recipe;

import static java.util.Objects.requireNonNull;

import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.RecipeBook;
import fridgy.model.RecipeModel;

/**
 * Clears the RecipeBook.
 */
public class ClearRecipeCommand extends RecipeCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Recipes have been cleared!";
    public static final String RECIPE_KEYWORD = "recipe";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + RECIPE_KEYWORD + ": Clears all recipes.\n";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code IngredientModel} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(RecipeModel model) throws CommandException {
        requireNonNull(model);
        model.setRecipeBook(new RecipeBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClearRecipeCommand);
    }
}
