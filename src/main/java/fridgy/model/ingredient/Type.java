package fridgy.model.ingredient;

import static java.util.Objects.requireNonNull;

import java.util.Map;

import fridgy.commons.util.AppUtil;

/**
 * Represents an Ingredient's
 * Guarantees: immutable; is valid as declared in {@link #isValidType(String)}
 */
public class Type {

    public static final String MESSAGE_CONSTRAINTS =
            "Type of ingredient can be either 'solid', 'liquid' or 'discrete'.";
    public static final String VALIDATION_REGEX = "(?i)^discrete$|^solid$|^liquid$";
    private static final Map<String, String> UNIT_MAPPING = Map.of(
            "solid", "grams",
            "liquid", "ml",
            "discrete", "pieces"
    );
    public final String value;

    /**
     * Constructs a {@code Type}.
     *
     * @param value A valid type.
     */
    public Type(String value) {
        requireNonNull(value);
        AppUtil.checkArgument(isValidType(value), MESSAGE_CONSTRAINTS);
        this.value = value.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid type.
     */
    public static boolean isValidType(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the unit of the ingredient.
     *
     * @return the string of the unit for the type of ingredient
     */
    public String getUnit() {
        return UNIT_MAPPING.get(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Type // instanceof handles nulls
                && value.equals(((Type) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
