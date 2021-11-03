package fridgy.ui;

import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;

/**
 * Observes an active {@code Ingredient} or {@code Recipe} or {@code TabEnum}
 */
public interface Observer {
    void update(Ingredient newItem);
    void update(Recipe newItem);
    void update(TabEnum tab);

    void clearWindow();
}
