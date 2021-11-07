package fridgy.model.recipe;

import static fridgy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import fridgy.model.base.Eq;
import fridgy.model.ingredient.BaseIngredient;

/**
 * Represents a recipe in the recipe book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Recipe implements Eq {

    // Identity Field
    private final Name name;

    // Data Fields
    private final Set<BaseIngredient> baseIngredients;
    private final List<Step> steps;
    private final Optional<String> description;

    /**
     * Every field must be present and not null.
     */
    public Recipe(Name name, Set<BaseIngredient> baseIngredients, List<Step> steps, Optional<String> description) {
        requireAllNonNull(name, baseIngredients, steps, description);
        this.name = name;
        this.baseIngredients = baseIngredients;
        this.steps = steps;
        this.description = description;
    }

    public Name getName() {
        return name;
    }

    public Set<BaseIngredient> getIngredients() {
        return baseIngredients;
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

        if (otherRecipe == null) {
            return false;
        }

        String currRecipeName = getName().fullName.toLowerCase();
        String otherRecipeName = otherRecipe.getName().fullName.toLowerCase();
        return currRecipeName.equals(otherRecipeName);
    }

    @Override
    public boolean isSame(Eq other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Recipe) {
            return isSameRecipe((Recipe) other);
        }
        return false;
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
                && baseIngredients.equals(recipe.baseIngredients)
                && steps.equals(recipe.steps)
                && description.equals(recipe.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, baseIngredients, steps, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        Optional<String> description = getDescription();

        if (!description.equals(Optional.of(""))) {
            builder.append("; Description: ")
                    .append(description.orElse(""));
        }
        builder.append("; Ingredients used: ")
                .append(getIngredients())
                .append("; Steps:");

        int count = 1;
        for (Step step : getSteps()) {
            builder.append(" ").append(count++).append(". ").append(step);
        }

        return builder.toString();
    }
}
