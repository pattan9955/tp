package fridgy.logic.parser;

import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.Model;

@FunctionalInterface
public interface CommandExecutor {
    CommandResult apply(Model model) throws CommandException;
}
