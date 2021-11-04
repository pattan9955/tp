package fridgy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.IngredientModel;
import fridgy.model.ingredient.Ingredient;

/**
 * Deletes ingredients identified using their displayed indices from the Inventory.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String INGREDIENT_KEYWORD = "ingredient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + INGREDIENT_KEYWORD
            + ": Deletes all ingredient(s) identified by the index numbers used in the displayed ingredient list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + INGREDIENT_KEYWORD + " 1 2 3";

    public static final String MESSAGE_DELETE_INGREDIENT_SUCCESS = "Deleted %1$d ingredient%2$s!";

    private final List<Index> targetIndices = new ArrayList<>();

    /**
     * Instantiates a new Delete command for ingredients. Accepts one to infinite indices to be deleted from the
     * Inventory.
     *
     * @param targetIndices indices of any number of additional ingredients to be deleted
     */
    public DeleteCommand(Index... targetIndices) {
        requireNonNull(targetIndices);
        this.targetIndices.addAll(Arrays.asList(targetIndices));
    }

    @Override
    public CommandResult execute(IngredientModel model) throws CommandException {
        requireNonNull(model);
        final List<Ingredient> lastShownList = model.getFilteredIngredientList();
        final List<Ingredient> toDelete = new ArrayList<>();

        Set<Index> indicesSet = new HashSet<>(targetIndices);
        // reject if there are any repeated indices
        if (indicesSet.size() < targetIndices.size()) {
            throw new CommandException(Messages.MESSAGE_DELETE_REPEATED_INDICES);
        }
        // reject if there are any invalid indices
        for (Index i : targetIndices) {
            if (i.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
            }
            toDelete.add(lastShownList.get(i.getZeroBased()));
        }
        // delete all chosen ingredients
        toDelete.forEach(model::delete);

        int size = targetIndices.size();
        String plural = size == 0 || size == 1 ? "" : "s";
        return new CommandResult(String.format(MESSAGE_DELETE_INGREDIENT_SUCCESS, size, plural));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndices.equals(((DeleteCommand) other).targetIndices)); // state check
    }
}
