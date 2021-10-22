package fridgy.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fridgy.model.RecipeBook;
import fridgy.model.recipe.Recipe;


public class TypicalRecipes {

    public static final Recipe BURGER = new RecipeBuilder()
            .withName("Burger")
            .withIngredients(Arrays.asList(TypicalBaseIngredients.INGR1, TypicalBaseIngredients.INGR2))
            .withSteps(Arrays.asList("Burger STEP 1", "Burger STEP 2"))
            .withDescription("Very Nice")
            .build();
    public static final Recipe MAGGIE = new RecipeBuilder()
            .withName("Maggie")
            .withIngredients(Arrays.asList(TypicalBaseIngredients.APPLE, TypicalBaseIngredients.GRAPES))
            .withSteps(Arrays.asList("Maggie STEP 1", "Maggie STEP 2"))
            .withDescription("Easy to make")
            .build();

    public static final Recipe FRIES = new RecipeBuilder()
            .withName("Fries")
            .withIngredients(Arrays.asList(TypicalBaseIngredients.CARROT, TypicalBaseIngredients.BANANA))
            .withSteps(Arrays.asList("Fries STEP 1", "Fries STEP 2"))
            .withDescription("More SALT!")
            .build();

    // Manually added
    public static final Recipe RICE = new RecipeBuilder()
            .withName("Rice")
            .withIngredients(Arrays.asList(new IngredientBuilder().withName("Rice")
                .withQuantity("500g").buildBaseIngredient()))
            .withSteps(Arrays.asList("Rice in cooker"))
            .withDescription("Carbs <3")
            .build();

    public static final Recipe NOODLE = new RecipeBuilder()
            .withName("Noodle")
            .withIngredients(Arrays.asList(new IngredientBuilder().withName("Noodle")
                .withQuantity("400g").buildBaseIngredient()))
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
            rb.add(recipe);
        }
        return rb;
    }

    public static List<Recipe> getTypicalRecipes() {
        return new ArrayList<>(Arrays.asList(BURGER, MAGGIE, FRIES));
    }
}
