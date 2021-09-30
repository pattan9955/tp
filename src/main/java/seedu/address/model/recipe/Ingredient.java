package seedu.address.model.recipe;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;


/**
 * Represents a Recipe's ingredient in the recipe book.
 * Guarantees: immutable;
 */
public class Ingredient {
    private final String name;

    /**
     * Constructs a {@code Ingredient}.
     *
     * @param ingredient A valid Ingredient.
     */
    public Ingredient(String ingredient) {
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
        if (!(o instanceof Ingredient)) {
            return false;
        }
        Ingredient that = (Ingredient) o;
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
