package fridgy.model.base;

import javafx.collections.ObservableList;

public interface ReadOnlyDatabase<T> {

    /**
     * Returns an unmodifiable view of the items list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<T> getList();
}
