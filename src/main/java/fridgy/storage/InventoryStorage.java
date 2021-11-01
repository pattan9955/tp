package fridgy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import fridgy.commons.exceptions.DataConversionException;
import fridgy.model.Inventory;
import fridgy.model.base.ReadOnlyDatabase;
import fridgy.model.ingredient.Ingredient;


/**
 * Represents a storage for {@link Inventory}.
 */
public interface InventoryStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getInventoryFilePath();

    /**
     * Returns Inventory data as a {@link ReadOnlyDatabase}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDatabase<Ingredient>> readInventory() throws DataConversionException, IOException;

    /**
     * @see #getInventoryFilePath()
     */
    Optional<ReadOnlyDatabase<Ingredient>> readInventory(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDatabase} to the storage.
     * @param inventory cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveInventory(ReadOnlyDatabase<Ingredient> inventory) throws IOException;

    /**
     * @see #saveInventory(ReadOnlyDatabase)
     */
    void saveInventory(ReadOnlyDatabase<Ingredient> inventory, Path filePath) throws IOException;

}
