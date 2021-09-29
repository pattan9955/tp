package fridgy.testutil;

import java.util.Set;

import fridgy.logic.commands.AddCommand;
import fridgy.logic.parser.CliSyntax;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.tag.Tag;
import fridgy.logic.commands.EditCommand.EditIngredientDescriptor;

/**
 * A utility class for Ingredient.
 */
public class IngredientUtil {

    /**
     * Returns an add command string for adding the {@code ingredient}.
     */
    public static String getAddCommand(Ingredient ingredient) {
        return AddCommand.COMMAND_WORD + " " + getIngredientDetails(ingredient);
    }

    /**
     * Returns the part of command string for the given {@code ingredient}'s details.
     */
    public static String getIngredientDetails(Ingredient ingredient) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + ingredient.getName().fullName + " ");
        sb.append(CliSyntax.PREFIX_QUANTITY + ingredient.getQuantity().value + " ");
        sb.append(CliSyntax.PREFIX_EMAIL + ingredient.getEmail().value + " ");
        sb.append(CliSyntax.PREFIX_DESCRIPTION + ingredient.getDescription().value + " ");
        ingredient.getTags().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditIngredientDescriptor}'s details.
     */
    public static String getEditIngredientDescriptorDetails(EditIngredientDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(CliSyntax.PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getQuantity().ifPresent(quantity -> sb.append(CliSyntax.PREFIX_QUANTITY).append(quantity.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(CliSyntax.PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getDescription().ifPresent(description -> sb.append(CliSyntax.PREFIX_DESCRIPTION).append(description.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(CliSyntax.PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(CliSyntax.PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
