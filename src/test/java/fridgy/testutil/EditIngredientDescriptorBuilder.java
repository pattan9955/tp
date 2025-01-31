package fridgy.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fridgy.model.tag.Tag;
import fridgy.logic.commands.EditCommand.EditIngredientDescriptor;
import fridgy.model.ingredient.Address;
import fridgy.model.ingredient.Email;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.Phone;

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
        descriptor.setPhone(ingredient.getPhone());
        descriptor.setEmail(ingredient.getEmail());
        descriptor.setAddress(ingredient.getAddress());
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
     * Sets the {@code Phone} of the {@code EditIngredientDescriptor} that we are building.
     */
    public EditIngredientDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditIngredientDescriptor} that we are building.
     */
    public EditIngredientDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditIngredientDescriptor} that we are building.
     */
    public EditIngredientDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
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
