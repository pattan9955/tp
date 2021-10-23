package fridgy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.IngredientModel;
import fridgy.model.ingredient.Ingredient;

public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String INGREDIENT_KEYWORD = "ingredient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + INGREDIENT_KEYWORD
            + ": view the ingredient identified by the index number used in the displayed ingredient list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + INGREDIENT_KEYWORD + " 1";

    public static final String MESSAGE_SUCCESS = "Viewing ingredient:\n%1$s";

    private final Index target;

    /**
     * Instantiates a new View command.
     *
     * @param target the target index of the ingredient to view
     */
    public ViewCommand(Index target) {
        requireNonNull(target);
        this.target = target;
    }

    @Override
    public CommandResult execute(IngredientModel model) throws CommandException {
        requireNonNull(model);
        List<Ingredient> ingredientList = model.getFilteredIngredientList();

        if (target.getZeroBased() >= ingredientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
        }

        Ingredient targetIngredient = ingredientList.get(target.getZeroBased());
        model.setActiveIngredient(targetIngredient);
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIngredient));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || ((other instanceof ViewCommand) //instanceof handles nulls
            && target.equals(((ViewCommand) other).target)); // state check
    }

}
