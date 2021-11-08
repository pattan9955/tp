package fridgy.testutil;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fridgy.logic.commands.ingredient.EditCommand.EditIngredientDescriptor;
import fridgy.model.ingredient.Description;
import fridgy.model.ingredient.ExpiryDate;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Quantity;
import fridgy.model.tag.Tag;

/**
 * A utility class to help with building EditIngredientDescriptor objects.
 */
public class EditIngredientDescriptorBuilder {

    private EditIngredientDescriptor descriptor;

    public EditIngredientDescriptorBuilder() {
        descriptor = new EditIngredientDescriptor();
    }

    public EditIngredientDescriptorBuilder(EditIngredientDescriptor descriptor) {
        this.descriptor = new EditIngredientDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditIngredientDescriptor} with fields containing {@code ingredient}'s details
     */
    public EditIngredientDescriptorBuilder(Ingredient ingredient) {
        descriptor = new EditIngredientDescriptor();
        descriptor.setName(ingredient.getName());
        descriptor.setQuantity(ingredient.getQuantity());
        descriptor.setDescription(ingredient.getDescription());
        descriptor.setExpiry(ingredient.getExpiryDate());
        descriptor.setTags(ingredient.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditIngredientDescriptor} that we are building.
     */
    public EditIngredientDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code EditIngredientDescriptor} that we are building.
     */
    public EditIngredientDescriptorBuilder withQuantity(String quantity) {
        descriptor.setQuantity(new Quantity(quantity));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditIngredientDescriptor} that we are building.
     */
    public EditIngredientDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(Optional.ofNullable(description)));
        return this;
    }

    /**
     * Sets the {@code ExpiryDate} of the {@code EditIngredientDescriptor} that we are building.
     */
    public EditIngredientDescriptorBuilder withExpiry(String expiryDate) {
        descriptor.setExpiry(new ExpiryDate(expiryDate));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditIngredientDescriptor}
     * that we are building.
     */
    public EditIngredientDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditIngredientDescriptor build() {
        return descriptor;
    }
}
