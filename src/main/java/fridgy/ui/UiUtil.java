package fridgy.ui;

/**
 * A utility class for UI components.
 */
public class UiUtil {

    /**
     * Truncates text according to the {@code limit} specified
     */
    public static String truncateText(String textToBeTruncated, int limit) {
        String truncatedText = textToBeTruncated;
        if (textToBeTruncated.length() > limit) {
            truncatedText = truncatedText.substring(0, limit) + "...";
        }
        return truncatedText;
    }
}
