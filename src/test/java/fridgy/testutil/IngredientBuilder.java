package fridgy.testutil;

import java.util.HashSet;
import java.util.Set;

import fridgy.model.ingredient.Address;
import fridgy.model.ingredient.Email;
import fridgy.model.ingredient.ExpiryDate;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Phone;
import fridgy.model.ingredient.Type;
import fridgy.model.tag.Tag;
import fridgy.model.util.SampleDataUtil;

/**
 * A utility class to help with building Ingredient objects.
 */
public class IngredientBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_TYPE = "solid";
    public static final String DEFAULT_EXPIRY_DATE = "20-08-2010";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Type type;
    private ExpiryDate expiryDate;

    /**
     * Creates a {@code IngredientBuilder} with the default details.
     */
    public IngredientBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        type = new Type(DEFAULT_TYPE);
        expiryDate = new ExpiryDate(DEFAULT_EXPIRY_DATE);
    }

    /**
     * Initializes the IngredientBuilder with the data of {@code ingredientToCopy}.
     */
    public IngredientBuilder(Ingredient ingredientToCopy) {
        name = ingredientToCopy.getName();
        phone = ingredientToCopy.getPhone();
        email = ingredientToCopy.getEmail();
        address = ingredientToCopy.getAddress();
        tags = new HashSet<>(ingredientToCopy.getTags());
        type = ingredientToCopy.getType();
        expiryDate = ingredientToCopy.getExpiryDate();
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
     * Sets the {@code Address} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code ExpiryDate} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withExpiryDate(String expiryDate) {
        this.expiryDate = new ExpiryDate(expiryDate);
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code IngredientBuilder} that we are building.
     */
    public IngredientBuilder withType(String type) {
        this.type = new Type(type);
        return this;
    }

    public Ingredient build() {
        return new Ingredient(name, phone, email, address, tags, type, expiryDate);
    }

}
