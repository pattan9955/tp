package fridgy.logic.commands.recipe;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.commons.util.CollectionUtil;
import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.logic.parser.CliSyntax;
import fridgy.model.RecipeModel;
import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.recipe.Name;
import fridgy.model.recipe.Recipe;
import fridgy.model.recipe.Step;

/**
 * Edits a Recipe in the RecipeBook.
 */
public class EditRecipeCommand extends RecipeCommand {

    public static final String COMMAND_WORD = "edit";
    public static final String RECIPE_KEYWORD = "recipe";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + RECIPE_KEYWORD + ": Edits the details of the recipe identified "
            + "by the index number used in the displayed recipe list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + CliSyntax.PREFIX_NAME + " NAME] "
            + "[" + CliSyntax.PREFIX_DESCRIPTION + " DESCRIPTION] "
            + "[" + CliSyntax.PREFIX_INGREDIENT + " INGREDIENT]... "
            + "[" + CliSyntax.PREFIX_STEP + " STEP]...\n"
            + "Example: " + COMMAND_WORD + " " + RECIPE_KEYWORD
            + " 1 " + CliSyntax.PREFIX_DESCRIPTION + " Juicy Burger";

    public static final String MESSAGE_EDIT_RECIPE_SUCCESS = "Edited Recipe:\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RECIPE = "This recipe already exists in the RecipeBook.";

    private final Index index;
    private final EditRecipeDescriptor descriptor;

    /**
     * Creates an EditRecipeCommand to edit the specified Recipe at {@code Index} using {@code EditRecipeDescriptor}.
     */
    public EditRecipeCommand(Index index, EditRecipeDescriptor descriptor) {
        requireNonNull(index);
        requireNonNull(descriptor);

        this.index = index;
        this.descriptor = descriptor;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code RecipeModel} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(RecipeModel model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredRecipeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        Recipe recipeToEdit = lastShownList.get(index.getZeroBased());
        Recipe editedRecipe = createEditedRecipe(recipeToEdit, descriptor);

        if (!recipeToEdit.isSameRecipe(editedRecipe) && model.has(editedRecipe)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECIPE);
        }

        model.set(recipeToEdit, editedRecipe);
        model.updateFilteredRecipeList(RecipeModel.PREDICATE_SHOW_ALL_RECIPES);
        return new CommandResult(String.format(MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe));
    }

    private static Recipe createEditedRecipe(Recipe toEdit, EditRecipeDescriptor descriptor) {
        requireNonNull(toEdit);

        Name updatedName = descriptor.getName().orElse(toEdit.getName());
        Optional<String> updatedDescription = Optional.of(descriptor.getDescription()
                .orElse(toEdit.getDescription().orElse("")));
        Set<BaseIngredient> updatedIngredients = descriptor.getIngredients()
                .orElse(toEdit.getIngredients());
        List<Step> updatedSteps = descriptor.getSteps()
                .orElse(toEdit.getSteps());

        Recipe editedRecipe = new Recipe(updatedName, updatedIngredients, updatedSteps, updatedDescription);
        return editedRecipe;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EditRecipeCommand)) {
            return false;
        }
        EditRecipeCommand otherCommand = (EditRecipeCommand) other;
        return this.index.equals(otherCommand.index)
                && this.descriptor.equals(otherCommand.descriptor);
    }

    public static class EditRecipeDescriptor {
        private Name name;
        private Set<BaseIngredient> ingredients;
        private List<Step> steps;
        private String description;

        public EditRecipeDescriptor() {}

        /** Copies an {@code EditRecipeDescriptor} for use in describing fields to edit. */
        public EditRecipeDescriptor(EditRecipeDescriptor toCopy) {
            setName(toCopy.name);
            setIngredients(toCopy.ingredients);
            setSteps(toCopy.steps);
            setDescription(toCopy.description);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, ingredients, steps, description);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setIngredients(Set<BaseIngredient> ingredients) {
            this.ingredients = (ingredients != null) ? new HashSet<>(ingredients) : null;
        }

        public void setSteps(List<Step> steps) {
            this.steps = (steps != null) ? new ArrayList<>(steps) : null;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        public Optional<Set<BaseIngredient>> getIngredients() {
            return (ingredients != null)
                    ? Optional.of(Collections.unmodifiableSet(ingredients))
                    : Optional.empty();
        }

        public Optional<List<Step>> getSteps() {
            return (steps != null)
                    ? Optional.of(Collections.unmodifiableList(steps))
                    : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditRecipeDescriptor)) {
                return false;
            }

            EditRecipeDescriptor e = (EditRecipeDescriptor) other;

            return getName().equals(e.getName())
                    && getDescription().equals(e.getDescription())
                    && getIngredients().equals(e.getIngredients())
                    && getSteps().equals(e.getSteps());
        }
    }
}
