package fridgy.commons.core.index;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fridgy.testutil.Assert;

public class IndexTest {

    @Test
    public void createOneBasedIndex() {
        // invalid index
        Assert.assertThrows(IndexOutOfBoundsException.class, () -> Index.fromOneBased(0));

        // check equality using the same base
        assertEquals(1, Index.fromOneBased(1).getOneBased());
        assertEquals(5, Index.fromOneBased(5).getOneBased());

        // convert from one-based index to zero-based index
        assertEquals(0, Index.fromOneBased(1).getZeroBased());
        assertEquals(4, Index.fromOneBased(5).getZeroBased());
    }

    @Test
    public void createZeroBasedIndex() {
        // invalid index
        Assert.assertThrows(IndexOutOfBoundsException.class, () -> Index.fromZeroBased(-1));

        // check equality using the same base
        assertEquals(0, Index.fromZeroBased(0).getZeroBased());
        assertEquals(5, Index.fromZeroBased(5).getZeroBased());

        // convert from zero-based index to one-based index
        assertEquals(1, Index.fromZeroBased(0).getOneBased());
        assertEquals(6, Index.fromZeroBased(5).getOneBased());
    }

    @Test
    public void equals() {
        final Index fifthIngredientIndex = Index.fromOneBased(5);

        // same values -> returns true
        assertTrue(fifthIngredientIndex.equals(Index.fromOneBased(5)));
        assertTrue(fifthIngredientIndex.equals(Index.fromZeroBased(4)));

        // same object -> returns true
        assertTrue(fifthIngredientIndex.equals(fifthIngredientIndex));

        // null -> returns false
        assertFalse(fifthIngredientIndex.equals(null));

        // different types -> returns false
        assertFalse(fifthIngredientIndex.equals(5.0f));

        // different index -> returns false
        assertFalse(fifthIngredientIndex.equals(Index.fromOneBased(1)));
    }
}
