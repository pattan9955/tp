package fridgy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static fridgy.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import fridgy.commons.exceptions.IllegalValueException;
import fridgy.commons.util.JsonUtil;
import fridgy.model.AddressBook;
import fridgy.testutil.Assert;
import org.junit.jupiter.api.Test;

import fridgy.testutil.TypicalIngredients;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_INGREDIENTS_FILE = TEST_DATA_FOLDER.resolve("typicalIngredientsAddressBook.json");
    private static final Path INVALID_INGREDIENT_FILE = TEST_DATA_FOLDER.resolve("invalidIngredientAddressBook.json");
    private static final Path DUPLICATE_INGREDIENT_FILE = TEST_DATA_FOLDER.resolve("duplicateIngredientAddressBook.json");

    @Test
    public void toModelType_typicalIngredientsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_INGREDIENTS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalIngredientsAddressBook = TypicalIngredients.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalIngredientsAddressBook);
    }

    @Test
    public void toModelType_invalidIngredientFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_INGREDIENT_FILE,
                JsonSerializableAddressBook.class).get();
        Assert.assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateIngredients_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_INGREDIENT_FILE,
                JsonSerializableAddressBook.class).get();
        Assert.assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_INGREDIENT,
                dataFromFile::toModelType);
    }

}
