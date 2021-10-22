package fridgy.ui;

import static java.util.Objects.requireNonNull;

import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;


/**
 * The type Observable that reports changes to an active {@code Ingredient} or {@code Recipe} to an {@code Observer}.
 */
public class Observable {
    private Observer observer;

    public void setObserver(Observer observer) {
        requireNonNull(observer);
        this.observer = observer;
    }

    /**
     * Change the item being observed by the observer.
     *
     * @param newItem new Ingredient to be observed by the observer
     */
    public void change (Ingredient newItem) {
        requireNonNull(newItem);
        observer.update(newItem);
    }

    /**
     * Change the item being observed by the observer.
     *
     * @param newItem new Recipe to be observed by the observer
     */
    public void change (Recipe newItem) {
        requireNonNull(newItem);
        observer.update(newItem);
    }
}
