package fridgy.logic.commands.recipe;

import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.RecipeModel;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class RecipeCommand {
    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code IngredientModel} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(RecipeModel model) throws CommandException;
}
