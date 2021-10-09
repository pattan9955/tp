package fridgy.logic.commands;

import static java.util.Objects.requireNonNull;

import fridgy.model.IngredientModel;
import fridgy.model.Inventory;


/**
 * Clears the Inventory.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Description book has been cleared!";


    @Override
    public CommandResult execute(IngredientModel model) {
        requireNonNull(model);
        model.setInventory(new Inventory());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
