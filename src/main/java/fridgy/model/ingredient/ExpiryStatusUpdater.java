package fridgy.model.ingredient;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import fridgy.model.tag.Tag;

/**
 * Updates an {@code Ingredient}'s {@code Tag} with expiring status.
 */
public class ExpiryStatusUpdater {

    private static final LocalDate currentDate = LocalDate.now();
    /** An ingredient has to be 7 days away from expiry date to be considered expiring */
    private static final Period EXPIRING_PERIOD = Period.of(0, 0, 7);

    /**
     * Update the ingredient's tags according to its expiry date.
     * "expired" tag will be added if the expiry date is after the system's date.
     * "expiring" tag will be added if the expiring date is within EXPIRING_PERIOD from the expiry date.
     * Any irrelevant expiry tags will be removed.
     *
     * @param ingredient the ingredient to update tags of
     * @return the same ingredient with updated tags
     */
    public static Ingredient updateExpiryTags(Ingredient ingredient) {
        Period periodToExpiry = Period.between(currentDate, ingredient.getExpiryDate().expiryDate);
        Set<Tag> tagSet = new HashSet<>(ingredient.getTags());
        tagSet.remove(Tag.EXPIRED);
        tagSet.remove(Tag.EXPIRING);
        if (periodToExpiry.isNegative()) {
            tagSet.add(Tag.EXPIRED);
        } else {
            Period periodToExpiring = periodToExpiry.minus(EXPIRING_PERIOD).normalized();
            /*
             If period is still negative after normalisation, it means that
             1. it is truly negative
             2. it has a few negative days (if month or year > 0, we can be sure the ingredient is fresh)
            */
            if (periodToExpiring.isNegative()) {
                if (periodToExpiring.getMonths() <= 0 && periodToExpiring.getYears() <= 0) {
                    tagSet.add(Tag.EXPIRING);
                }
            }
        }
        return new Ingredient(ingredient.getName(), ingredient.getQuantity(),
                ingredient.getDescription(), tagSet, ingredient.getExpiryDate());
    }
}
