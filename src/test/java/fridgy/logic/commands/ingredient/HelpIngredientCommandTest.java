package fridgy.logic.commands;

import org.junit.jupiter.api.Test;

import fridgy.model.Model;
import fridgy.model.ModelManager;

public class HelpIngredientCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(HelpIngredientCommand.SHOWING_HELP_MESSAGE, true, false);
        CommandTestUtil.assertCommandSuccess(new HelpIngredientCommand(), model, expectedCommandResult, expectedModel);
    }
}
