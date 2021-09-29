package seedu.address.model.recipe;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a recipe in the recipe book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Recipe {

    // Identity Field
    private final Name name;

    // Data Fields
    private final List<Ingredient> ingredients;
    private final List<Step> steps;
    private final Optional<String> description;

    /**
     * Every field must be present and not null.
     */
    public Recipe(Name name, List<Ingredient> ingredients, List<Step> steps, Optional<String> description) {
        requireAllNonNull(name, ingredients, steps, description);
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.description = description;
    }

    public Name getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public Optional<String> getDescription() {
        return description;
    }


    /**
     * Returns true if both recipes have the same name.
     * This defines a weaker notion of equality between two recipes.
     */
    public boolean isSameRecipe(Recipe otherRecipe) {
        if (otherRecipe == this) {
            return true;
        }

        return otherRecipe != null
                && otherRecipe.getName().equals(getName());
    }

    /**
     * Returns true if both recipes have the same identity and data fields.
     * This defines a stronger notion of equality between two recipes.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recipe)) {
            return false;
        }
        Recipe recipe = (Recipe) o;
        return name.equals(recipe.name)
                && ingredients.equals(recipe.ingredients)
                && steps.equals(recipe.steps)
                && description.equals(recipe.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ingredients, steps, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; ingredients: ")
                .append(getIngredients())
                .append("; step: ")
                .append(getSteps())
                .append("; description: ")
                .append(getDescription().orElse(""));

        return builder.toString();
    }
}
