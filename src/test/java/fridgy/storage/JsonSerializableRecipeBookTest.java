package fridgy.storage;

import static fridgy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import fridgy.commons.exceptions.IllegalValueException;
import fridgy.commons.util.JsonUtil;
import fridgy.model.RecipeBook;
import fridgy.testutil.TypicalRecipes;

public class JsonSerializableRecipeBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableRecipeBookTest");
    private static final Path TYPICAL_RECIPES_FILE = TEST_DATA_FOLDER.resolve("typicalRecipeBook.json");
    private static final Path INVALID_RECIPE_FILE = TEST_DATA_FOLDER.resolve("invalidRecipeBook.json");
    private static final Path DUPLICATE_RECIPE_FILE = TEST_DATA_FOLDER.resolve("duplicateRecipeBook.json");

    @Test
    public void toModelType_typicalFile_success() throws Exception {
        JsonSerializableRecipeBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_RECIPES_FILE,
                JsonSerializableRecipeBook.class).get();
        RecipeBook recipeBookFromFile = dataFromFile.toModelType();
        RecipeBook typicalRecipeBook = TypicalRecipes.getTypicalRecipeBook();
        assertEquals(recipeBookFromFile, typicalRecipeBook);
    }

    @Test
    public void toModelType_invalidFile_throwsIllegalValueException() throws Exception {
        JsonSerializableRecipeBook dataFromFile = JsonUtil.readJsonFile(INVALID_RECIPE_FILE,
                JsonSerializableRecipeBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicates_throwsIllegalValueException() throws Exception {
        JsonSerializableRecipeBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_RECIPE_FILE,
                JsonSerializableRecipeBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableRecipeBook.MESSAGE_DUPLICATE_RECIPE,
                dataFromFile::toModelType);
    }

}
