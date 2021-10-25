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
 * Deletes a Recipe from the RecipeBook.
 */
public class DeleteRecipeCommand extends RecipeCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String RECIPE_KEYWORD = "recipe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + RECIPE_KEYWORD
            + ": Deletes the recipe by index.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + RECIPE_KEYWORD
            + " 1";

    public static final String MESSAGE_SUCCESS = "Recipe deleted:\n%1$s";

    private final Index target;

    /**
     * Creates a DeleteRecipeCommand to delete the recipe specified at {@code Index} in the
     * last observed recipe list.
     */
    public DeleteRecipeCommand(Index target) {
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
        model.delete(targetRecipe);
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetRecipe));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || ((other instanceof DeleteRecipeCommand) //instanceof handles nulls
                && target.equals(((DeleteRecipeCommand) other).target)); // state check
    }
}
