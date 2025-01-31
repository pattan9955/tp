package fridgy.model.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import fridgy.model.Inventory;
import fridgy.model.ReadOnlyInventory;
import fridgy.model.ReadOnlyRecipeBook;
import fridgy.model.RecipeBook;
import fridgy.model.ingredient.Address;
import fridgy.model.ingredient.Email;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Phone;
import fridgy.model.recipe.Recipe;
import fridgy.model.recipe.RecipeIngredient;
import fridgy.model.recipe.Step;
import fridgy.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Inventory} with sample data.
 */
public class SampleDataUtil {
    public static Ingredient[] getSampleIngredients() {
        return new Ingredient[] {
            new Ingredient(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Ingredient(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Ingredient(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Ingredient(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Ingredient(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Ingredient(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static Recipe[] getSampleRecipes() {
        return new Recipe[] {
            new Recipe(
                new fridgy.model.recipe.Name("Burger"),
                Arrays.asList(new RecipeIngredient("Bread"), new RecipeIngredient("patty")),
                Arrays.asList(new Step("B1"), new Step("B2")),
                Optional.of("Desc B")
            ),
            new Recipe(
                    new fridgy.model.recipe.Name("Maggie"),
                    Arrays.asList(new RecipeIngredient("Maggie")),
                    Arrays.asList(new Step("M1"), new Step("M2")),
                    Optional.of("Desc M")
            ),
            new Recipe(
                    new fridgy.model.recipe.Name("Recipe"),
                    Arrays.asList(new RecipeIngredient("RecipeIngredient 1"),
                            new RecipeIngredient("RecipeIngredient 2")),
                    Arrays.asList(new Step("Step 1"), new Step("Step 2")),
                    Optional.empty()
            ),
        };

    }

    public static ReadOnlyInventory getSampleInventory() {
        Inventory sampleAb = new Inventory();
        for (Ingredient sampleIngredient : getSampleIngredients()) {
            sampleAb.addIngredient(sampleIngredient);
        }
        return sampleAb;
    }

    public static ReadOnlyRecipeBook getSampleRecipeBook() {
        RecipeBook sampleRb = new RecipeBook();
        for (Recipe sampleRecipe: getSampleRecipes()) {
            sampleRb.addRecipe(sampleRecipe);
        }
        return sampleRb;
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
