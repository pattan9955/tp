package fridgy.model.base;

import static fridgy.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import fridgy.model.base.exceptions.DuplicateItemException;
import fridgy.model.recipe.exceptions.DuplicateRecipeException;
import fridgy.model.recipe.exceptions.RecipeNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represent a unique data list.
 * @param <T> Any type that implements {@code Eq}
 */
public class UniqueDataList<T extends Eq> implements Iterable<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    private Optional<Comparator<T>> comparator;

    public UniqueDataList(Comparator<T> comparator) {
        this.comparator = Optional.ofNullable(comparator);
    }
    public UniqueDataList() {
        this.comparator = Optional.empty();
    };

    /**
     * Returns true if the list contains an equivalent item as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSame);
    }

    /**
     * Adds an item to the list.
     * The item must not already exist in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRecipeException();
        }
        internalList.add(toAdd);
        sort();
    }

    /**
     * Replaces the recipe {@code target} in the list with {@code editedItem}.
     * {@code target} must exist in the list.
     * The recipe identity of {@code editedItem} must not be the same as another existing item in the list.
     */
    public void set(T target, T editedItem) {
        requireAllNonNull(target, editedItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RecipeNotFoundException();
        }

        if (!target.isSame(editedItem) && contains(editedItem)) {
            throw new DuplicateRecipeException();
        }

        internalList.set(index, editedItem);
        sort();
    }

    /**
     * Removes the equivalent recipe from the list.
     * The recipe must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RecipeNotFoundException();
        }
        sort();
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     */
    public void replace(UniqueDataList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        sort();
    }

    /**
     * Replaces the contents of this list with {@code recipes}.
     * {@code recipes} must not contain duplicate recipes.
     */
    public void replace(List<T> items) {
        requireAllNonNull(items);
        if (!itemsAreUnique(items)) {
            throw new DuplicateItemException();
        }

        internalList.setAll(items);
        sort();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueDataList<?> // instanceof handles nulls
                && internalList.equals(((UniqueDataList<?>) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Sorts the list of items using the current comparator.
     */
    public void sort() {
        comparator.ifPresent(internalList::sort);
    }

    /**
     * Sorts the list of items using the specified comparator.
     */
    public void sort(Comparator<T> comparator) {
        this.comparator = Optional.ofNullable(comparator);
        sort();
    }

    private boolean itemsAreUnique(List<T> items) {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = i + 1; j < items.size(); j++) {
                if (items.get(i).isSame(items.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
