package fridgy.model.ingredient;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import fridgy.commons.util.CollectionUtil;
import fridgy.model.tag.Tag;

/**
 * Represents a Ingredient in the Inventory.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Ingredient {

    // Identity fields
    private final Name name;
    private final Quantity quantity;
    private final Email email;

    // Data fields
    private final Description address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Ingredient(Name name, Quantity quantity, Email email, Description address, Set<Tag> tags) {
        CollectionUtil.requireAllNonNull(name, quantity, email, address, tags);
        this.name = name;
        this.quantity = quantity;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Email getEmail() {
        return email;
    }

    public Description getDescription() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both ingredients have the same name.
     * This defines a weaker notion of equality between two ingredients.
     */
    public boolean isSameIngredient(Ingredient otherIngredient) {
        if (otherIngredient == this) {
            return true;
        }

        return otherIngredient != null
                && otherIngredient.getName().equals(getName());
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

        if (!(other instanceof Ingredient)) {
            return false;
        }

        Ingredient otherIngredient = (Ingredient) other;
        return otherIngredient.getName().equals(getName())
                && otherIngredient.getQuantity().equals(getQuantity())
                && otherIngredient.getEmail().equals(getEmail())
                && otherIngredient.getDescription().equals(getDescription())
                && otherIngredient.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, quantity, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Quantity: ")
                .append(getQuantity())
                .append("; Email: ")
                .append(getEmail())
                .append("; Description: ")
                .append(getDescription());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
