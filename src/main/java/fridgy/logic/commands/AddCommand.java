package fridgy.logic.commands;

import static fridgy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static fridgy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static fridgy.logic.parser.CliSyntax.PREFIX_NAME;
import static fridgy.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static fridgy.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.Model;
import fridgy.model.ingredient.Ingredient;

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
            + PREFIX_EMAIL + " EMAIL "
            + PREFIX_DESCRIPTION + " DESCRIPTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + " Grapes "
            + PREFIX_QUANTITY + " 4 "
            + PREFIX_EMAIL + " johnd@example.com "
            + PREFIX_DESCRIPTION + " Got any grapes? "
            + PREFIX_TAG + " fruit "
            + PREFIX_TAG + " sweet";

    public static final String MESSAGE_SUCCESS = "New ingredient added: %1$s";
    public static final String MESSAGE_DUPLICATE_INGREDIENT = "This ingredient already exists in the Inventory";

    private final Ingredient toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Ingredient}
     */
    public AddCommand(Ingredient ingredient) {
        requireNonNull(ingredient);
        toAdd = ingredient;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasIngredient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_INGREDIENT);
        }

        model.addIngredient(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
