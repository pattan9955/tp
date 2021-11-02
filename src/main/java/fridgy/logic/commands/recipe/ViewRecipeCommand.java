package fridgy.logic.commands.recipe;

import static java.util.Objects.requireNonNull;

import java.util.List;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.RecipeModel;
import fridgy.model.recipe.Recipe;

/**
 * Views a specific Recipe in the RecipeBook in greater detail.
 */
public class ViewRecipeCommand extends RecipeCommand {

    public static final String COMMAND_WORD = "view";
    public static final String RECIPE_KEYWORD = "recipe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + RECIPE_KEYWORD
        + ": Views a recipe by index.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " "
        + RECIPE_KEYWORD
        + " 1";

    public static final String MESSAGE_SUCCESS = "Recipe in view: %1$s";

    private final Index target;

    /**
     * Creates a DeleteRecipeCommand to delete the recipe specified at {@code Index} in the
     * last observed recipe list.
     */
    public ViewRecipeCommand(Index target) {
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

        Recipe targetRecipe = recipeList.get(target.getZeroBased());
        model.setActiveRecipe(targetRecipe);
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetRecipe));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || ((other instanceof ViewRecipeCommand) //instanceof handles nulls
            && target.equals(((ViewRecipeCommand) other).target)); // state check
    }
}
