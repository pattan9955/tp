package fridgy.model.base;

/**
 * Represents a type of objects that has alternate equivalence comparisons than equals.
 */
public interface Eq {
    boolean isSame(Eq other);
}
