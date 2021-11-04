package fridgy.storage.recipe;

import static fridgy.storage.recipe.JsonAdaptedRecipe.MISSING_FIELD_MESSAGE_FORMAT;
import static fridgy.testutil.Assert.assertThrows;
import static fridgy.testutil.TypicalRecipes.BURGER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import fridgy.commons.exceptions.IllegalValueException;
import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.recipe.Name;

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
                fridgy.model.recipe.Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_nullIngredient_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_NAME, null, VALID_STEP, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                BaseIngredient.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_nullDescription_doesNotThrowException() {
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_NAME, VALID_INGREDIENT, VALID_STEP, null);
        assertDoesNotThrow(recipe::toModelType);
    }

}
