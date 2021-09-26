package fridgy.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import fridgy.commons.exceptions.IllegalValueException;
import fridgy.model.Inventory;
import fridgy.model.ReadOnlyInventory;
import fridgy.model.ingredient.Ingredient;

/**
 * An Immutable Inventory that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableInventory {

    public static final String MESSAGE_DUPLICATE_INGREDIENT = "Ingredients list contains duplicate ingredient(s).";

    private final List<JsonAdaptedIngredient> ingredients = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableInventory} with the given ingredients.
     */
    @JsonCreator
    public JsonSerializableInventory(@JsonProperty("ingredients") List<JsonAdaptedIngredient> ingredients) {
        this.ingredients.addAll(ingredients);
    }

    /**
     * Converts a given {@code ReadOnlyInventory} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableInventory}.
     */
    public JsonSerializableInventory(ReadOnlyInventory source) {
        ingredients.addAll(source.getIngredientList().stream()
                .map(JsonAdaptedIngredient::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Inventory into the model's {@code Inventory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Inventory toModelType() throws IllegalValueException {
        Inventory addressBook = new Inventory();
        for (JsonAdaptedIngredient jsonAdaptedIngredient : ingredients) {
            Ingredient ingredient = jsonAdaptedIngredient.toModelType();
            if (addressBook.hasIngredient(ingredient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INGREDIENT);
            }
            addressBook.addIngredient(ingredient);
        }
        return addressBook;
    }

}
