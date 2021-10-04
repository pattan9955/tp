package fridgy.model.recipe;

import static fridgy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;


/**
 * Represents a Recipe's ingredient in the recipe book.
 * Guarantees: immutable;
 */
public class RecipeIngredient {
    private final String name;

    /**
     * Constructs a {@code RecipeIngredient}.
     *
     * @param ingredient A valid RecipeIngredient.
     */
    public RecipeIngredient(String ingredient) {
        requireAllNonNull(ingredient);
        this.name = ingredient;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipeIngredient)) {
            return false;
        }
        RecipeIngredient that = (RecipeIngredient) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
