package fridgy.logic.commands.recipe;

import static fridgy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static fridgy.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static fridgy.logic.parser.CliSyntax.PREFIX_NAME;
import static fridgy.logic.parser.CliSyntax.PREFIX_STEP;
import static java.util.Objects.requireNonNull;

import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.RecipeModel;
import fridgy.model.recipe.Recipe;

/**
 * Adds a Recipe to the RecipeBook.
 */
public class AddRecipeCommand extends RecipeCommand {

    public static final String COMMAND_WORD = "add";
    public static final String RECIPE_KEYWORD = "recipe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + RECIPE_KEYWORD
            + ": Adds a recipe to the RecipeBook.\n"
            + "Parameters: "
            + PREFIX_NAME + " NAME "
            + PREFIX_INGREDIENT + " INGREDIENTS "
            + "[" + PREFIX_STEP + " STEPS " + "]"
            + "[" + PREFIX_DESCRIPTION + " DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD + " " + RECIPE_KEYWORD + " "
            + PREFIX_NAME + " Burger "
            + PREFIX_INGREDIENT + " Buns 2 "
            + PREFIX_INGREDIENT + " Patty 1 "
            + PREFIX_STEP + " Toast the buns. "
            + PREFIX_STEP + " Put the patty between the buns. "
            + PREFIX_DESCRIPTION + " Great burger! ";

    public static final String MESSAGE_SUCCESS = "New recipe added:\n%1$s";
    public static final String MESSAGE_DUPLICATE_RECIPE = "This recipe already exists in the Inventory";

    private final Recipe toAdd;

    /**
     * Creates an AddRecipeCommand to add the specified {@code Recipe}
     */
    public AddRecipeCommand(Recipe recipe) {
        requireNonNull(recipe);
        toAdd = recipe;
    }

    @Override
    public CommandResult execute(RecipeModel model) throws CommandException {
        requireNonNull(model);

        if (model.has(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECIPE);
        }

        model.add(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRecipeCommand // instanceof handles nulls
                && toAdd.equals(((AddRecipeCommand) other).toAdd));
    }
}
