package fridgy.model.base;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;

public class Database<T extends Eq> implements ReadOnlyDatabase<T> {
    private final UniqueDataList<T> items;

    public Database() {
        this.items = new UniqueDataList<T>();
    }

    public Database(UniqueDataList<T> items) {
        this.items = items;
    }

    /**
     * Creates a database using the data in the {@code toBeCopied}
     */
    public Database(ReadOnlyDatabase<T> toBeCopied) {
        this();
        resetDatabase(toBeCopied);
    }

    /**
     * Replaces the contents of the data list with {@code items}.
     * {@code items} must not contain duplicate data.
     */
    public void setItems(List<T> items) {
        this.items.replace(items);
    }

    /**
     * Resets the existing data of this {@code Database} with {@code newData}.
     */
    public void resetDatabase(ReadOnlyDatabase<T> newData) {
        requireNonNull(newData);

        setItems(newData.getList());
    }

    /**
     * Returns true if a data with the same identity as {@code item} exists in the database.
     */
    public boolean has(T item) {
        requireNonNull(item);
        return items.contains(item);
    }

    /**
     * Adds a item to the database.
     * The item must not already exist in the database.
     */
    public void add(T p) {
        items.add(p);
    }

    /**
     * Replaces the given item {@code target} in the list with {@code editedT}.
     * {@code target} must exist in the database.
     * The item identity of {@code editedT} must not be the same as another existing item in the database.
     */
    public void set(T target, T editedT) {
        requireNonNull(editedT);

        items.set(target, editedT);
    }

    /**
     * Removes {@code key} from this {@code Inventory}.
     * {@code key} must exist in the database.
     */
    public void remove(T key) {
        items.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return items.asUnmodifiableObservableList().size() + " items";
        // TODO: refine later
    }

    /**
     * Sorts the items by given comparator
     */
    public void sort(Comparator<T> comparator) {
        items.sort(comparator);
    }

    @Override
    public ObservableList<T> getList() {
        return items.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Database<?> // instanceof handles nulls
                && items.equals(((Database<?>) other).items));
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
