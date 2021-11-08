package fridgy.logic.commands;

import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.ingredient.IngredientCommand;
import fridgy.model.IngredientModel;


/**
 * Terminates the program.
 */
public class ExitIngredientCommand extends IngredientCommand {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Fridgy...";

    @Override
    public CommandResult execute(IngredientModel model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
