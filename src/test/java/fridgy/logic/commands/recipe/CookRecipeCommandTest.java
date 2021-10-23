package fridgy.logic.commands.recipe;

import static fridgy.logic.commands.recipe.CookRecipeCommand.NOT_ENOUGH_INGR;
import static fridgy.testutil.TypicalIngredients.CHICKEN;
import static fridgy.testutil.TypicalIngredients.FLOUR;
import static fridgy.testutil.TypicalRecipes.RICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.index.Index;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.Inventory;
import fridgy.model.ModelManager;
import fridgy.model.RecipeBook;
import fridgy.model.RecipeModel;
import fridgy.model.UserPrefs;
import fridgy.testutil.IngredientBuilder;

public class CookRecipeCommandTest {

    private ModelManager modelManager = new ModelManager(new Inventory(), new RecipeBook(), new UserPrefs());

    @Test
    public void constructor_nullRecipe_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CookRecipeCommand(null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        RecipeModel nullModel = null;
        assertThrows(NullPointerException.class, () ->
                new CookRecipeCommand(Index.fromOneBased(1)).execute(nullModel));
    }

    @Test
    public void equals_sameCommand_returnsTrue() {
        CookRecipeCommand testCommand = new CookRecipeCommand(Index.fromOneBased(1));
        assertTrue(testCommand.equals(testCommand));
    }

    @Test
    public void equals_differentCommandSameValue_returnsTrue() {
        CookRecipeCommand testCommand = new CookRecipeCommand(Index.fromOneBased(1));
        CookRecipeCommand targetCommand = new CookRecipeCommand(Index.fromOneBased(1));
        assertTrue(testCommand.equals(targetCommand));
    }

    @Test
    public void equals_differentCommandDifferentValue_returnsFalse() {
        CookRecipeCommand testCommand = new CookRecipeCommand(Index.fromOneBased(1));
        CookRecipeCommand targetCommand = new CookRecipeCommand(Index.fromOneBased(2));
        assertFalse(testCommand.equals(targetCommand));
    }

    @Test
    public void equals_targetObjectNotCommand_returnsFalse() {
        CookRecipeCommand testCommand = new CookRecipeCommand(Index.fromOneBased(1));
        Object targetObject = new String("Random");
        assertFalse(testCommand.equals(targetObject));
    }

    @Test
    public void equals_targetObjectNull_returnsFalse() {
        CookRecipeCommand testCommand = new CookRecipeCommand(Index.fromOneBased(1));
        assertFalse(testCommand.equals(null));
    }

    @Test
    public void execute_zeroOfRequiredIngredient_commandExceptionThrown() {
        // Adding ingredients
        modelManager.add(CHICKEN);
        modelManager.add(FLOUR);
        // Adding a recipe that doesn't use any of the ingredients
        modelManager.add(RICE);

        CookRecipeCommand cookRecipeCommand = new CookRecipeCommand(Index.fromOneBased(1));
        CommandException e = assertThrows(CommandException.class, () -> cookRecipeCommand.execute(modelManager));
        assertEquals(String.format(NOT_ENOUGH_INGR, modelManager.getRecipeBook().getList().get(0)), e.getMessage());
    }

    @Test
    public void execute_notEnoughRequiredIngredient_commandExceptionThrown() {
        // Adding ingredients
        modelManager.add(CHICKEN);
        modelManager.add(FLOUR);
        // insufficient quantity
        modelManager.add(new IngredientBuilder().withName("rice")
                .withQuantity("200g").withExpiryDate("20-02-2090").build());
        // Adding a recipe that uses rice
        modelManager.add(RICE);

        CookRecipeCommand cookRecipeCommand = new CookRecipeCommand((Index.fromOneBased(1)));
        CommandException e = assertThrows(CommandException.class, () -> cookRecipeCommand.execute(modelManager));
        assertEquals(String.format(NOT_ENOUGH_INGR, modelManager.getRecipeBook().getList().get(0)), e.getMessage());
    }


    @Test
    public void execute_expiredRequiredIngredient_commandExcpetionThrown() {
        // Adding an expired ingredient with appropriate quantity and name, but expired
        modelManager.add(new IngredientBuilder().withName("rice").withQuantity("500g").build());
        // Others ingredients
        modelManager.add(CHICKEN);
        modelManager.add(FLOUR);
        // Adding a recipe that uses the expired ingredient (insufficient)
        modelManager.add(RICE);
        CookRecipeCommand cookRecipeCommand = new CookRecipeCommand((Index.fromOneBased(1)));
        CommandException e = assertThrows(CommandException.class, () -> cookRecipeCommand.execute(modelManager));
        assertEquals(String.format(NOT_ENOUGH_INGR, modelManager.getRecipeBook().getList().get(0)), e.getMessage());
    }

    @Test
    public void execute_noIngredientsInInventory_commandExceptionThrown() {
        modelManager.add(RICE);
        CookRecipeCommand cookRecipeCommand = new CookRecipeCommand((Index.fromOneBased(1)));
        CommandException e = assertThrows(CommandException.class, () -> cookRecipeCommand.execute(modelManager));
        assertEquals(String.format(NOT_ENOUGH_INGR, modelManager.getRecipeBook().getList().get(0)), e.getMessage());
    }

    @Test
    public void execute_successfulDeduction_success() {
        modelManager.add(new IngredientBuilder().withName("rice")
                .withQuantity("500g").withExpiryDate("24-10-2090").build());
        modelManager.add(CHICKEN);
        modelManager.add(FLOUR);
        modelManager.add(RICE);
        CookRecipeCommand cookRecipeCommand = new CookRecipeCommand((Index.fromOneBased(1)));
        try {
            assertEquals(String.format(CookRecipeCommand.MESSAGE_SUCCESS, RICE),
                    cookRecipeCommand.execute(modelManager).getFeedbackToUser());
        } catch (CommandException e) {
            fail();
        }
        // Rice should be completely used up and removed
        assertEquals(2, modelManager.getInventory().getList().size());
    }
}
