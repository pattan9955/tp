package fridgy.model.ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import fridgy.testutil.Assert;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Description(Optional.of(invalidDescription)));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertTrue(Description.isValidDescription(Optional.empty()));

        // invalid description
        assertFalse(Description.isValidDescription(Optional.of(""))); // empty string
        assertFalse(Description.isValidDescription(Optional.of("ø∂çß"))); // non-ascii character

        // valid descriptions
        assertTrue(Description.isValidDescription(Optional.of(" "))); // spaces only, trimmed to empty string
        assertTrue(Description.isValidDescription(Optional.of("From NTUC supermarket")));
        assertTrue(Description.isValidDescription(Optional.of("-"))); // one character
        assertTrue(Description.isValidDescription(
                Optional.of("Nestle dark choco bar with 90 percent chocolate"))); // long description
    }

    @Test
    public void hashCodeTest() {
        Description desc = new Description(Optional.of("A"));
        Description descSame = new Description(Optional.of("A"));
        Description descDiff = new Description(Optional.of("B"));

        assertEquals(desc.hashCode(), desc.hashCode());
        assertEquals(desc.hashCode(), descSame.hashCode());
        assertNotEquals(desc.hashCode(), descDiff.hashCode());
    }
}
