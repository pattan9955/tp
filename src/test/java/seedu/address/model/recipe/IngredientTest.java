package seedu.address.model.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


class IngredientTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Ingredient(null));
    }

    @Test
    public void hashCodeTest() {
        Ingredient ingredient = new Ingredient("A");
        Ingredient ingredientSame = new Ingredient("A");
        Ingredient ingredientDiff = new Ingredient("B");

        assertEquals(ingredient.hashCode(), ingredient.hashCode());
        assertEquals(ingredient.hashCode(), ingredientSame.hashCode());
        assertNotEquals(ingredient.hashCode(), ingredientDiff.hashCode());
    }
}
