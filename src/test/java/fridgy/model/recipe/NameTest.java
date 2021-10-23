package fridgy.model.recipe;

import static fridgy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new fridgy.model.recipe.Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new fridgy.model.recipe.Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> fridgy.model.recipe.Name.isValidName(null));

        // invalid name
        assertFalse(fridgy.model.recipe.Name.isValidName("")); // empty string
        assertFalse(fridgy.model.recipe.Name.isValidName(" ")); // spaces only
        assertFalse(fridgy.model.recipe.Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(fridgy.model.recipe.Name.isValidName("pear*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(fridgy.model.recipe.Name.isValidName("pear compote")); // alphabets only
        assertTrue(fridgy.model.recipe.Name.isValidName("12345")); // numbers only
        assertTrue(fridgy.model.recipe.Name.isValidName("pear the 2nd")); // alphanumeric characters
        assertTrue(fridgy.model.recipe.Name.isValidName("Chocolate Bar")); // with capital letters
        assertTrue(Name.isValidName("Nestle dark choco bar with 90 percent chocolate")); // long names
    }
}
