package fridgy.storage.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import fridgy.model.recipe.RecipeIngredient;

/**
 * Jackson-friendly version of {@link RecipeIngredient}
 */
public class JsonAdaptedIngredient {

    private final String ingredient;

    /**
     * Constructs a {@code JsonAdaptedIngredient} with the given {@code ingredient}.
     */
    @JsonCreator
    public JsonAdaptedIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * Converts a given {@code RecipeIngredient} into this class for Jackson use.
     */
    public JsonAdaptedIngredient(RecipeIngredient source) {
        ingredient = source.getName();
    }

    @JsonValue
    public String getIngredientName() {
        return ingredient;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code RecipeIngredient} object.
     */
    public RecipeIngredient toModelType() {
        return new RecipeIngredient(ingredient);
    }
}
