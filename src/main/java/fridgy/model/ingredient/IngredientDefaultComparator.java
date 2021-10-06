package fridgy.model.ingredient;

import java.util.Comparator;

/**
 * The default comparator for {@code Ingredient},
 * which compares ingredients by their expiry dates.
 */
public class IngredientDefaultComparator implements Comparator<Ingredient> {

    /**
     * Compares recipes using expiry dates.
     * If expiry dates are equal, compare them by name in lexicographical order.
     */
    @Override
    public int compare(Ingredient i1, Ingredient i2) {
        return i1.getExpiryDate().compareTo(i2.getExpiryDate()) == 0
                ? i1.getName().toString().compareTo(i2.getName().toString())
                : i1.getExpiryDate().compareTo(i2.getExpiryDate());
    }
}
