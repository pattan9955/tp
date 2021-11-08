package fridgy.logic.commands.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import fridgy.model.Inventory;
import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.UserPrefs;
import fridgy.testutil.TypicalRecipes;

public class ClearRecipeCommandTest {
    @Test
    public void execute_emptyRecipeBook_success() {
        Model testModel = new ModelManager();
        Model expectedModel = new ModelManager();

        RecipeCommandTestUtil.assertRecipeCommandSuccess(new ClearRecipeCommand(), testModel,
                ClearRecipeCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyRecipeBook_success() {
        Model testModel = new ModelManager(new Inventory(),
                TypicalRecipes.getTypicalRecipeBook(), new UserPrefs());
        Model expectedModel = new ModelManager();

        RecipeCommandTestUtil.assertRecipeCommandSuccess(new ClearRecipeCommand(), testModel,
                ClearRecipeCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals_otherObjectIsNull_returnsFalse() {
        ClearRecipeCommand testCommand = new ClearRecipeCommand();
        assertNotEquals(testCommand, null);
    }

    @Test
    public void equals_otherObjectWrongType_returnsFalse() {
        ClearRecipeCommand testCommand = new ClearRecipeCommand();
        assertNotEquals(testCommand, 5);
    }

    @Test
    public void equals_otherObjectSameObject_returnsTrue() {
        ClearRecipeCommand testCommand = new ClearRecipeCommand();
        assertEquals(testCommand, testCommand);
    }

    @Test
    public void equals_otherObjectDifferentClearRecipeCommand_returnsTrue() {
        ClearRecipeCommand testCommand = new ClearRecipeCommand();
        ClearRecipeCommand targetCommand = new ClearRecipeCommand();
        assertEquals(testCommand, targetCommand);
    }
}
