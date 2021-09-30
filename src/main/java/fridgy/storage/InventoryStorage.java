package fridgy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import fridgy.commons.exceptions.DataConversionException;
import fridgy.model.Inventory;
import fridgy.model.ReadOnlyInventory;

/**
 * Represents a storage for {@link Inventory}.
 */
public interface InventoryStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getInventoryFilePath();

    /**
     * Returns Inventory data as a {@link ReadOnlyInventory}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyInventory> readInventory() throws DataConversionException, IOException;

    /**
     * @see #getInventoryFilePath()
     */
    Optional<ReadOnlyInventory> readInventory(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyInventory} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveInventory(ReadOnlyInventory addressBook) throws IOException;

    /**
     * @see #saveInventory(ReadOnlyInventory)
     */
    void saveInventory(ReadOnlyInventory addressBook, Path filePath) throws IOException;

}
