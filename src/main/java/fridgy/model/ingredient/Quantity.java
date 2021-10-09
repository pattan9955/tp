package fridgy.model.ingredient;

import static java.util.Objects.requireNonNull;

import fridgy.commons.util.AppUtil;
import fridgy.model.QuantityCalc;

/**
 * Represents an ingredient's quantity number in the Inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
 */
public class Quantity {


    public static final String MESSAGE_CONSTRAINTS =
            "Quantity should be greater than zero. \n"
                    + "Include units in grams (g) or litres (l) when applicable. \n"
                    + "Do not input units for discrete ingredients (i.e. bottle, pieces, etc.) \n"
                    + "SI prefixes for units: milli (m) and kilo (k) are accepted. \n";
    public static final String VALIDATION_REGEX = "^(?=.*[1-9])\\d+(\\.\\d+)?\\h*((m|k)?(g|l)){0,1}$";
    public final String value;

    /**
     * Constructs a {@code Quantity}. Standard Units are "kg", "l" or none for discrete.
     * Accepted prefixes are milli (m) and kilo (k).
     *
     * @param quantity A valid quantity of ingredients, converted to standard units.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);
        AppUtil.checkArgument(isValidQuantity(quantity), MESSAGE_CONSTRAINTS);
        // Removes leading zeros
        quantity = quantity.replaceFirst("^0+(?!$)", "");
        value = convertToStandardUnit(quantity);
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

    private String convertToStandardUnit(String quantity) {
        return QuantityCalc.convertToStandardUnits(quantity);
    }
}
