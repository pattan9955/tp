package fridgy.ui;

import java.util.List;

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

    /**
     * Transform a list of objects into a pretty print numbered output using
     * their toString methods.
     */
    public static <T> String numberedList(List<T> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < list.size() + 1; i++) {
            sb.append(i + ". ")
                .append(list.get(i - 1).toString());
            if (!(i == list.size())) {
                sb.append("\n\n");
            } else {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
