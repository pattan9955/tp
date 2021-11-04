package fridgy.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import fridgy.commons.util.AppUtil;

/**
 * Represents a Tag in the Inventory.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {
    public static final String[] RESERVED_KEYWORDS = {"expired", "expiring"};
    public static final String MESSAGE_CONSTRAINTS = "Tags should be alphanumeric, and should not use reserved "
            + "keywords in any case: " + Arrays.toString(RESERVED_KEYWORDS) + ".\n"
            + "Tags with multiple words must only be separated by a single space.";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9]+( [a-zA-Z0-9]+)*$";
    public static final Tag EXPIRED = new Tag("expired");
    public static final Tag EXPIRING = new Tag("expiring");
    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        AppUtil.checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
    }

    public String getTag() {
        return tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string matches with any reserved keywords.
     * @param test string to be checked
     * @return boolean true if it matches a reserved keyword, false otherwise.
     */
    public static boolean isReservedTag(String test) {
        for (String reserved: RESERVED_KEYWORDS) {
            if (test.equalsIgnoreCase(reserved)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
