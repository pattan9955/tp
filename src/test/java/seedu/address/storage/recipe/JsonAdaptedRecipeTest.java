package seedu.address.storage.recipe;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.recipe.JsonAdaptedRecipe.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRecipes.BURGER;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.recipe.Name;

class JsonAdaptedRecipeTest {
    private static final String INVALID_NAME = "B@rg@r";

    private static final String VALID_NAME = BURGER.getName().toString();
    private static final List<JsonAdaptedIngredient> VALID_INGREDIENT =
            BURGER.getIngredients().stream().map(JsonAdaptedIngredient::new).collect(Collectors.toList());
    private static final List<JsonAdaptedStep> VALID_STEP =
            BURGER.getSteps().stream().map(JsonAdaptedStep::new).collect(Collectors.toList());
    private static final String VALID_DESCRIPTION = BURGER.getDescription().toString();

    @Test
    public void toModelType_validRecipeDetails_returnsRecipe() throws Exception {
        JsonAdaptedRecipe recipe = new JsonAdaptedRecipe(BURGER);
        assertEquals(BURGER, recipe.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(INVALID_NAME, VALID_INGREDIENT, VALID_STEP, VALID_DESCRIPTION);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(null, VALID_INGREDIENT, VALID_STEP, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                seedu.address.model.recipe.Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_nullIngredient_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_NAME, null, VALID_STEP, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                seedu.address.model.recipe.Ingredient.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }
    @Test
    public void toModelType_nullStep_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_NAME, VALID_INGREDIENT, null, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                seedu.address.model.recipe.Step.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }
    @Test
    public void toModelType_nullDescription_doesNotThrowException() {
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_NAME, VALID_INGREDIENT, VALID_STEP, null);
        assertDoesNotThrow(recipe::toModelType);
    }

}
