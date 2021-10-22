package fridgy.model.recipe;

import static fridgy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;


class RecipeIngredientTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BaseIngredient(null));
    }

    @Test
    public void hashCodeTest() {
        BaseIngredient baseIngredient = new BaseIngredient("A");
        BaseIngredient baseIngredientSame = new BaseIngredient("A");
        BaseIngredient baseIngredientDiff = new BaseIngredient("B");

        assertEquals(baseIngredient.hashCode(), baseIngredient.hashCode());
        assertEquals(baseIngredient.hashCode(), baseIngredientSame.hashCode());
        assertNotEquals(baseIngredient.hashCode(), baseIngredientDiff.hashCode());
    }
}
