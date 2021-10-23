package fridgy.storage;

import static fridgy.testutil.TypicalIngredients.BANANA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import fridgy.commons.exceptions.IllegalValueException;
import fridgy.model.ingredient.Description;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Quantity;
import fridgy.testutil.Assert;

public class JsonAdaptedIngredientTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_QUANTITY = "+651234";
    private static final String INVALID_DESCRIPTION = "˚¬˙µ";
    private static final String INVALID_TAG = "#snack";

    private static final String VALID_NAME = BANANA.getName().toString();
    private static final String VALID_QUANTITY = BANANA.getQuantity().toString();
    private static final String VALID_DESCRIPTION = BANANA.getDescription().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BANANA.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_EXPIRY_DATE = BANANA.getExpiryDate().toString();

    @Test
    public void toModelType_validIngredientDetails_returnsIngredient() throws Exception {
        JsonAdaptedIngredient ingredient = new JsonAdaptedIngredient(BANANA);
        assertEquals(BANANA, ingredient.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(INVALID_NAME, VALID_QUANTITY, VALID_DESCRIPTION, VALID_TAGS,
                        VALID_EXPIRY_DATE);

        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient = new JsonAdaptedIngredient(null, VALID_QUANTITY, VALID_DESCRIPTION,
                VALID_TAGS, VALID_EXPIRY_DATE);

        String expectedMessage = String.format(JsonAdaptedIngredient.MISSING_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(VALID_NAME, INVALID_QUANTITY, VALID_DESCRIPTION, VALID_TAGS,
                        VALID_EXPIRY_DATE);
        String expectedMessage = Quantity.MESSAGE_CONSTRAINTS;

        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient = new JsonAdaptedIngredient(VALID_NAME, null,
                VALID_DESCRIPTION, VALID_TAGS, VALID_EXPIRY_DATE);
        String expectedMessage = String.format(JsonAdaptedIngredient.MISSING_FIELD_MESSAGE_FORMAT,
                Quantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(VALID_NAME, VALID_QUANTITY, INVALID_DESCRIPTION, VALID_TAGS,
                        VALID_EXPIRY_DATE);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;

        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(VALID_NAME, VALID_QUANTITY, "", VALID_TAGS,
                        VALID_EXPIRY_DATE);
        String expectedMessage = String.format(JsonAdaptedIngredient.MISSING_FIELD_MESSAGE_FORMAT,
                Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(VALID_NAME, VALID_QUANTITY, VALID_DESCRIPTION, invalidTags,
                        VALID_EXPIRY_DATE);
        Assert.assertThrows(IllegalValueException.class, ingredient::toModelType);
    }

}
