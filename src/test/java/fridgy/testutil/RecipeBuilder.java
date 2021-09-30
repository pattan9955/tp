package fridgy.testutil;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import fridgy.model.recipe.RecipeIngredient;
import fridgy.model.recipe.Name;
import fridgy.model.recipe.Recipe;
import fridgy.model.recipe.Step;

/**
 * A utility class to help with building Recipe objects.
 */
public class RecipeBuilder {

    public static final String DEFAULT_NAME = "Burger";
    public static final List<String> DEFAULT_INGREDIENTS = Arrays.asList("Ing 1", "Ing 2");
    public static final List<String> DEFAULT_STEPS = Arrays.asList("Step 1", "Step 2");
    public static final String DEFAULT_DESCRIPTION = "Very Nice";

    private Name name;
    private List<RecipeIngredient> recipeIngredients;
    private List<Step> steps;
    private Optional<String> description;

    /**
     * Creates a {@code RecipeBuilder} with the default details.
     */
    public RecipeBuilder() {
        withName(DEFAULT_NAME);
        withIngredients(DEFAULT_INGREDIENTS);
        withSteps(DEFAULT_STEPS);
        withDescription(DEFAULT_DESCRIPTION);
    }

    /**
     * Initializes the RecipeBuilder with the data of {@code recipeToCopy}.
     */
    public RecipeBuilder(Recipe recipeToCopy) {
        name = recipeToCopy.getName();
        recipeIngredients = recipeToCopy.getIngredients();
        steps = recipeToCopy.getSteps();
        description = recipeToCopy.getDescription();
    }

    /**
     * Sets the {@code Name} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code RecipeIngredient} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withIngredients(List<String> ingredients) {
        this.recipeIngredients = ingredients.stream().map(x -> new RecipeIngredient(x)).collect(Collectors.toList());
        return this;
    }

    /**
     * Sets the {@code Step} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withSteps(List<String> steps) {
        this.steps = steps.stream().map(x -> new Step(x)).collect(Collectors.toList());
        return this;
    }

    /**
     * Sets the Description of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withDescription(String description) {
        this.description = Optional.ofNullable(description);
        return this;
    }

    public Recipe build() {
        return new Recipe(name, recipeIngredients, steps, description);
    }

}
