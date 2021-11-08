package fridgy.model.base;

import java.util.function.Predicate;

import javafx.collections.ObservableList;


public interface Model<T> {
    /** {@code Predicate} that always evaluate to true */
    Predicate<?> PREDICATE_SHOW_ALL_RECIPES = unused -> true;
    /**
     * Replaces item book data with the data in {@code itemBook}.
     */
    void setDatabase(ReadOnlyDatabase<T> database);

    /** Returns the Database */
    ReadOnlyDatabase<T> getDatabase();


    /**
     * Returns true if an item with the same identity as {@code item} exists in the database.
     */
    boolean has(T item);

    /**
     * Deletes the given item.
     * The item must exist in the database.
     */
    void delete(T target);

    /**
     * Adds the given item.
     * {@code item} must not already exist in the database.
     */
    void add(T item);

    /**
     * Replaces the given item {@code target} with {@code editedItem}.
     * {@code target} must exist in the database.
     * The item identity of {@code editedItem} must not be the same as another existing item in the database.
     */
    void set(T target, T editedItem);

    /** Returns an unmodifiable view of the filtered item list */
    ObservableList<T> getFilteredList();

    /**
     * Updates the filter of the filtered item list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredList(Predicate<T> predicate);

}
