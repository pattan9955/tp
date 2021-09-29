package fridgy.testutil;

import java.util.HashSet;
import java.util.Set;

import fridgy.model.tag.Tag;
import fridgy.model.util.SampleDataUtil;
import fridgy.model.ingredient.Description;
import fridgy.model.ingredient.Email;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.Quantity;

/**
 * A utility class to help with building Ingredient objects.
 */
public class IngredientBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_QUANTITY = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_DESCRIPTION = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Quantity quantity;
    private Email email;
    private Description description;
    private Set<Tag> tags;

    /**
     * Creates a {@code IngredientBuilder} with the default details.
     */
    public IngredientBuilder() {
        name = new Name(DEFAULT_NAME);
        quantity = new Quantity(DEFAULT_QUANTITY);
        email = new Email(DEFAULT_EMAIL);
        description = new Description(DEFAULT_DESCRIPTION);
        tags = new HashSet<>();
    }

    /**
     * Initializes the IngredientBuilder with the data of {@code ingredientToCopy}.
     */
    public IngredientBuilder(Ingredient ingredientToCopy) {
        name = ingredientToCopy.getName();
        quantity = ingredientToCopy.getQuantity();
        email = ingredientToCopy.getEmail();
        description = ingredientToCopy.getDescription();
        tags = new HashSet<>(ingredientToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Ingredient build() {
        return new Ingredient(name, quantity, email, description, tags);
    }

}
