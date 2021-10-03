package fridgy.model.ingredient;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import fridgy.commons.util.AppUtil;

/**
 * Represents an ingredient's description in the Inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(Optional)}
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS = "Descriptions can take any values, and it should not be blank";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * Only accepts ASCII characters.
     */
    public static final String VALIDATION_REGEX = "^[\\p{ASCII}][\\p{ASCII} ]*";

    public final Optional<String> value;

    /**
     * Constructs an {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(Optional<String> description) {
        requireNonNull(description);
        AppUtil.checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        value = description;
    }

    /**
     * Returns true if a given string is a valid description.
     * @param test
     */
    public static boolean isValidDescription(Optional<String> test) {
        return test.equals(Optional.empty()) || test.get().matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.orElse("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
