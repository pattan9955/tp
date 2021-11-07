package fridgy.model.ingredient;

import static java.util.Objects.requireNonNull;

import fridgy.commons.util.AppUtil;
import fridgy.model.util.QuantityCalc;

/**
 * Represents an ingredient's quantity number in the Inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
 */
public class Quantity {

    public static final String MESSAGE_CONSTRAINTS =
            "Quantity is invalid. Quantity must be a valid number greater than 0 with a valid unit (if any). \n"
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

    /**
     * Gets units of the {@code Quantity}.
     * @return String representation of the units
     */
    public String getUnits() {
        String[] valueAndUnit = value.split("\\h");
        return valueAndUnit.length <= 1 ? "" : valueAndUnit[1];
    }

    /**
     * Gets value of the {@code Quantity}.
     * @return value as a Double
     */
    public Double getValue() {
        String[] valueAndUnit = value.split("\\h");
        return Double.parseDouble(valueAndUnit[0]);
    }

    /**
     * Naively deducts quantity of newQuantity from this quantity, with the assumption both have the same units.
     * Returns a {@code double}, regardless of units of {@code Quantity}.
     *
     * @param newQuantity to be compared to
     * @return double value of (quantity - newQuantity)
     */
    public double compareQtyWithSameUnits(Quantity newQuantity) {
        requireNonNull(newQuantity);
        assert getUnits().equals(newQuantity.getUnits());

        Double qty1 = getValue();
        Double qty2 = newQuantity.getValue();
        return qty1 - qty2;
    }
}
