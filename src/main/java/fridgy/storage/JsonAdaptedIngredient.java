package fridgy.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fridgy.commons.exceptions.IllegalValueException;
import fridgy.model.ingredient.Description;
import fridgy.model.ingredient.ExpiryDate;
import fridgy.model.ingredient.ExpiryStatusUpdater;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Quantity;
import fridgy.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Ingredient}.
 */
class JsonAdaptedIngredient {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Ingredient's %s field is missing!";

    private final String name;
    private final String quantity;
    private final String expiryDate;
    private String description = null;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedIngredient} with the given ingredient details.
     */
    @JsonCreator
    public JsonAdaptedIngredient(@JsonProperty("name") String name, @JsonProperty("quantity") String quantity,
            @JsonProperty("description") String description, @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                                 @JsonProperty("expiryDate") String expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        if (description != null) {
            this.description = description;
        }
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Ingredient} into this class for Jackson use.
     */
    public JsonAdaptedIngredient(Ingredient source) {
        name = source.getName().fullName;
        quantity = source.getQuantity().toString();
        expiryDate = source.getExpiryDate().toString();
        description = source.getDescription().value.orElse(null);
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted ingredient object into the model's {@code Ingredient} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ingredient.
     */
    public Ingredient toModelType() throws IllegalValueException {
        final List<Tag> ingredientTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            ingredientTags.add(tag.toModelType());
        }

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

        final Optional<String> modelDescription = Optional.ofNullable(description);
        if (description == "") {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(modelDescription)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }

        if (expiryDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExpiryDate.class.getSimpleName()));
        }
        if (!ExpiryDate.isValidExpiry(expiryDate)) {
            throw new IllegalValueException(ExpiryDate.MESSAGE_CONSTRAINTS);
        }
        final ExpiryDate modelExpiryDate = new ExpiryDate(expiryDate);

        final Set<Tag> modelTags = new HashSet<>(ingredientTags);

        Ingredient ingredient = new Ingredient(modelName, modelQuantity, new Description(modelDescription),
                modelTags, modelExpiryDate);

        return ExpiryStatusUpdater.updateExpiryTags(ingredient);
    }

}
