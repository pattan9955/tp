package fridgy.model.ingredient;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
        assertTrue(Description.isValidDescription(Optional.of("Blk 456, Den Road, #01-355")));
        assertTrue(Description.isValidDescription(Optional.of("-"))); // one character
        assertTrue(Description.isValidDescription(
                Optional.of("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA"))); // long address
    }
}
