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
import fridgy.model.ReadOnlyRecipeBook;


/**
 * A class to access RecipeBook data stored as a json file on the hard disk.
 */
public class JsonRecipeBookStorage implements RecipeBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonRecipeBookStorage.class);

    private Path filePath;

    public JsonRecipeBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getRecipeBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyRecipeBook> readRecipeBook() throws DataConversionException {
        return readRecipeBook(filePath);
    }

    /**
     * Similar to {@link #readRecipeBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyRecipeBook> readRecipeBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableRecipeBook> jsonRecipeBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableRecipeBook.class);
        if (!jsonRecipeBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonRecipeBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveRecipeBook(ReadOnlyRecipeBook addressBook) throws IOException {
        saveRecipeBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveRecipeBook(ReadOnlyRecipeBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveRecipeBook(ReadOnlyRecipeBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableRecipeBook(addressBook), filePath);
    }

}
