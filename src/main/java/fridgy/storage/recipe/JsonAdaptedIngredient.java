package fridgy.storage.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fridgy.commons.exceptions.IllegalValueException;
import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Quantity;

/**
 * Jackson-friendly version of {@link BaseIngredient}
 */
public class JsonAdaptedIngredient {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Ingredient's %s field is missing!";

    private final String name;
    private final String quantity;

    /**
     * Constructs a {@code JsonAdaptedIngredient} with the given {@code ingredient}.
     */
    @JsonCreator
    public JsonAdaptedIngredient(@JsonProperty("name") String name, @JsonProperty("quantity") String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Converts a given {@code BaseIngredient} into this class for Jackson use.
     */
    public JsonAdaptedIngredient(BaseIngredient source) {
        name = source.getName().fullName;
        quantity = source.getQuantity().toString();
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code BaseIngredient} object.
     */
    public BaseIngredient toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Quantity.class.getSimpleName()));
        }
        if (!Quantity.isValidQuantityString(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        return new BaseIngredient(modelName, modelQuantity);

    }
}
