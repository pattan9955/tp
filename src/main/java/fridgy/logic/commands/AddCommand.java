package fridgy.logic.commands;

import static fridgy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static fridgy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static fridgy.logic.parser.CliSyntax.PREFIX_NAME;
import static fridgy.logic.parser.CliSyntax.PREFIX_PHONE;
import static fridgy.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.IngredientModel;
import fridgy.model.ingredient.Ingredient;


/**
 * Adds a ingredient to the Inventory.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a ingredient to the Inventory. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

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
    public CommandResult execute(IngredientModel model) throws CommandException {
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
