package fridgy.ui.event;

import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;
import javafx.event.Event;
import javafx.event.EventType;

public class TabSwitchEvent<T> extends Event {

    public static final EventType<TabSwitchEvent<?>> ANY = new EventType<>(Event.ANY, "ANY_TAB_SWITCH");

    // switch to recipe tab AND select the right recipe
    public static final EventType<TabSwitchEvent<Recipe>> RECIPE =
        new EventType<>(TabSwitchEvent.ANY, "TAB_CHANGE_RECIPE");

    // switch to ingredient tab AND select the right ingredient
    public static final EventType<TabSwitchEvent<Ingredient>> INGREDIENT =
        new EventType<>(TabSwitchEvent.ANY, "TAB_CHANGE_INGREDIENT");

    private final T item;

    /**
     * Constructs a custom event for UI active item changes.
     */
    public TabSwitchEvent(EventType<? extends Event> eventType, T item) {
        super(eventType);
        this.item = item;
    }

    public T getItem() {
        return this.item;
    }
}

