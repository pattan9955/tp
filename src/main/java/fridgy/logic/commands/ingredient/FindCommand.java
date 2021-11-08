package fridgy.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.Command;
import fridgy.logic.commands.CommandResult;
import fridgy.model.IngredientModel;
import fridgy.model.ingredient.IngredientDefaultComparator;
import fridgy.model.ingredient.NameContainsKeywordsPredicate;

/**
 * Finds and lists all ingredients in Inventory whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String INGREDIENT_KEYWORD = "ingredient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + INGREDIENT_KEYWORD
            + ": Finds all ingredients whose names contain any of "
            + "the specified keywords.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + INGREDIENT_KEYWORD + " almond basil chocolate";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(IngredientModel model) {
        requireNonNull(model);
        model.updateFilteredIngredientList(predicate);
        model.sortIngredient(new IngredientDefaultComparator());

        int size = model.getFilteredIngredientList().size();
        String plural = size == 0 || size == 1 ? "" : "s";
        return new CommandResult(
                String.format(Messages.MESSAGE_INGREDIENTS_LISTED_OVERVIEW, size, plural));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
