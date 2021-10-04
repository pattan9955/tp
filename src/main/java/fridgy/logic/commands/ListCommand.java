package fridgy.logic.commands;

import static java.util.Objects.requireNonNull;

import fridgy.model.Model;

/**
 * Lists all ingredients in the Inventory to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String INGREDIENT_KEYWORD = "ingredient";

    public static final String MESSAGE_SUCCESS = "Listed all ingredients";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredIngredientList(Model.PREDICATE_SHOW_ALL_INGREDIENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
