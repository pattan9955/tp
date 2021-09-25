package fridgy.logic.commands;

import static fridgy.logic.commands.CommandTestUtil.assertCommandSuccess;

import fridgy.model.AddressBook;
import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.UserPrefs;
import fridgy.testutil.TypicalIngredients;
import org.junit.jupiter.api.Test;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(TypicalIngredients.getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalIngredients.getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
