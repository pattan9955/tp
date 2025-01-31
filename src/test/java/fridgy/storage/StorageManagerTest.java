package fridgy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static fridgy.testutil.TypicalIngredients.getTypicalInventory;

import java.nio.file.Path;

import fridgy.commons.core.GuiSettings;
import fridgy.model.Inventory;
import fridgy.model.ReadOnlyInventory;
import fridgy.model.UserPrefs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonInventoryStorage addressBookStorage = new JsonInventoryStorage(getTempFilePath("ab"));
        JsonRecipeBookStorage recipeBookStorage = new JsonRecipeBookStorage(getTempFilePath("re"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, recipeBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonInventoryStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonInventoryStorageTest} class.
         */
        Inventory original = getTypicalInventory();
        storageManager.saveInventory(original);
        ReadOnlyInventory retrieved = storageManager.readInventory().get();
        assertEquals(original, new Inventory(retrieved));
    }

    @Test
    public void getInventoryFilePath() {
        assertNotNull(storageManager.getInventoryFilePath());
    }

}
