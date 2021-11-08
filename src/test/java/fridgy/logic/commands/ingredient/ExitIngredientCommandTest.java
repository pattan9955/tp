package fridgy.logic.commands;

import static fridgy.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import fridgy.model.Model;
import fridgy.model.ModelManager;

public class ExitIngredientCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(ExitIngredientCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitIngredientCommand(), model, expectedCommandResult, expectedModel);
    }
}
