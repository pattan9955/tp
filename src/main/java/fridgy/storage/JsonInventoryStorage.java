package fridgy.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import fridgy.commons.core.LogsCenter;
import fridgy.commons.exceptions.DataConversionException;
import fridgy.commons.exceptions.IllegalValueException;
import fridgy.commons.util.FileUtil;
import fridgy.commons.util.JsonUtil;
import fridgy.model.ReadOnlyInventory;

/**
 * A class to access Inventory data stored as a json file on the hard disk.
 */
public class JsonInventoryStorage implements InventoryStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonInventoryStorage.class);

    private Path filePath;

    public JsonInventoryStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getInventoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyInventory> readInventory() throws DataConversionException {
        return readInventory(filePath);
    }

    /**
     * Similar to {@link #readInventory()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyInventory> readInventory(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableInventory> jsonInventory = JsonUtil.readJsonFile(
                filePath, JsonSerializableInventory.class);
        if (!jsonInventory.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonInventory.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveInventory(ReadOnlyInventory addressBook) throws IOException {
        saveInventory(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveInventory(ReadOnlyInventory)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveInventory(ReadOnlyInventory addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableInventory(addressBook), filePath);
    }

}
