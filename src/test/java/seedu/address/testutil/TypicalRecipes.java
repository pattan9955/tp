package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.RecipeBook;
import seedu.address.model.recipe.Recipe;


public class TypicalRecipes {

    public static final Recipe BURGER = new RecipeBuilder()
            .withName("Burger")
            .withIngredients(Arrays.asList("Burger Ingredient 1", "Burger Ingredient 2"))
            .withSteps(Arrays.asList("Burger STEP 1", "Burger STEP 2"))
            .withDescription("Very Nice")
            .build();
    public static final Recipe MAGGIE = new RecipeBuilder()
            .withName("Maggie")
            .withIngredients(Arrays.asList("Maggie Ingredient 1", "Maggie Ingredient 2"))
            .withSteps(Arrays.asList("Maggie STEP 1", "Maggie STEP 2"))
            .withDescription("Easy to make")
            .build();

    public static final Recipe FRIES = new RecipeBuilder()
            .withName("Fries")
            .withIngredients(Arrays.asList("Fries Ingredient 1", "Fries Ingredient 2"))
            .withSteps(Arrays.asList("Fries STEP 1", "Fries STEP 2"))
            .withDescription("More SALT!")
            .build();

    // Manually added
    public static final Recipe RICE = new RecipeBuilder()
            .withName("Rice")
            .withIngredients(Arrays.asList("Rice"))
            .withSteps(Arrays.asList("Rice in cooker"))
            .withDescription("Carbs <3")
            .build();

    public static final Recipe NOODLE = new RecipeBuilder()
            .withName("Noodle")
            .withIngredients(Arrays.asList("Noodle"))
            .withSteps(Arrays.asList("Boil water", "Put Noodle", "Wait 15 mins"))
            .withDescription("Nice Carbs <3")
            .build();

    private TypicalRecipes() {} // prevents instantiation

    /**
     * Returns an {@code RecipeBook} with all the typical recipes.
     */
    public static RecipeBook getTypicalRecipeBook() {
        RecipeBook rb = new RecipeBook();
        for (Recipe recipe : getTypicalRecipes()) {
            rb.addRecipe(recipe);
        }
        return rb;
    }

    public static List<Recipe> getTypicalRecipes() {
        return new ArrayList<>(Arrays.asList(BURGER, MAGGIE, FRIES));
    }
}
