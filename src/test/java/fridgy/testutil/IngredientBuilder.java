package fridgy.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.ingredient.Description;
import fridgy.model.ingredient.ExpiryDate;
import fridgy.model.ingredient.ExpiryStatusUpdater;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Quantity;
import fridgy.model.tag.Tag;
import fridgy.model.util.SampleDataUtil;


/**
 * A utility class to help with building Ingredient objects.
 */
public class IngredientBuilder {

    public static final String DEFAULT_NAME = "Almond";
    public static final String DEFAULT_QUANTITY = "50g";
    public static final String DEFAULT_DESCRIPTION = "Nut";
    public static final String DEFAULT_EXPIRY_DATE = "20-08-2099";
    public static final Set<Tag> DEFAULT_TAGS = new HashSet<>();

    private Name name;
    private Quantity quantity;
    private Description description;
    private Set<Tag> tags;
    private ExpiryDate expiryDate;

    /**
     * Creates a {@code IngredientBuilder} with the default details.
     */
    public IngredientBuilder() {
        name = new Name(DEFAULT_NAME);
        quantity = new Quantity(DEFAULT_QUANTITY);
        description = new Description(Optional.of(DEFAULT_DESCRIPTION));
        tags = DEFAULT_TAGS;
        expiryDate = new ExpiryDate(DEFAULT_EXPIRY_DATE);
    }

    /**
     * Initializes the IngredientBuilder with the data of {@code ingredientToCopy}.
     */
    public IngredientBuilder(Ingredient ingredientToCopy) {
        name = ingredientToCopy.getName();
        quantity = ingredientToCopy.getQuantity();
        description = ingredientToCopy.getDescription();
        tags = new HashSet<>(ingredientToCopy.getTags());
        expiryDate = ingredientToCopy.getExpiryDate();
    }

    /**
     * Initializes the IngredientBuilder for BaseIngredient
     * with the data of {@code ingredientToCopy}.
     */
    public IngredientBuilder(BaseIngredient ingredientToCopy) {
        name = ingredientToCopy.getName();
        quantity = ingredientToCopy.getQuantity();
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
        this.description = new Description(Optional.ofNullable(description));
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
     * Sets the {@code ExpiryDate} of the {@code Ingredient} that we are building.
     * Do note the expiry tags will not be updated by this method.
     */
    public IngredientBuilder withExpiryDate(String expiryDate) {
        this.expiryDate = new ExpiryDate(expiryDate);
        return this;
    }

    /**
     * Build {@code Ingredient} with the specified parameters.
     *
     * @return the ingredient built
     */
    public Ingredient build() {
        return ExpiryStatusUpdater.updateExpiryTags(
                new Ingredient(name, quantity, description, tags, expiryDate));
    }

    /**
     * Build {@code BaseIngredient} with the specified parameters.
     *
     * @return the base ingredient built
     */
    public BaseIngredient buildBaseIngredient() {
        return new BaseIngredient(name, quantity);
    }

}
