package fridgy.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import fridgy.model.Inventory;
import fridgy.model.ReadOnlyInventory;
import fridgy.model.ingredient.Description;
import fridgy.model.ingredient.Email;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Quantity;
import fridgy.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Inventory} with sample data.
 */
public class SampleDataUtil {
    public static Ingredient[] getSampleIngredients() {
        return new Ingredient[] {
            new Ingredient(new Name("Alex Yeoh"), new Quantity("87438807"), new Email("alexyeoh@example.com"),
                new Description("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Ingredient(new Name("Bernice Yu"), new Quantity("99272758"), new Email("berniceyu@example.com"),
                new Description("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Ingredient(new Name("Charlotte Oliveiro"), new Quantity("93210283"), new Email("charlotte@example.com"),
                new Description("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Ingredient(new Name("David Li"), new Quantity("91031282"), new Email("lidavid@example.com"),
                new Description("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Ingredient(new Name("Irfan Ibrahim"), new Quantity("92492021"), new Email("irfan@example.com"),
                new Description("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Ingredient(new Name("Roy Balakrishnan"), new Quantity("92624417"), new Email("royb@example.com"),
                new Description("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyInventory getSampleInventory() {
        Inventory sampleAb = new Inventory();
        for (Ingredient sampleIngredient : getSampleIngredients()) {
            sampleAb.addIngredient(sampleIngredient);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
