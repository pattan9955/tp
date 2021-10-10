package fridgy.logic.commands.recipe;

import static fridgy.testutil.TypicalRecipes.BURGER;
import static fridgy.testutil.TypicalRecipes.MAGGIE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fridgy.commons.core.index.Index;
import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.ReadOnlyRecipeBook;
import fridgy.model.RecipeBook;
import fridgy.model.RecipeModel;
import fridgy.model.recipe.Recipe;
import javafx.collections.ObservableList;

public class DeleteRecipeCommandTest {
    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteRecipeCommand(null));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        DeleteRecipeCommand testCommand = new DeleteRecipeCommand(Index.fromZeroBased(4));
        assertTrue(testCommand.equals(testCommand));
    }

    @Test
    public void equals_differentCommand_returnsFalse() {
        DeleteRecipeCommand testCommand = new DeleteRecipeCommand(Index.fromZeroBased(3));
        DeleteRecipeCommand targetCommand = new DeleteRecipeCommand(Index.fromZeroBased(4));
        assertFalse(testCommand.equals(targetCommand));
    }

    @Test
    public void equals_equalCommand_returnsTrue() {
        DeleteRecipeCommand testCommand = new DeleteRecipeCommand(Index.fromZeroBased(3));
        DeleteRecipeCommand targetCommand = new DeleteRecipeCommand(Index.fromZeroBased(3));
        DeleteRecipeCommand targetCommand2 = new DeleteRecipeCommand(Index.fromOneBased(4));
        assertTrue(testCommand.equals(targetCommand));
        assertTrue(testCommand.equals(targetCommand2));
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        DeleteRecipeCommand testCommand = new DeleteRecipeCommand(Index.fromZeroBased(2));
        Object targetObj = new String("2");
        assertFalse(testCommand.equals(targetObj));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        DeleteRecipeCommand testCommand = new DeleteRecipeCommand(Index.fromZeroBased(1));
        assertThrows(NullPointerException.class, () -> testCommand.execute(null));
    }

    @Test
    public void execute_targetIndexLargerThanList_throwsCommandException() {
        DeleteRecipeCommand testCommand = new DeleteRecipeCommand(Index.fromZeroBased(3));
        RecipeModelStubWithRecipe testModel = new RecipeModelStubWithRecipe();
        assertThrows(CommandException.class, () -> testCommand.execute(testModel));
    }

    @Test
    public void execute_validTargetIndex_deletesSpecifiedRecipe() {
        DeleteRecipeCommand testCommand = new DeleteRecipeCommand(Index.fromZeroBased(0));
        RecipeModelStubWithRecipe testModel = new RecipeModelStubWithRecipe();
        testModel.addRecipe(MAGGIE);
        CommandResult expected = new CommandResult(
                String.format(DeleteRecipeCommand.MESSAGE_SUCCESS, BURGER));
        try {
            CommandResult result = testCommand.execute(testModel);
            assertTrue(result.equals(expected));
            assertFalse(testModel.hasRecipe(BURGER));
            assertTrue(testModel.hasRecipe(MAGGIE));
        } catch (CommandException e) {
            Assertions.fail("CommandException thrown!");
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
        private RecipeBook recipeBook = new RecipeBook();

        @Override
        public ObservableList<Recipe> getFilteredRecipeList() {
            return recipeBook.getList();
        }

        @Override
        public boolean hasRecipe(Recipe recipe) {
            return recipeBook.has(recipe);
        }

        @Override
        public void deleteRecipe(Recipe target) {
            this.recipeBook.remove(target);
        }

        @Override
        public void addRecipe(Recipe recipe) {
            this.recipeBook.add(recipe);
        }

        @Override
        public void setRecipe(Recipe target, Recipe editedRecipe) {
            this.recipeBook.set(target, editedRecipe);
        }
    }

    private class RecipeModelStubWithRecipe extends RecipeModelStubNoRecipe {
        public RecipeModelStubWithRecipe() {
            super.recipeBook.add(BURGER);
        }
    }
}
