package fridgy.model.ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fridgy.testutil.Assert;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("pear*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("pear compote")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("pear the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Chocolate Bar")); // with capital letters
        assertTrue(Name.isValidName("Nestle dark choco bar with 90 percent chocolate")); // long names
    }

    @Test
    public void hashCodeTest() {
        Name name = new Name("A");
        Name nameSame = new Name("A");
        Name nameDiff = new Name("B");

        assertEquals(name.hashCode(), name.hashCode());
        assertEquals(name.hashCode(), nameSame.hashCode());
        assertNotEquals(name.hashCode(), nameDiff.hashCode());
    }
}
