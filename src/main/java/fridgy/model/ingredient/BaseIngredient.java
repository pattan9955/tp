package fridgy.model.ingredient;

import static fridgy.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Objects;

import fridgy.model.base.Eq;

/**
 * Represents a Base ingredient to be used by Recipe.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class BaseIngredient implements Eq {
    public static final String MESSAGE_CONSTRAINTS =
        "Base Ingredient should have the format of <Name> <Quantity>";
    public static final String BASE_INGREDIENT_CONSTRAINTS = "Ingredient field cannot be empty.";

    // Identity fields
    private final Name name;
    private final Quantity quantity;

    /**
     * Constructs a base ingredient reference with the given name and quantity.
     */
    public BaseIngredient(Name name, Quantity quantity) {
        requireAllNonNull(name, quantity);
        this.name = name;
        this.quantity = quantity;
    }


    public Name getName() {
        return name;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * Returns true if both ingredients have the same name.
     * This defines a weaker notion of equality between two ingredients.
     */
    public boolean isSameIngredient(BaseIngredient otherBaseIngredient) {
        if (otherBaseIngredient == this) {
            return true;
        }

        return otherBaseIngredient != null
                && otherBaseIngredient.getName().equals(getName());
    }

    @Override
    public boolean isSame(Eq other) {
        if (other == this) {
            return true;
        }
        if (other instanceof BaseIngredient) {
            return isSameIngredient((BaseIngredient) other);
        }
        return false;
    }

    /**
     * Returns true if both ingredients have the same identity and data fields.
     * This defines a stronger notion of equality between two ingredients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof BaseIngredient)) {
            return false;
        }

        BaseIngredient otherBaseIngredient = (BaseIngredient) other;
        return otherBaseIngredient.getName().equals(getName())
                && otherBaseIngredient.getQuantity().equals(getQuantity());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, quantity);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Quantity: ")
                .append(getQuantity());

        return builder.toString();
    }

    /**
     * Naively deducts quantity of otherIngr from this ingredient.
     * Returns a {@code double}, disregards of units of {@code Quantity} in this calculation.
     *
     * @param otherIngr
     * @return double (quantity of this ingredient - quantity of otherIngr)
     */
    public double getQuantityDiff(BaseIngredient otherIngr) {
        requireNonNull(otherIngr);
        Quantity qty1 = getQuantity();
        Quantity qty2 = otherIngr.getQuantity();
        return qty1.compareQtyWithSameUnits(qty2);

    }

    /**
     * Matches this ingredient with another {@code BaseIngredient} to see if they match in {@code Name} and
     * {@code Quantity} units.{@code Name} is matched ignoring case.
     * Returns true if they are matched, false if they do not match.
     *
     * @param ingredient to be compared
     * @return boolean that indicates if the two ingredients have the same name and quantity units
     */
    public boolean isComparable(BaseIngredient ingredient) {
        requireNonNull(ingredient);
        // check name of ingredients ignoring case
        if (!isSameIngredient(ingredient)) {
            return false;
        }
        // check units of quantity
        String ingr1Units = getQtyUnits();
        String ingr2Units = ingredient.getQtyUnits();
        return ingr1Units.equals(ingr2Units);
    }

    private String getQtyUnits() {
        return getQuantity().getUnits();
    }
}
