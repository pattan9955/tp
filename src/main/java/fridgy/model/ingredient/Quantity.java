package fridgy.model.ingredient;

import static java.util.Objects.requireNonNull;

import fridgy.commons.util.AppUtil;
import fridgy.model.util.QuantityCalc;

/**
 * Represents an ingredient's quantity number in the Inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantityString(String)}
 */
public class Quantity {

    public static final String MESSAGE_CONSTRAINTS =
            "Quantity is invalid. Quantity must be a valid number greater than 0 with a valid unit (if any). \n"
                    + "Include units in grams (g) or litres (l) when applicable. \n"
                    + "Do not input units for discrete ingredients (i.e. bottle, pieces, etc.) \n"
                    + "SI prefixes for units: milli (m) and kilo (k) are accepted. \n";
    public static final String VALIDATION_REGEX = "^(?=.*[1-9])\\d+(\\.\\d+)?\\h*((m|k)?(g|l)){0,1}$";
    private final Double value;
    private final String units;

    /**
     * Constructs a {@code Quantity}. Standard Units are "kg", "l" or none for discrete.
     * Accepted prefixes are milli (m) and kilo (k).
     *
     * @param quantity A valid quantity of ingredients, converted to standard units.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);
        AppUtil.checkArgument(isValidQuantityString(quantity), MESSAGE_CONSTRAINTS);
        String standardQuantity = QuantityCalc.standardiseQuantity(quantity);
        value = parseValue(standardQuantity);
        units = parseUnits(standardQuantity);
    }

    /**
     * Returns true if a given string is a valid quantity number.
     */
    public static boolean isValidQuantityString(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        // standardised to 3 decimal places
        return String.format("%.3f %s", value, units).trim();
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

    private Double parseValue(String quantity) {
        String[] qtySplit = quantity.split(" ");
        return Double.parseDouble(qtySplit[0]);
    }

    private String parseUnits(String quantity) {
        String[] qtySplit = quantity.split(" ");
        return qtySplit.length > 1 ? qtySplit[1] : "";
    }

    /**
     * Gets units of the {@code Quantity}.
     * @return String representation of the units
     */
    public String getUnits() {
        return units;
    }

    /**
     * Gets value of the {@code Quantity}.
     * @return value as a Double
     */
    public Double getValue() {
        return value;
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
