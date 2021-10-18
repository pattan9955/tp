package fridgy.model.recipe;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fridgy.model.RecipeModel;

/**
 * The default comparator for {@code Recipe},
 * which compares recipes by their ingredient availability.
 */
public class RecipeDefaultComparator implements Comparator<Recipe>{

    private RecipeModel model;

    /**
     * @param model to get all the ingredient list to check for missing ingredient
     */
    public RecipeDefaultComparator(RecipeModel model) {
        this.model = model;
    }

    /**
     * Compares recipes using ingredient availability.
     * If the number of missing ingredients for both recipes are equal, compare them by name in lexicographical order.
     */
    @Override
    public int compare(Recipe i1, Recipe i2) {
        Set<RecipeIngredient> ingredientsList1 = i1.getIngredients();
        Set<RecipeIngredient> ingredientsList2 = i2.getIngredients();

        List<String> ingredientNames = model.getFilteredIngredientList().stream()
                .map((ingredient)-> ingredient.getName().fullName).collect(Collectors.toList());

        Long noOfMissingIngredients1 = ingredientsList1.stream().map((recipeIngredient) -> recipeIngredient.getName())
                .filter((recipeIngredient) -> !ingredientNames.contains(recipeIngredient))
                .count();
        Long noOfMissingIngredients2 = ingredientsList2.stream().map((recipeIngredient) -> recipeIngredient.getName())
                .filter((recipeIngredient) -> !ingredientNames.contains(recipeIngredient))
                .count();

        return noOfMissingIngredients1 == noOfMissingIngredients2
                ? i1.getName().toString().compareTo(i2.getName().toString())
                : noOfMissingIngredients1.intValue() - noOfMissingIngredients2.intValue();
    }
}
