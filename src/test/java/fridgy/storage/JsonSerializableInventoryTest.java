package fridgy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import fridgy.commons.exceptions.IllegalValueException;
import fridgy.commons.util.JsonUtil;
import fridgy.model.Inventory;
import fridgy.testutil.Assert;
import fridgy.testutil.TypicalIngredients;

public class JsonSerializableInventoryTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableInventoryTest");
    private static final Path TYPICAL_INGREDIENTS_FILE = TEST_DATA_FOLDER.resolve("typicalIngredientsInventory.json");
    private static final Path INVALID_INGREDIENT_FILE = TEST_DATA_FOLDER.resolve("invalidIngredientInventory.json");
    private static final Path DUPLICATE_INGREDIENT_FILE = TEST_DATA_FOLDER.resolve("duplicateIngredientInventory.json");

    @Test
    public void toModelType_typicalIngredientsFile_success() throws Exception {
        JsonSerializableInventory dataFromFile = JsonUtil.readJsonFile(TYPICAL_INGREDIENTS_FILE,
                JsonSerializableInventory.class).get();
        Inventory inventoryFromFile = dataFromFile.toModelType();
        Inventory typicalIngredientsInventory = TypicalIngredients.getTypicalInventory();
        assertEquals(inventoryFromFile, typicalIngredientsInventory);
    }

    @Test
    public void toModelType_invalidIngredientFile_throwsIllegalValueException() throws Exception {
        JsonSerializableInventory dataFromFile = JsonUtil.readJsonFile(INVALID_INGREDIENT_FILE,
                JsonSerializableInventory.class).get();
        Assert.assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateIngredients_throwsIllegalValueException() throws Exception {
        JsonSerializableInventory dataFromFile = JsonUtil.readJsonFile(DUPLICATE_INGREDIENT_FILE,
                JsonSerializableInventory.class).get();
        Assert.assertThrows(IllegalValueException.class, JsonSerializableInventory.MESSAGE_DUPLICATE_INGREDIENT,
                dataFromFile::toModelType);
    }

}
