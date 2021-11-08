package fridgy.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.commons.util.CollectionUtil;
import fridgy.logic.commands.Command;
import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.logic.parser.CliSyntax;
import fridgy.model.IngredientModel;
import fridgy.model.ingredient.Description;
import fridgy.model.ingredient.ExpiryDate;
import fridgy.model.ingredient.ExpiryStatusUpdater;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.IngredientDefaultComparator;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Quantity;
import fridgy.model.tag.Tag;

/**
 * Edits the details of an existing ingredient in the Inventory.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String INGREDIENT_KEYWORD = "ingredient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + INGREDIENT_KEYWORD + ": Edits details of the ingredient. "
            + "Existing values are replaced with input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + CliSyntax.PREFIX_NAME + " NAME] "
            + "[" + CliSyntax.PREFIX_QUANTITY + " QUANTITY] "
            + "[" + CliSyntax.PREFIX_EXPIRY + " EXPIRY DATE] "
            + "[" + CliSyntax.PREFIX_DESCRIPTION + " DESCRIPTION] "
            + "[" + CliSyntax.PREFIX_TAG + " TAG]...\n"
            + "Example: " + COMMAND_WORD + " " + INGREDIENT_KEYWORD + " 1 "
            + CliSyntax.PREFIX_QUANTITY + " 69";

    public static final String MESSAGE_EDIT_INGREDIENT_SUCCESS = "Edited Ingredient:\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_INGREDIENT = "This ingredient already exists in the Inventory.";

    private final Index index;
    private final EditIngredientDescriptor editIngredientDescriptor;

    /**
     * @param index of the ingredient in the filtered ingredient list to edit
     * @param editIngredientDescriptor details to edit the ingredient with
     */
    public EditCommand(Index index, EditIngredientDescriptor editIngredientDescriptor) {
        requireNonNull(index);
        requireNonNull(editIngredientDescriptor);

        this.index = index;
        this.editIngredientDescriptor = new EditIngredientDescriptor(editIngredientDescriptor);
    }

    @Override
    public CommandResult execute(IngredientModel model) throws CommandException {
        requireNonNull(model);
        List<Ingredient> lastShownList = model.getFilteredIngredientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
        }

        Ingredient ingredientToEdit = lastShownList.get(index.getZeroBased());
        Ingredient editedIngredient = createEditedIngredient(ingredientToEdit, editIngredientDescriptor);

        if (!ingredientToEdit.isSameIngredient(editedIngredient) && model.has(editedIngredient)) {
            throw new CommandException(MESSAGE_DUPLICATE_INGREDIENT);
        }

        model.set(ingredientToEdit, editedIngredient);
        model.updateFilteredIngredientList(IngredientModel.PREDICATE_SHOW_ALL_INGREDIENTS);
        model.sortIngredient(new IngredientDefaultComparator());
        return new CommandResult(String.format(MESSAGE_EDIT_INGREDIENT_SUCCESS, editedIngredient));
    }

    /**
     * Creates and returns a {@code Ingredient} with the details of {@code ingredientToEdit}
     * edited with {@code editIngredientDescriptor}.
     */
    private static Ingredient createEditedIngredient(Ingredient ingredientToEdit,
                                                     EditIngredientDescriptor editIngredientDescriptor) {
        assert ingredientToEdit != null;

        Name updatedName = editIngredientDescriptor.getName().orElse(ingredientToEdit.getName());
        Quantity updatedQuantity = editIngredientDescriptor.getQuantity().orElse(ingredientToEdit.getQuantity());
        Description updatedDescription = editIngredientDescriptor.getDescription()
                .orElse(ingredientToEdit.getDescription());
        Set<Tag> updatedTags = editIngredientDescriptor.getTags().orElse(ingredientToEdit.getTags());
        ExpiryDate updatedExpiryDate = editIngredientDescriptor.getExpiryDate()
                .orElse(ingredientToEdit.getExpiryDate());

        Ingredient editedIngredient = new Ingredient(updatedName, updatedQuantity, updatedDescription,
                updatedTags, updatedExpiryDate);
        return ExpiryStatusUpdater.updateExpiryTags(editedIngredient);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editIngredientDescriptor.equals(e.editIngredientDescriptor);
    }

    /**
     * Stores the details to edit the ingredient with. Each non-empty field value will replace the
     * corresponding field value of the ingredient.
     */
    public static class EditIngredientDescriptor {
        private Name name;
        private Quantity quantity;
        private Description description;
        private Set<Tag> tags;
        private ExpiryDate expiryDate;

        public EditIngredientDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditIngredientDescriptor(EditIngredientDescriptor toCopy) {
            setName(toCopy.name);
            setQuantity(toCopy.quantity);
            setDescription(toCopy.description);
            setTags(toCopy.tags);
            setExpiry(toCopy.expiryDate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, quantity, description, tags, expiryDate);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        public Optional<Quantity> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setExpiry(ExpiryDate expiryDate) {
            this.expiryDate = expiryDate;
        }

        public Optional<ExpiryDate> getExpiryDate() {
            return Optional.ofNullable(expiryDate);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditIngredientDescriptor)) {
                return false;
            }

            // state check
            EditIngredientDescriptor e = (EditIngredientDescriptor) other;

            return getName().equals(e.getName())
                    && getQuantity().equals(e.getQuantity())
                    && getDescription().equals(e.getDescription())
                    && getTags().equals(e.getTags())
                    && getExpiryDate().equals(e.getExpiryDate());
        }
    }
}
