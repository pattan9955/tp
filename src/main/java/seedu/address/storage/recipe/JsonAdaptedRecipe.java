package seedu.address.storage.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.recipe.Ingredient;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.Step;


/**
 * Jackson-friendly version of {@link Recipe}
 */
public class JsonAdaptedRecipe {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Recipe's %s field is missing!";

    private final String name;
    private final List<JsonAdaptedIngredient> ingredients = new ArrayList<>();
    private final List<JsonAdaptedStep> steps = new ArrayList<>();
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedRecipe} with the given recipe details.
     */
    @JsonCreator
    public JsonAdaptedRecipe(@JsonProperty("name") String name,
                             @JsonProperty("ingredients") List<JsonAdaptedIngredient> ingredients,
                             @JsonProperty("steps") List<JsonAdaptedStep> steps,
                             @JsonProperty("description") String description) {
        this.name = name;
        if (ingredients != null) {
            this.ingredients.addAll(ingredients);
        }
        if (steps != null) {
            this.steps.addAll(steps);
        }
        this.description = description;
    }

    /**
     * Converts a given {@code Recipe} into this class for Jackson use.
     */
    public JsonAdaptedRecipe(Recipe source) {
        name = source.getName().fullName;
        ingredients.addAll(source.getIngredients().stream()
                .map(JsonAdaptedIngredient::new)
                .collect(Collectors.toList()));
        steps.addAll(source.getSteps().stream()
                .map(JsonAdaptedStep::new)
                .collect(Collectors.toList()));
        description = source.getDescription().orElse("");
    }

    /**
     * Converts this Jackson-friendly adapted recipe object into the model's {@code Recipe} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Recipe.
     */
    public Recipe toModelType() throws IllegalValueException {
        final List<Ingredient> modelIngredients = new ArrayList<>();
        for (JsonAdaptedIngredient ingredient : ingredients) {
            modelIngredients.add(ingredient.toModelType());
        }

        final List<Step> modelSteps = new ArrayList<>();
        for (JsonAdaptedStep step : steps) {
            modelSteps.add(step.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (modelIngredients.isEmpty()) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Ingredient.class.getSimpleName()));
        }

        if (modelSteps.isEmpty()) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Step.class.getSimpleName()));
        }
        final Optional<String> modelDescription = Optional.ofNullable(description);
        return new Recipe(modelName, modelIngredients, modelSteps, modelDescription);
    }
}
