package fridgy.model.recipe;

import java.util.Comparator;

/**
 * The default comparator for {@code Recipe},
 * which compares recipes by their names.
 */
public class RecipeDefaultComparator implements Comparator<Recipe> {

    /**
     * Compares recipes using recipe names lexicographically
     */
    @Override
    public int compare(Recipe i1, Recipe i2) {
        return i1.getName().toString().compareTo(i2.getName().toString());
    }
}

