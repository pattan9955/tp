package fridgy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import fridgy.commons.exceptions.DataConversionException;
import fridgy.model.ReadOnlyInventory;
import fridgy.model.ReadOnlyUserPrefs;
import fridgy.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends InventoryStorage, RecipeBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getInventoryFilePath();

    @Override
    Optional<ReadOnlyInventory> readInventory() throws DataConversionException, IOException;

    @Override
    void saveInventory(ReadOnlyInventory addressBook) throws IOException;

}
