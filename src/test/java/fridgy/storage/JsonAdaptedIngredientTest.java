package fridgy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static fridgy.testutil.Assert.assertThrows;
import static fridgy.testutil.TypicalIngredients.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fridgy.commons.exceptions.IllegalValueException;
import fridgy.model.ingredient.Description;
import fridgy.model.ingredient.Email;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Quantity;
import fridgy.testutil.Assert;
import org.junit.jupiter.api.Test;

public class JsonAdaptedIngredientTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_QUANTITY = "+651234";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_QUANTITY = BENSON.getQuantity().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_DESCRIPTION = BENSON.getDescription().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validIngredientDetails_returnsIngredient() throws Exception {
        JsonAdaptedIngredient ingredient = new JsonAdaptedIngredient(BENSON);
        assertEquals(BENSON, ingredient.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(INVALID_NAME, VALID_QUANTITY, VALID_EMAIL, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient = new JsonAdaptedIngredient(null, VALID_QUANTITY, VALID_EMAIL, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedIngredient.MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(VALID_NAME, INVALID_QUANTITY, VALID_EMAIL, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Quantity.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient = new JsonAdaptedIngredient(VALID_NAME, null, VALID_EMAIL, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedIngredient.MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(VALID_NAME, VALID_QUANTITY, INVALID_EMAIL, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient = new JsonAdaptedIngredient(VALID_NAME, VALID_QUANTITY, null, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedIngredient.MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(VALID_NAME, VALID_QUANTITY, VALID_EMAIL, INVALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient = new JsonAdaptedIngredient(VALID_NAME, VALID_QUANTITY, VALID_EMAIL, null, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedIngredient.MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(VALID_NAME, VALID_QUANTITY, VALID_EMAIL, VALID_DESCRIPTION, invalidTags);
        Assert.assertThrows(IllegalValueException.class, ingredient::toModelType);
    }

}
