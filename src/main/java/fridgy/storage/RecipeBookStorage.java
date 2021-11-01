package fridgy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import fridgy.commons.exceptions.DataConversionException;
import fridgy.model.base.ReadOnlyDatabase;
import fridgy.model.recipe.Recipe;


/**
 * Represents a storage for {@link fridgy.model.RecipeBook}.
 */
public interface RecipeBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getRecipeBookFilePath();

    /**
     * Returns RecipeBook data as a {@link ReadOnlyDatabase}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDatabase<Recipe>> readRecipeBook() throws DataConversionException, IOException;

    /**
     * @see #getRecipeBookFilePath()
     */
    Optional<ReadOnlyDatabase<Recipe>> readRecipeBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDatabase} to the storage.
     * @param recipeBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRecipeBook(ReadOnlyDatabase<Recipe> recipeBook) throws IOException;

    /**
     * @see #saveRecipeBook(ReadOnlyDatabase)
     */
    void saveRecipeBook(ReadOnlyDatabase<Recipe> recipeBook, Path filePath) throws IOException;

}
