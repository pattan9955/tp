package fridgy.ui.event;

import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;
import javafx.event.Event;
import javafx.event.EventType;

public class ActiveItemChangeEvent<T> extends Event {

    public static final EventType<ActiveItemChangeEvent<?>> ANY = new EventType<>(Event.ANY, "ANY_ACTIVE");
    public static final EventType<ActiveItemChangeEvent<Recipe>> RECIPE =
        new EventType<>(ActiveItemChangeEvent.ANY, "ACTIVE_CHANGE_RECIPE");
    public static final EventType<ActiveItemChangeEvent<Ingredient>> INGREDIENT =
        new EventType<>(ActiveItemChangeEvent.ANY, "ACTIVE_CHANGE_INGREDIENT");
    public static final EventType<ActiveItemChangeEvent<?>> CLEAR = new EventType<>(Event.ANY, "ACTIVE_CLEAR_SCREEN");

    private final T item;

    /**
     * Constructs a custom event for UI active item changes.
     */
    public ActiveItemChangeEvent(EventType<? extends Event> eventType, T item) {
        super(eventType);
        this.item = item;
    }

    public T getItem() {
        return this.item;
    }
}

