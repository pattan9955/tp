package fridgy.model.ingredient;

import static java.util.Objects.requireNonNull;

import fridgy.commons.util.AppUtil;

/**
 * Represents an ingredient's quantity number in the Inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
 */
public class Quantity {


    public static final String MESSAGE_CONSTRAINTS =
            "Quantity should be greater than zero and less than 10 digits long";
    public static final String VALIDATION_REGEX = "(?=^[0-9]*[1-9][0-9]*$)\\d{1,10}$";
    public final String value;

    /**
     * Constructs a {@code Quantity}.
     *
     * @param quantity A valid quantity number.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);
        AppUtil.checkArgument(isValidQuantity(quantity), MESSAGE_CONSTRAINTS);
        // Removes leading zeros
        value = quantity.replaceFirst("^0+(?!$)", "");
    }

    /**
     * Returns true if a given string is a valid quantity number.
     */
    public static boolean isValidQuantity(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Quantity // instanceof handles nulls
                && value.equals(((Quantity) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
