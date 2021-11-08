package fridgy.model.ingredient;

import static fridgy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import fridgy.model.base.Eq;
import fridgy.model.tag.Tag;

/**
 * Represents an ingredient in the Inventory.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Ingredient extends BaseIngredient {

    // Identity fields
    private final ExpiryDate expiryDate;

    // Data fields
    private final Description description;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Constructs an ingredient reference with an empty description.
     */
    public Ingredient(Name name, Quantity quantity, Set<Tag> tags, ExpiryDate expiryDate) {
        super(name, quantity);
        requireAllNonNull(name, quantity, expiryDate, tags);
        this.description = new Description(Optional.empty());
        this.expiryDate = expiryDate;
        this.tags.addAll(tags);
    }

    /**
     * Constructs an ingredient reference where all fields must be present and not null.
     */
    public Ingredient(Name name, Quantity quantity, Description description,
                      Set<Tag> tags, ExpiryDate expiryDate) {
        super(name, quantity);
        requireAllNonNull(name, quantity, expiryDate, tags);
        this.description = description;
        this.expiryDate = expiryDate;
        this.tags.addAll(tags);
    }

    public Description getDescription() {
        return description;
    }

    public ExpiryDate getExpiryDate() {
        return expiryDate;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both ingredients have the same name and expiry date.
     * Comparison is case-insensitive.
     * This defines a weaker notion of equality between two ingredients.
     */
    public boolean isSameIngredient(Ingredient otherIngredient) {
        if (otherIngredient == this) {
            return true;
        }

        return otherIngredient != null
                && getName().equals(otherIngredient.getName())
                && otherIngredient.getExpiryDate().equals(getExpiryDate());
    }

    @Override
    public boolean isSame(Eq other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Ingredient) {
            return isSameIngredient((Ingredient) other);
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

        if (!(other instanceof Ingredient)) {
            return false;
        }

        Ingredient otherIngredient = (Ingredient) other;
        return otherIngredient.getName().equals(getName())
                && otherIngredient.getQuantity().equals(getQuantity())
                && otherIngredient.getExpiryDate().equals((getExpiryDate()))
                && otherIngredient.getDescription().equals(getDescription())
                && otherIngredient.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), getQuantity(), description, tags, expiryDate);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Quantity: ")
                .append(getQuantity())
                .append("; Expiry Date: ")
                .append(getExpiryDate());

        Description description = getDescription();
        if (!description.value.equals(Optional.empty())) {
            builder.append("; Description: ")
                    .append(getDescription());
        }

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
