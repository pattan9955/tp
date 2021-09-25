package fridgy.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import fridgy.commons.exceptions.IllegalValueException;
import fridgy.model.AddressBook;
import fridgy.model.ReadOnlyAddressBook;
import fridgy.model.ingredient.Ingredient;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_INGREDIENT = "Ingredients list contains duplicate ingredient(s).";

    private final List<JsonAdaptedIngredient> ingredients = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given ingredients.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("ingredients") List<JsonAdaptedIngredient> ingredients) {
        this.ingredients.addAll(ingredients);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        ingredients.addAll(source.getIngredientList().stream().map(JsonAdaptedIngredient::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
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
