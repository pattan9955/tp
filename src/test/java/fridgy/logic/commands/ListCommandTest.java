package fridgy.logic.commands;

import static fridgy.logic.commands.CommandTestUtil.assertCommandSuccess;

import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.RecipeBook;
import fridgy.model.UserPrefs;
import fridgy.testutil.TypicalIndexes;
import fridgy.testutil.TypicalIngredients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalIngredients.getTypicalInventory(), new RecipeBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getInventory(), new RecipeBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        CommandTestUtil.assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        CommandTestUtil.showIngredientAtIndex(model, TypicalIndexes.INDEX_FIRST_INGREDIENT);
        CommandTestUtil.assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
