package fridgy.model.recipe;

import static fridgy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Recipe's step in the recipe book.
 * Guarantees: immutable;
 */
public class Step {
    public static final String STEP_CONSTRAINTS = "Step field cannot be empty.";
    private final String step;

    /**
     * Constructs a {@code Step}.
     *
     * @param step A valid step as String.
     */
    public Step(String step) {
        requireAllNonNull(step);
        this.step = step;
    }

    public String getStep() {
        return step;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Step)) {
            return false;
        }
        Step that = (Step) o;
        return Objects.equals(step, that.step);
    }

    @Override
    public int hashCode() {
        return Objects.hash(step);
    }

    @Override
    public String toString() {
        return step;
    }
}
