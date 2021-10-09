package fridgy.logic.parser.recipe;

import fridgy.logic.commands.recipe.RecipeCommand;
import fridgy.logic.parser.exceptions.ParseException;

public interface RecipeCommandParser<T extends RecipeCommand> {
    public T parse(String userInput) throws ParseException;
}
