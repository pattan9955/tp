package fridgy.logic.commands.recipe;

import static fridgy.testutil.TypicalRecipes.BURGER;
import static fridgy.testutil.TypicalRecipes.MAGGIE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static fridgy.testutil.Assert.assertThrows;
import static fridgy.logic.commands.recipe.AddRecipeCommand.MESSAGE_SUCCESS;

import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.ReadOnlyRecipeBook;
import fridgy.model.RecipeBook;
import fridgy.model.RecipeModel;
import fridgy.model.recipe.Recipe;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.function.Predicate;

public class AddRecipeCommandTest {
    @Test
    public void constructor_nullRecipe_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddRecipeCommand(null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        RecipeModel nullModel = null;
        assertThrows(NullPointerException.class,
                () -> new AddRecipeCommand(BURGER).execute(nullModel));
    }

    @Test
    public void equals_sameCommand_returnsTrue() {
        AddRecipeCommand testCommand = new AddRecipeCommand(MAGGIE);
        assertTrue(testCommand.equals(testCommand));
    }

    @Test
    public void equals_differentCommandSameValue_returnsTrue() {
        AddRecipeCommand testCommand = new AddRecipeCommand(BURGER);
        AddRecipeCommand targetCommand = new AddRecipeCommand(BURGER);
        assertTrue(testCommand.equals(targetCommand));
    }

    @Test
    public void equals_differentCommandDifferentValue_returnsFalse() {
        AddRecipeCommand testCommand = new AddRecipeCommand(BURGER);
        AddRecipeCommand targetCommand = new AddRecipeCommand(MAGGIE);
        assertFalse(testCommand.equals(targetCommand));
    }

    @Test
    public void equals_targetObjectNotCommand_returnsFalse() {
        AddRecipeCommand testCommand = new AddRecipeCommand(BURGER);
        Object targetObject = new String("Burger");
        assertFalse(testCommand.equals(targetObject));
    }

    @Test
    public void equals_targetObjectNull_returnsFalse() {
        AddRecipeCommand testCommand = new AddRecipeCommand(BURGER);
        assertFalse(testCommand.equals(null));
    }

    @Test
    public void execute_duplicateRecipeProvided_throwsCommandException() {
        AddRecipeCommand testCommand = new AddRecipeCommand(BURGER);
        RecipeModelStubWithRecipe testModel = new RecipeModelStubWithRecipe();
        assertThrows(CommandException.class,
                () -> testCommand.execute(testModel));
    }

    @Test
    public void execute_validRecipeProvided_returnsCommandResult() {
        AddRecipeCommand testCommand = new AddRecipeCommand(MAGGIE);
        RecipeModelStubWithRecipe testModel = new RecipeModelStubWithRecipe();
        CommandResult expected = new CommandResult(String.format(MESSAGE_SUCCESS, MAGGIE));
        try {
            assertTrue(testCommand.execute(testModel).equals(expected));
        } catch (CommandException e) {
            Assertions.fail("Exception thrown!");
        }
    }

    private class RecipeModelStub implements RecipeModel {

        @Override
        public Path getRecipeBookFilePath() {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void setRecipeBookFilePath(Path recipeBookFilePath) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void setRecipeBook(ReadOnlyRecipeBook recipeBook) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public ReadOnlyRecipeBook getRecipeBook() {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public boolean hasRecipe(Recipe recipe) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void deleteRecipe(Recipe target) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void addRecipe(Recipe recipe) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void setRecipe(Recipe target, Recipe editedRecipe) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public ObservableList<Recipe> getFilteredRecipeList() {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void updateFilteredRecipeList(Predicate<Recipe> predicate) {
            throw new AssertionError("Should not be used!");
        }
    }

    private class RecipeModelStubNoRecipe extends RecipeModelStub {
        public RecipeBook recipeBook = new RecipeBook();

        @Override
        public boolean hasRecipe(Recipe recipe) {
            return recipeBook.hasRecipe(recipe);
        }

        @Override
        public void deleteRecipe(Recipe target) {
            this.recipeBook.removeRecipe(target);
        }

        @Override
        public void addRecipe(Recipe recipe) {
            this.recipeBook.addRecipe(recipe);
        }

        @Override
        public void setRecipe(Recipe target, Recipe editedRecipe) {
            this.recipeBook.setRecipe(target, editedRecipe);
        }
    }

    private class RecipeModelStubWithRecipe extends RecipeModelStubNoRecipe {
        public RecipeModelStubWithRecipe() {
            super.recipeBook.addRecipe(BURGER);
        }
    }
}
