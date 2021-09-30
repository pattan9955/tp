package fridgy.model.ingredient;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Tests that a {@code Ingredient}'s {@code Name} matches any of the keywords given.
 */
public class ExpiredPredicate implements Predicate<Ingredient> {

    private final ExpiryDate localDate = new ExpiryDate(LocalDate.now().format(ExpiryDate.DATE_FORMATTER));

    @Override
    public boolean test(Ingredient ingredient) {
        return ingredient.getExpiryDate().compareTo(localDate) < 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpiredPredicate // instanceof handles nulls
                && localDate.equals(((ExpiredPredicate) other).localDate)); // state check
    }

}
