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

public class ViewRecipeCommandTest {
    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ViewRecipeCommand(null));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        ViewRecipeCommand testCommand = new ViewRecipeCommand(Index.fromZeroBased(4));
        assertTrue(testCommand.equals(testCommand));
    }

    @Test
    public void equals_differentCommand_returnsFalse() {
        ViewRecipeCommand testCommand = new ViewRecipeCommand(Index.fromZeroBased(3));
        ViewRecipeCommand targetCommand = new ViewRecipeCommand(Index.fromZeroBased(4));
        assertFalse(testCommand.equals(targetCommand));
    }

    @Test
    public void equals_equalCommand_returnsTrue() {
        ViewRecipeCommand testCommand = new ViewRecipeCommand(Index.fromZeroBased(3));
        ViewRecipeCommand targetCommand = new ViewRecipeCommand(Index.fromZeroBased(3));
        ViewRecipeCommand targetCommand2 = new ViewRecipeCommand(Index.fromOneBased(4));
        assertTrue(testCommand.equals(targetCommand));
        assertTrue(testCommand.equals(targetCommand2));
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        ViewRecipeCommand testCommand = new ViewRecipeCommand(Index.fromZeroBased(2));
        Object targetObj = new String("2");
        assertFalse(testCommand.equals(targetObj));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        ViewRecipeCommand testCommand = new ViewRecipeCommand(Index.fromZeroBased(1));
        assertThrows(NullPointerException.class, () -> testCommand.execute(null));
    }

    @Test
    public void execute_targetIndexLargerThanList_throwsCommandException() {
        ViewRecipeCommand testCommand = new ViewRecipeCommand(Index.fromZeroBased(3));
        RecipeModelStubWithRecipe testModel = new RecipeModelStubWithRecipe();
        assertThrows(CommandException.class, () -> testCommand.execute(testModel));
    }

    @Test
    public void execute_validTargetIndex_changesSpecifiedActiveRecipe() {
        ViewRecipeCommand testCommand = new ViewRecipeCommand(Index.fromZeroBased(0));
        RecipeModelStubWithRecipe testModel = new RecipeModelStubWithRecipe();
        testModel.addRecipe(MAGGIE);
        CommandResult expected = new CommandResult(
                String.format(ViewRecipeCommand.MESSAGE_SUCCESS, BURGER));
        try {
            CommandResult result = testCommand.execute(testModel);
            assertTrue(result.equals(expected));
            assertTrue(testModel.getActive().equals(BURGER));
            assertFalse(testModel.getActive().equals(MAGGIE));
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
        public void setActiveRecipe(Recipe recipe) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public ObservableList<Recipe> getActiveRecipe() {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void updateFilteredRecipeList(Predicate<Recipe> predicate) {
            throw new AssertionError("Should not be used!");
        }
    }

    private class RecipeModelStubNoRecipe extends RecipeModelStub {
        private RecipeBook recipeBook = new RecipeBook();
        private Recipe active;

        @Override
        public ObservableList<Recipe> getFilteredRecipeList() {
            return recipeBook.getRecipeList();
        }

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

        @Override
        public void setActiveRecipe(Recipe recipe) {
            this.active = recipe;
        }

        public Recipe getActive() {
            return this.active;
        }
    }

    private class RecipeModelStubWithRecipe extends RecipeModelStubNoRecipe {
        public RecipeModelStubWithRecipe() {
            super.recipeBook.addRecipe(BURGER);
        }
    }


}
