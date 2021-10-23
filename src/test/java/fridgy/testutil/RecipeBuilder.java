package fridgy.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.ingredient.Quantity;
import fridgy.model.recipe.Name;
import fridgy.model.recipe.Recipe;
import fridgy.model.recipe.Step;

/**
 * A utility class to help with building Recipe objects.
 */
public class RecipeBuilder {

    public static final String DEFAULT_NAME = "Burger";
    public static final List<BaseIngredient> DEFAULT_INGREDIENTS = Arrays.asList(
        new BaseIngredient(new fridgy.model.ingredient.Name("Ing 1"), new Quantity("1kg")),
        new BaseIngredient(new fridgy.model.ingredient.Name("Ing 2"), new Quantity("100ml"))
    );
    public static final List<String> DEFAULT_STEPS = Arrays.asList("Step 1", "Step 2");
    public static final String DEFAULT_DESCRIPTION = "Very Nice";

    private Name name;
    private Set<BaseIngredient> baseIngredients;
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
        baseIngredients = recipeToCopy.getIngredients();
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
     * Sets the {@code BaseIngredient} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withIngredients(List<BaseIngredient> ingredients) {
        this.baseIngredients = new HashSet<>(ingredients);
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
        return new Recipe(name, baseIngredients, steps, description);
    }

}
