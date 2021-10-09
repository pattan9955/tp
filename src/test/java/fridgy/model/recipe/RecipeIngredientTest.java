package fridgy.model.recipe;

import static fridgy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;


class RecipeIngredientTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RecipeIngredient(null));
    }

    @Test
    public void hashCodeTest() {
        RecipeIngredient recipeIngredient = new RecipeIngredient("A");
        RecipeIngredient recipeIngredientSame = new RecipeIngredient("A");
        RecipeIngredient recipeIngredientDiff = new RecipeIngredient("B");

        assertEquals(recipeIngredient.hashCode(), recipeIngredient.hashCode());
        assertEquals(recipeIngredient.hashCode(), recipeIngredientSame.hashCode());
        assertNotEquals(recipeIngredient.hashCode(), recipeIngredientDiff.hashCode());
    }
}
