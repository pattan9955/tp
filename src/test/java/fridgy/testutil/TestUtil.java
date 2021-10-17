package fridgy.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import fridgy.commons.core.index.Index;
import fridgy.model.Model;
import fridgy.model.ingredient.Ingredient;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the Ingredient in the {@code model}'s Ingredient list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredIngredientList().size() / 2);
    }

    /**
     * Returns the last index of the Ingredient in the {@code model}'s Ingredient list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredIngredientList().size());
    }

    /**
     * Returns the Ingredient in the {@code model}'s Ingredient list at {@code index}.
     */
    public static Ingredient getIngredient(Model model, Index index) {
        return model.getFilteredIngredientList().get(index.getZeroBased());
    }

    /**
     * Returns a string of {@code char} of the length specified in the {@code length}.
     */
    public static String createString(char ch, int length) {
        char[] charArray = new char[length];
        Arrays.fill(charArray, ch);
        return new String(charArray);
    }
}
