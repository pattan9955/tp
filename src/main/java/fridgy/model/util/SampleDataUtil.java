package fridgy.model.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import fridgy.model.Inventory;
import fridgy.model.RecipeBook;
import fridgy.model.base.ReadOnlyDatabase;
import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.ingredient.Description;
import fridgy.model.ingredient.ExpiryDate;
import fridgy.model.ingredient.ExpiryStatusUpdater;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.IngredientDefaultComparator;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Quantity;
import fridgy.model.recipe.Recipe;
import fridgy.model.recipe.Step;
import fridgy.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Inventory} with sample data.
 */
public class SampleDataUtil {
    public static Ingredient[] getSampleIngredients() {
        return new Ingredient[] {
            new Ingredient(new Name("Almond jelly"), new Quantity("1kg"),
                new Description(Optional.of("Very nice")),
                getTagSet("fruit"), new ExpiryDate("20-08-2021")),
            new Ingredient(new Name("Banana leaf"), new Quantity("1kg"),
                new Description(Optional.of("leafy")),
                getTagSet("vegetable", "fruit"), new ExpiryDate("20-12-2021")),
            new Ingredient(new Name("Cabbage"), new Quantity("1kg"),
                new Description(Optional.of("From cold storage")),
                getTagSet("leafy"), new ExpiryDate("20-12-2021")),
            new Ingredient(new Name("Dark chocolate"), new Quantity("500g"),
                new Description(Optional.of("Very bitter")),
                getTagSet("sweet"), new ExpiryDate("20-08-2022")),
            new Ingredient(new Name("Ice cream"), new Quantity("1kg"),
                new Description(Optional.of("Very nice")),
                getTagSet("favorite"), new ExpiryDate("20-08-2022")),
            new Ingredient(new Name("Red beans"), new Quantity("10kg"),
                new Description(Optional.of("From NTUC")),
                getTagSet("bean"), new ExpiryDate("20-08-2022"))
        };
    }

    public static Recipe[] getSampleRecipes() {
        return new Recipe[] {
            new Recipe(
                new fridgy.model.recipe.Name("Burger"),
                Set.of(new BaseIngredient(new Name("Bread"), new Quantity("2")),
                    new BaseIngredient(new Name("Patty"), new Quantity("100g"))
                ),
                Arrays.asList(new Step("B1"), new Step("B2")),
                Optional.of("Desc B")
            ),
            new Recipe(
                    new fridgy.model.recipe.Name("Maggie"),
                    Set.of(new BaseIngredient(new Name("Maggie"), new Quantity("1"))),
                    Arrays.asList(new Step("M1"), new Step("M2")),
                    Optional.of("Desc M")
            ),
            new Recipe(
                    new fridgy.model.recipe.Name("Recipe"),
                    Set.of(new BaseIngredient(new Name("BaseIngredient 1"), new Quantity("1kg")),
                        new BaseIngredient(new Name("BaseIngredient 2"), new Quantity("100ml"))
                    ),
                    Arrays.asList(new Step("Step 1"), new Step("Step 2")),
                    Optional.empty()
            ),
        };

    }

    public static ReadOnlyDatabase<Ingredient> getSampleInventory() {
        Inventory sampleAb = new Inventory();
        for (Ingredient sampleIngredient : getSampleIngredients()) {
            sampleIngredient = ExpiryStatusUpdater.updateExpiryTags(sampleIngredient);
            sampleAb.add(sampleIngredient);
        }
        sampleAb.sort(new IngredientDefaultComparator());
        return sampleAb;
    }

    public static ReadOnlyDatabase<Recipe> getSampleRecipeBook() {
        RecipeBook sampleRb = new RecipeBook();
        for (Recipe sampleRecipe: getSampleRecipes()) {
            sampleRb.add(sampleRecipe);
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
