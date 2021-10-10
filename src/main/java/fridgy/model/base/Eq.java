package fridgy.model.base;

/**
 * Represents a type of objects that has alternate equivalence comparisons than equals.
 */
public interface Eq {
    /**
     * Returns true if a weaker notion of equality hold true for two Eq objects'
     */
    boolean isSame(Eq other);
}
