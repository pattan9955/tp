package fridgy.model.ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fridgy.testutil.Assert;

public class ExpiryDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ExpiryDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ExpiryDate(invalidDate));
    }

    @Test
    public void isValidExpiryDate() {
        // null expiry date
        Assert.assertThrows(NullPointerException.class, () -> ExpiryDate.isValidExpiry(null));

        // invalid expiry date
        assertFalse(ExpiryDate.isValidExpiry("")); // empty string
        assertFalse(ExpiryDate.isValidExpiry(" ")); // spaces only
        assertFalse(ExpiryDate.isValidExpiry("^.][")); // only non-alphanumeric characters
        assertFalse(ExpiryDate.isValidExpiry("24/10/2021")); // Wrong format, using "/" instead of "-"
        assertFalse(ExpiryDate.isValidExpiry("99-05-2021")); // excessive number of days
        assertFalse(ExpiryDate.isValidExpiry("05-99-2021")); // excessive number of months
        assertFalse(ExpiryDate.isValidExpiry("00-00-0000")); // date does not exist
        assertFalse(ExpiryDate.isValidExpiry("29-02-2021")); // date does not exist


        // valid expiry date
        assertTrue(ExpiryDate.isValidExpiry("24-10-2021")); // correct date format
        assertTrue(ExpiryDate.isValidExpiry("25-10-2090")); // numbers only
    }

    @Test
    public void hashCodeTest() {
        ExpiryDate expDate = new ExpiryDate("24-10-2021");
        ExpiryDate expDateSame = new ExpiryDate("24-10-2021");
        ExpiryDate expDateDiff = new ExpiryDate("26-10-2021");

        assertEquals(expDate.hashCode(), expDate.hashCode());
        assertEquals(expDateSame.hashCode(), expDate.hashCode());
        assertNotEquals(expDate.hashCode(), expDateDiff.hashCode());
    }

    @Test
    public void expiredTest() {
        assertTrue(new ExpiryDate("24-10-1979").isExpired()); // expired
        assertFalse(new ExpiryDate("25-10-2090").isExpired()); // not expired
    }
}
