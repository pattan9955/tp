package fridgy.model;

import static fridgy.model.RecipeModel.PREDICATE_SHOW_ALL_RECIPES;
import static fridgy.testutil.Assert.assertThrows;
import static fridgy.testutil.TypicalRecipes.BURGER;
import static fridgy.testutil.TypicalRecipes.MAGGIE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fridgy.commons.core.GuiSettings;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.IngredientDefaultComparator;
import fridgy.model.ingredient.NameContainsKeywordsPredicate;
import fridgy.model.recipe.Recipe;
import fridgy.testutil.Assert;
import fridgy.testutil.InventoryBuilder;
import fridgy.testutil.RecipeBookBuilder;
import fridgy.testutil.TypicalIngredients;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        Assertions.assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Inventory(), new Inventory(modelManager.getInventory()));
        assertEquals(new RecipeBook(), new RecipeBook(modelManager.getRecipeBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setInventoryFilePath(Paths.get("address/book/file/path"));
        userPrefs.setRecipeBookFilePath(Paths.get("recipe/book/file/path"));

        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setInventoryFilePath(Paths.get("new/address/book/file/path"));
        userPrefs.setRecipeBookFilePath(Paths.get("new/book/file/path"));

        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        Assertions.assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    //==================Inventory Section======================
    @Test
    public void setInventoryFilePath_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setInventoryFilePath(null));
    }

    @Test
    public void setInventoryFilePath_validPath_setsInventoryFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setInventoryFilePath(path);
        assertEquals(path, modelManager.getInventoryFilePath());
    }

    @Test
    public void hasIngredient_nullIngredient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.has((Ingredient) null));
    }

    @Test
    public void hasIngredient_ingredientNotInInventory_returnsFalse() {
        assertFalse(modelManager.has(TypicalIngredients.APPLE));
    }

    @Test
    public void hasIngredient_ingredientInInventory_returnsTrue() {
        modelManager.add(TypicalIngredients.APPLE);
        assertTrue(modelManager.has(TypicalIngredients.APPLE));
    }

    @Test
    public void getFilteredIngredientList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () ->
                modelManager.getFilteredIngredientList().remove(0));
    }

    //=============Recipe Book Section====================
    @Test
    public void setRecipeBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setRecipeBookFilePath(null));
    }

    @Test
    public void setRecipeBookFilePath_validPath_setsRecipeBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setRecipeBookFilePath(path);
        assertEquals(path, modelManager.getRecipeBookFilePath());
    }

    @Test
    public void hasRecipe_nullRecipe_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.has((Recipe) null));
    }

    @Test
    public void hasRecipe_ingredientNotInRecipeBook_returnsFalse() {
        assertFalse(modelManager.has(BURGER));
    }

    @Test
    public void hasRecipe_ingredientInRecipeBook_returnsTrue() {
        modelManager.add(BURGER);
        assertTrue(modelManager.has(BURGER));
    }

    @Test
    public void getFilteredRecipeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredRecipeList().remove(0));
    }

    @Test
    public void equals() {
        Inventory inventory = new InventoryBuilder().withIngredient(TypicalIngredients.APPLE)
                .withIngredient(TypicalIngredients.BANANA).build();
        Inventory differentInventory = new Inventory();

        RecipeBook recipeBook = new RecipeBookBuilder().withRecipe(BURGER).withRecipe(MAGGIE).build();
        RecipeBook differentRecipeBook = new RecipeBook();

        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(inventory, recipeBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(inventory, recipeBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // sorts object -> returns true
        modelManager.sortIngredient(new IngredientDefaultComparator());
        assertEquals(modelManager, modelManagerCopy);

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different inventory -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentInventory, recipeBook, userPrefs)));

        // different recipeBook -> return false
        assertFalse(modelManager.equals(new ModelManager(inventory, differentRecipeBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = TypicalIngredients.APPLE.getName().fullName.split("\\s+");
        modelManager.updateFilteredIngredientList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(inventory, recipeBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredIngredientList(Model.PREDICATE_SHOW_ALL_INGREDIENTS);

        // different filteredList -> returns false
        modelManager.updateFilteredRecipeList(x -> x.getName().equals(BURGER.getName()));
        assertFalse(modelManager.equals(new ModelManager(inventory, recipeBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setInventoryFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(inventory, recipeBook, differentUserPrefs)));

    }
}
