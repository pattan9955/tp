package fridgy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static fridgy.testutil.Assert.assertThrows;
import static fridgy.testutil.TypicalRecipes.BURGER;
import static fridgy.testutil.TypicalRecipes.NOODLE;
import static fridgy.testutil.TypicalRecipes.RICE;
import static fridgy.testutil.TypicalRecipes.getTypicalRecipeBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import fridgy.commons.exceptions.DataConversionException;
import fridgy.model.ReadOnlyRecipeBook;
import fridgy.model.RecipeBook;



public class JsonRecipeBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonRecipeBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readRecipeBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readRecipeBook(null));
    }

    private java.util.Optional<ReadOnlyRecipeBook> readRecipeBook(String filePath) throws Exception {
        return new JsonRecipeBookStorage(Paths.get(filePath)).readRecipeBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readRecipeBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readRecipeBook("notJsonFormatRecipeBook.json"));
    }

    @Test
    public void readRecipeBook_invalidRecipeBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readRecipeBook("invalidRecipeBook.json"));
    }

    @Test
    public void readRecipeBook_invalidAndValidRecipeBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readRecipeBook("invalidAndValidRecipeBook.json"));
    }

    @Test
    public void readAndSaveRecipeBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempRecipeBook.json");
        RecipeBook original = getTypicalRecipeBook();
        JsonRecipeBookStorage jsonRecipeBookStorage = new JsonRecipeBookStorage(filePath);

        // Save in new file and read back
        jsonRecipeBookStorage.saveRecipeBook(original, filePath);
        ReadOnlyRecipeBook readBack = jsonRecipeBookStorage.readRecipeBook(filePath).get();
        assertEquals(original, new RecipeBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addRecipe(RICE);
        original.removeRecipe(BURGER);
        jsonRecipeBookStorage.saveRecipeBook(original, filePath);
        readBack = jsonRecipeBookStorage.readRecipeBook(filePath).get();
        assertEquals(original, new RecipeBook(readBack));

        // Save and read without specifying file path
        original.addRecipe(NOODLE);
        jsonRecipeBookStorage.saveRecipeBook(original); // file path not specified
        readBack = jsonRecipeBookStorage.readRecipeBook().get(); // file path not specified
        assertEquals(original, new RecipeBook(readBack));

    }

    @Test
    public void saveRecipeBook_nullRecipeBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveRecipeBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveRecipeBook(ReadOnlyRecipeBook addressBook, String filePath) {
        try {
            new JsonRecipeBookStorage(Paths.get(filePath))
                    .saveRecipeBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveRecipeBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveRecipeBook(new RecipeBook(), null));
    }
}
