package fridgy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static fridgy.testutil.Assert.assertThrows;
import static fridgy.testutil.TypicalIngredients.ALICE;
import static fridgy.testutil.TypicalIngredients.HOON;
import static fridgy.testutil.TypicalIngredients.IDA;
import static fridgy.testutil.TypicalIngredients.getTypicalInventory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import fridgy.commons.exceptions.DataConversionException;
import fridgy.model.Inventory;
import fridgy.model.ReadOnlyInventory;
import fridgy.testutil.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class JsonInventoryStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonInventoryStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readInventory_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readInventory(null));
    }

    private java.util.Optional<ReadOnlyInventory> readInventory(String filePath) throws Exception {
        return new JsonInventoryStorage(Paths.get(filePath)).readInventory(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readInventory("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readInventory("notJsonFormatInventory.json"));
    }

    @Test
    public void readInventory_invalidIngredientInventory_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readInventory("invalidIngredientInventory.json"));
    }

    @Test
    public void readInventory_invalidAndValidIngredientInventory_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readInventory("invalidAndValidIngredientInventory.json"));
    }

    @Test
    public void readAndSaveInventory_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempInventory.json");
        Inventory original = getTypicalInventory();
        JsonInventoryStorage jsonInventoryStorage = new JsonInventoryStorage(filePath);

        // Save in new file and read back
        jsonInventoryStorage.saveInventory(original, filePath);
        ReadOnlyInventory readBack = jsonInventoryStorage.readInventory(filePath).get();
        assertEquals(original, new Inventory(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addIngredient(HOON);
        original.removeIngredient(ALICE);
        jsonInventoryStorage.saveInventory(original, filePath);
        readBack = jsonInventoryStorage.readInventory(filePath).get();
        assertEquals(original, new Inventory(readBack));

        // Save and read without specifying file path
        original.addIngredient(IDA);
        jsonInventoryStorage.saveInventory(original); // file path not specified
        readBack = jsonInventoryStorage.readInventory().get(); // file path not specified
        assertEquals(original, new Inventory(readBack));

    }

    @Test
    public void saveInventory_nullInventory_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveInventory(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveInventory(ReadOnlyInventory addressBook, String filePath) {
        try {
            new JsonInventoryStorage(Paths.get(filePath))
                    .saveInventory(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveInventory_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveInventory(new Inventory(), null));
    }
}
