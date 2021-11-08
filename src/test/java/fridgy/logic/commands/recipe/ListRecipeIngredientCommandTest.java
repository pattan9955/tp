package fridgy.logic.commands.recipe;

import static fridgy.logic.commands.recipe.ListRecipeCommand.MESSAGE_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fridgy.logic.commands.CommandResult;
import fridgy.model.Inventory;
import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.UserPrefs;
import fridgy.testutil.TypicalIndexes;
import fridgy.testutil.TypicalRecipes;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListRecipeCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new Inventory(), TypicalRecipes.getTypicalRecipeBook(), new UserPrefs());
        expectedModel = new ModelManager(new Inventory(), model.getRecipeBook(), new UserPrefs());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        ListRecipeCommand testCommand = new ListRecipeCommand();
        assertTrue(testCommand.equals(testCommand));
    }

    @Test
    public void equals_differentCommandObject_returnsTrue() {
        ListRecipeCommand testCommand = new ListRecipeCommand();
        ListRecipeCommand targetCommand = new ListRecipeCommand();
        assertTrue(testCommand.equals(targetCommand));
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        ListRecipeCommand testCommand = new ListRecipeCommand();
        Object targetObj = "2";
        assertFalse(testCommand.equals(targetObj));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        ListRecipeCommand testCommand = new ListRecipeCommand();
        assertThrows(NullPointerException.class, () -> testCommand.execute(null));
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        ListRecipeCommand testCommand = new ListRecipeCommand();
        CommandResult expected = new CommandResult(MESSAGE_SUCCESS);

        assertTrue(testCommand.execute(model).equals(expected));
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        ListRecipeCommand testCommand = new ListRecipeCommand();
        CommandResult expected = new CommandResult(MESSAGE_SUCCESS);

        RecipeCommandTestUtil.showRecipeAtIndex(model, TypicalIndexes.INDEX_FIRST_INGREDIENT);
        assertTrue(testCommand.execute(model).equals(expected));
        assertEquals(expectedModel, model);
    }
}
