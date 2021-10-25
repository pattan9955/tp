package fridgy.logic.commands.recipe;

import static java.util.Objects.requireNonNull;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.CommandResult;
import fridgy.model.RecipeModel;
import fridgy.model.recipe.NameContainsKeywordsPredicate;

/**
 * Finds a Recipe by name in the RecipeBook.
 */
public class FindRecipeCommand extends RecipeCommand {
    public static final String COMMAND_WORD = "find";
    public static final String RECIPE_KEYWORD = "recipe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + RECIPE_KEYWORD
            + ": Finds all recipes whose names contain any of "
            + "the specified keywords.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + RECIPE_KEYWORD + " burger salad tempura";

    private final NameContainsKeywordsPredicate predicate;

    /**
     * Creates a FindRecipeCommand to search for recipes which fulfill the specified {@code predicate}
     */
    public FindRecipeCommand(NameContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(RecipeModel model) {
        requireNonNull(model);
        model.updateFilteredRecipeList(predicate);

        int size = model.getFilteredRecipeList().size();
        String plural = size == 0 || size == 1 ? "" : "s";
        return new CommandResult(
                String.format(Messages.MESSAGE_RECIPES_LISTED_OVERVIEW, size, plural));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindRecipeCommand // instanceof handles nulls
                && predicate.equals(((FindRecipeCommand) other).predicate)); // state check
    }
}
