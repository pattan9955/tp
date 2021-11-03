package fridgy.logic.commands.recipe;

import static java.util.Objects.requireNonNull;

import fridgy.logic.commands.CommandResult;
import fridgy.model.Model;
import fridgy.model.RecipeModel;

/**
 * Lists all recipes in the recipe book to the user.
 */
public class ListRecipeCommand extends RecipeCommand {

    public static final String COMMAND_WORD = "list";
    public static final String RECIPE_KEYWORD = "recipe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + RECIPE_KEYWORD + ": Lists all recipes.\n";

    public static final String MESSAGE_SUCCESS = "Listed all recipes!";


    @Override
    public CommandResult execute(RecipeModel model) {
        requireNonNull(model);
        model.updateFilteredRecipeList(Model.PREDICATE_SHOW_ALL_RECIPES);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListRecipeCommand); // instanceof handles nulls
    }
}
