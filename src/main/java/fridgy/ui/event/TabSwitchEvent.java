package fridgy.ui.event;

import fridgy.ui.TabEnum;
import javafx.event.Event;
import javafx.event.EventType;

public class TabSwitchEvent extends Event {

    public static final EventType<TabSwitchEvent> ANY = new EventType<>(Event.ANY, "ANY_TAB_SWITCH");

    // switch to recipe tab AND select the right recipe
    public static final EventType<TabSwitchEvent> CHANGE =
        new EventType<>(TabSwitchEvent.ANY, "TAB_CHANGE");

    private final TabEnum item;

    /**
     * Constructs a custom event for UI active item changes.
     */
    public TabSwitchEvent(EventType<? extends Event> eventType, TabEnum item) {
        super(eventType);
        this.item = item;
    }

    public TabEnum getItem() {
        return this.item;
    }
}

