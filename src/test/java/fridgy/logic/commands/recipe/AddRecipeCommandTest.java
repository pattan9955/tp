package fridgy.logic.commands.recipe;

import static fridgy.logic.commands.recipe.AddRecipeCommand.MESSAGE_SUCCESS;
import static fridgy.testutil.Assert.assertThrows;
import static fridgy.testutil.TypicalRecipes.BURGER;
import static fridgy.testutil.TypicalRecipes.MAGGIE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fridgy.logic.commands.CommandResult;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.RecipeBook;
import fridgy.model.RecipeModel;
import fridgy.model.base.ReadOnlyDatabase;
import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;
import fridgy.testutil.RecipeBuilder;
import javafx.collections.ObservableList;

public class AddRecipeCommandTest {
    @Test
    public void constructor_nullRecipe_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddRecipeCommand(null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        RecipeModel nullModel = null;
        assertThrows(NullPointerException.class, () ->
                new AddRecipeCommand(BURGER).execute(nullModel));
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
        assertThrows(CommandException.class, () ->
                testCommand.execute(testModel));
    }

    @Test
    public void execute_sameRecipeDifferentCase_throwsCommandException() {
        Recipe editedBurger = new RecipeBuilder(BURGER).withName("burger").build();
        AddRecipeCommand testCommand = new AddRecipeCommand(editedBurger);
        RecipeModelStub testModel = new RecipeModelStubWithRecipe();
        assertThrows(CommandException.class, AddRecipeCommand.MESSAGE_DUPLICATE_RECIPE, () ->
                testCommand.execute(testModel));
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
        public void setRecipeBook(ReadOnlyDatabase<Recipe> recipeBook) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public ReadOnlyDatabase<Recipe> getRecipeBook() {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public boolean has(Recipe recipe) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void delete(Recipe target) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void add(Recipe recipe) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void sortRecipe(Comparator<Recipe> comparator) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void set(Recipe target, Recipe editedRecipe) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public ObservableList<Recipe> getFilteredRecipeList() {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public ObservableList<Ingredient> getFilteredIngredientList() {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void setActiveRecipe(Recipe recipe) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void updateFilteredRecipeList(Predicate<Recipe> predicate) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public boolean deductIngredients(Set<BaseIngredient> ingredients) {
            throw new AssertionError("Should not be used!");
        }
    }

    private class RecipeModelStubNoRecipe extends RecipeModelStub {
        private RecipeBook recipeBook = new RecipeBook();

        @Override
        public boolean has(Recipe recipe) {
            return recipeBook.has(recipe);
        }

        @Override
        public void delete(Recipe target) {
            this.recipeBook.remove(target);
        }

        @Override
        public void add(Recipe recipe) {
            this.recipeBook.add(recipe);
        }

        @Override
        public void set(Recipe target, Recipe editedRecipe) {
            this.recipeBook.set(target, editedRecipe);
        }
    }

    private class RecipeModelStubWithRecipe extends RecipeModelStubNoRecipe {
        public RecipeModelStubWithRecipe() {
            super.recipeBook.add(BURGER);
        }
    }
}
