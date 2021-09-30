package fridgy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static fridgy.testutil.Assert.assertThrows;
import static fridgy.testutil.TypicalIngredients.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fridgy.commons.exceptions.IllegalValueException;
import fridgy.model.ingredient.Address;
import fridgy.model.ingredient.Email;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Phone;
import fridgy.testutil.Assert;
import org.junit.jupiter.api.Test;

public class JsonAdaptedIngredientTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_EXPIRY_DATE = BENSON.getExpiryDate().toString();
    private static final String VALID_TYPE = BENSON.getType().toString();

    @Test
    public void toModelType_validIngredientDetails_returnsIngredient() throws Exception {
        JsonAdaptedIngredient ingredient = new JsonAdaptedIngredient(BENSON);
        assertEquals(BENSON, ingredient.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_TYPE, VALID_EXPIRY_DATE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient = new JsonAdaptedIngredient(null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_TYPE, VALID_EXPIRY_DATE);
        String expectedMessage = String.format(JsonAdaptedIngredient.MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_TYPE, VALID_EXPIRY_DATE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient = new JsonAdaptedIngredient(VALID_NAME, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_TYPE, VALID_EXPIRY_DATE);
        String expectedMessage = String.format(JsonAdaptedIngredient.MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_TYPE, VALID_EXPIRY_DATE);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient = new JsonAdaptedIngredient(VALID_NAME, VALID_PHONE, null,
                VALID_ADDRESS, VALID_TAGS, VALID_TYPE, VALID_EXPIRY_DATE);
        String expectedMessage = String.format(JsonAdaptedIngredient.MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS,
                        VALID_TYPE, VALID_EXPIRY_DATE);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient = new JsonAdaptedIngredient(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_TAGS, VALID_TYPE, VALID_EXPIRY_DATE);
        String expectedMessage = String.format(JsonAdaptedIngredient.MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags,
                        VALID_TYPE, VALID_EXPIRY_DATE);
        Assert.assertThrows(IllegalValueException.class, ingredient::toModelType);
    }

}
