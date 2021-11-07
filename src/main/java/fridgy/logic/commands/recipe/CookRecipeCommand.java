package fridgy.logic.commands.recipe;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.RecipeModel;
import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.recipe.Recipe;


/**
 * Cooks a recipe and deducts the required quantity from the fridge inventory
 */
public class CookRecipeCommand extends RecipeCommand {

    public static final String COMMAND_WORD = "cook";
    public static final String RECIPE_KEYWORD = "recipe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + RECIPE_KEYWORD
            + ": Cooks a recipe and deducts the quantity of ingredients from the inventory.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + RECIPE_KEYWORD + " 1";

    public static final String MESSAGE_SUCCESS = "Recipe cooked:\n%1$s";

    public static final String NOT_ENOUGH_INGR = "There are not enough ingredients in the inventory to cook:\n%1$s";

    private final Index target;

    /**
     * Instantiates a new Cook recipe command.
     *
     * @param target the target index of the recipe to be cooked.
     */
    public CookRecipeCommand(Index target) {
        requireNonNull(target);
        this.target = target;
    }

    @Override
    public CommandResult execute(RecipeModel model) throws CommandException {
        requireNonNull(model);
        List<Recipe> recipeList = model.getFilteredRecipeList();

        if (target.getZeroBased() >= recipeList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }
        Recipe recipeToCook = recipeList.get(target.getZeroBased());
        Set<BaseIngredient> recipeIngredients = recipeToCook.getIngredients();
        boolean deducted = model.deductIngredients(recipeIngredients);
        if (!deducted) {
            throw new CommandException(String.format(NOT_ENOUGH_INGR, recipeToCook));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, recipeToCook));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CookRecipeCommand // instanceof handles nulls
                && target.equals(((CookRecipeCommand) other).target));
    }
}
