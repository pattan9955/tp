package fridgy.ui;

import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;

import static java.util.Objects.requireNonNull;


/**
 * The type Observable that reports changes to an active {@code Ingredient} or {@code Recipe} to an {@code Observer}.
 */
public class Observable {
    private Observer observer;

    public void setObserver(Observer observer) {
        requireNonNull(observer);
        this.observer = observer;
    }

    public void change (Ingredient newItem) {
        requireNonNull(newItem);
        observer.update(newItem);
    }

    public void change (Recipe newItem) {
        requireNonNull(newItem);
        observer.update(newItem);
    }
}
