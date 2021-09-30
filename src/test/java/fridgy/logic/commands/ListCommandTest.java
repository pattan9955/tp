package fridgy.logic.commands;

import static fridgy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static fridgy.logic.commands.CommandTestUtil.showIngredientAtIndex;
import static fridgy.testutil.TypicalIndexes.INDEX_FIRST_INGREDIENT;
import static fridgy.testutil.TypicalIngredients.getTypicalInventory;
import static fridgy.testutil.TypicalRecipes.getTypicalRecipeBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalInventory(), getTypicalRecipeBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getInventory(), getTypicalRecipeBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showIngredientAtIndex(model, INDEX_FIRST_INGREDIENT);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
