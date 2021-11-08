package fridgy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import fridgy.model.IngredientModel;
import fridgy.model.Inventory;
import fridgy.model.ingredient.Ingredient;

/**
 * Clears the Inventory or clear all expired ingredients.
 */
public class ClearCommand extends Command {

    public static final String INGREDIENT_KEYWORD = "ingredient";
    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Inventory has been cleared!";
    public static final String MESSAGE_SUCCESS_EXPIRED = "All expired ingredients has been cleared.";
    public static final String EXPIRED_KEYWORD = "expired";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + INGREDIENT_KEYWORD + " [" + EXPIRED_KEYWORD + "]"
            + ": Clears all ingredients, unless \"" + EXPIRED_KEYWORD + "\" keyword is added \n"
            + "Example: " + COMMAND_WORD + " " + INGREDIENT_KEYWORD + "\n"
            + "Example: " + COMMAND_WORD + " " + INGREDIENT_KEYWORD + " " + EXPIRED_KEYWORD;
    private final boolean isClearExpired;

    public ClearCommand(boolean isClearExpired) {
        this.isClearExpired = isClearExpired;
    }


    @Override
    public CommandResult execute(IngredientModel model) {
        requireNonNull(model);
        if (!isClearExpired) {
            model.setInventory(new Inventory());
            return new CommandResult(MESSAGE_SUCCESS);
        }

        List<Ingredient> lastShownList = model.getInventory().getList();
        List<Ingredient> toDelete = new ArrayList<>();
        for (Ingredient ingr : lastShownList) {
            if (ingr.getExpiryDate().isExpired()) {
                toDelete.add(ingr);
            }
        }
        toDelete.forEach(model::delete);
        return new CommandResult(MESSAGE_SUCCESS_EXPIRED);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClearCommand // instanceof handles nulls
                && isClearExpired == (((ClearCommand) other).isClearExpired)); // state check
    }
}
