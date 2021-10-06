package fridgy.logic.commands;

import org.junit.jupiter.api.Test;

import fridgy.model.Inventory;
import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.UserPrefs;
import fridgy.testutil.TypicalIngredients;

public class ClearCommandTest {

    @Test
    public void execute_emptyInventory_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyInventory_success() {
        Model model = new ModelManager(TypicalIngredients.getTypicalInventory(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalIngredients.getTypicalInventory(), new UserPrefs());
        expectedModel.setInventory(new Inventory());

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
