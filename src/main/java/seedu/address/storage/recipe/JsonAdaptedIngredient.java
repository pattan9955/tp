package seedu.address.storage.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.model.recipe.Ingredient;

/**
 * Jackson-friendly version of {@link Ingredient}
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
     * Converts a given {@code Ingredient} into this class for Jackson use.
     */
    public JsonAdaptedIngredient(Ingredient source) {
        ingredient = source.getName();
    }

    @JsonValue
    public String getIngredientName() {
        return ingredient;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Ingredient} object.
     */
    public Ingredient toModelType() {
        return new Ingredient(ingredient);
    }
}
