package fridgy.logic.commands.recipe;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.RecipeModel;
import fridgy.model.recipe.Recipe;

/**
 * Deletes recipes identified using their displayed indices from the RecipeBook.
 */
public class MultiDeleteRecipeCommand extends RecipeCommand {

    public static final String COMMAND_WORD = "multidelete";
    public static final String RECIPE_KEYWORD = "recipe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + RECIPE_KEYWORD
            + ": Deletes all recipe(s) identified by the index numbers used in the displayed recipe list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + RECIPE_KEYWORD + " 1 2 3";

    public static final String MESSAGE_MULTIDELETE_RECIPE_SUCCESS = "Deleted %1$d recipes";

    private final List<Index> targetIndices = new ArrayList<>();

    /**
     * Instantiates a new MultiDeleteRecipe command for recipes. Accepts one to infinite indices to be deleted from
     * the RecipeBook.
     *
     * @param targetIndex   index of the minimal recipe to be deleted
     * @param targetIndices indices of any number of additional recipes to be deleted
     */
    public MultiDeleteRecipeCommand(Index targetIndex, Index... targetIndices) {
        requireNonNull(targetIndex);
        requireNonNull(targetIndices);
        this.targetIndices.add(targetIndex);
        this.targetIndices.addAll(Arrays.asList(targetIndices));
    }

    @Override
    public CommandResult execute(RecipeModel model) throws CommandException {
        requireNonNull(model);
        final List<Recipe> lastShownList = model.getFilteredRecipeList();
        final List<Recipe> toDelete = new ArrayList<>();

        Set<Index> indicesSet = new HashSet<>(targetIndices);
        // reject if there are any repeated indices
        if (indicesSet.size() < targetIndices.size()) {
            throw new CommandException(Messages.MESSAGE_MULTIDELETE_REPEATED_INDICES);
        }
        // reject if there are any invalid indices
        for (Index i : targetIndices) {
            if (i.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
            }
            toDelete.add(lastShownList.get(i.getZeroBased()));
        }
        // delete all chosen recipes
        toDelete.forEach(model::delete);
        return new CommandResult(String.format(MESSAGE_MULTIDELETE_RECIPE_SUCCESS, targetIndices.size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MultiDeleteRecipeCommand // instanceof handles nulls
                && targetIndices.equals(((MultiDeleteRecipeCommand) other).targetIndices)); // state check
    }
}
