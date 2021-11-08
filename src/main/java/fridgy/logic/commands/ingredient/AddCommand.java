package fridgy.logic.commands.ingredient;

import static fridgy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static fridgy.logic.parser.CliSyntax.PREFIX_EXPIRY;
import static fridgy.logic.parser.CliSyntax.PREFIX_NAME;
import static fridgy.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static fridgy.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import fridgy.logic.commands.Command;
import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.IngredientModel;
import fridgy.model.ingredient.ExpiryStatusUpdater;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.IngredientDefaultComparator;


/**
 * Adds an ingredient to the Inventory.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String INGREDIENT_KEYWORD = "ingredient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + INGREDIENT_KEYWORD + ": Adds an ingredient to the Inventory. "
            + "Parameters: "
            + PREFIX_NAME + " NAME "
            + PREFIX_QUANTITY + " QUANTITY "
            + PREFIX_EXPIRY + " EXPIRY DATE "
            + "[" + PREFIX_DESCRIPTION + " DESCRIPTION] "
            + "[" + PREFIX_TAG + " TAG]...\n"
            + "Example: "
            + COMMAND_WORD + " "
            + INGREDIENT_KEYWORD + " "
            + PREFIX_NAME + " Grapes "
            + PREFIX_QUANTITY + " 4 "
            + PREFIX_DESCRIPTION + " Black seedless grapes "
            + PREFIX_EXPIRY + " 20-08-2010 "
            + PREFIX_TAG + " fruit "
            + PREFIX_TAG + " sweet";

    public static final String MESSAGE_SUCCESS = "New ingredient added:\n%1$s";
    public static final String MESSAGE_DUPLICATE_INGREDIENT = "This ingredient already exists in the Inventory";

    private final Ingredient toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Ingredient}
     */
    public AddCommand(Ingredient ingredient) {
        requireNonNull(ingredient);
        toAdd = ExpiryStatusUpdater.updateExpiryTags(ingredient);
    }

    @Override
    public CommandResult execute(IngredientModel model) throws CommandException {
        requireNonNull(model);

        if (model.has(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_INGREDIENT);
        }

        model.add(toAdd);
        model.sortIngredient(new IngredientDefaultComparator());
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
