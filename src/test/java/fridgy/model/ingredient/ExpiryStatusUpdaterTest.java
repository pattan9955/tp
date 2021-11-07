package fridgy.model.ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import org.junit.jupiter.api.Test;

import fridgy.model.tag.Tag;
import fridgy.testutil.IngredientBuilder;

public class ExpiryStatusUpdaterTest {
    @Test
    public void updateExpiryTags_updateCorrectly() {
        // setting as expired
        Ingredient expiredIngredient = new IngredientBuilder().withExpiryDate("20-10-1979").build();
        Set<Tag> setWithDefaultExpired = Set.of(Tag.EXPIRED);
        assertEquals(setWithDefaultExpired, ExpiryStatusUpdater.updateExpiryTags(expiredIngredient).getTags());

        // ingredient expiring today
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Ingredient expiringIngredient = new IngredientBuilder().withExpiryDate(currentDate).build();
        Set<Tag> setWithExpiring = Set.of(Tag.EXPIRING);
        assertEquals(setWithExpiring, ExpiryStatusUpdater.updateExpiryTags(expiringIngredient).getTags());

        // ingredient expiring long in the future
        Period decade = Period.of(10, 0, 0);
        String newDate = LocalDate.now().plus(decade).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Ingredient freshIngredient = new IngredientBuilder().withExpiryDate(newDate).build();
        Set<Tag> emptyTagSet = Set.of();
        assertEquals(emptyTagSet, ExpiryStatusUpdater.updateExpiryTags(freshIngredient).getTags());
    }

}
