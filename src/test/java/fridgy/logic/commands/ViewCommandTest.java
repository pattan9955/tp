package fridgy.logic.commands;

import static fridgy.testutil.TypicalIngredients.APPLE;
import static fridgy.testutil.TypicalIngredients.BANANA;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fridgy.commons.core.GuiSettings;
import fridgy.commons.core.index.Index;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.logic.commands.ingredient.ViewCommand;
import fridgy.model.IngredientModel;
import fridgy.model.Inventory;
import fridgy.model.ReadOnlyUserPrefs;
import fridgy.model.base.ReadOnlyDatabase;
import fridgy.model.ingredient.Ingredient;
import javafx.collections.ObservableList;

public class ViewCommandTest {
    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ViewCommand(null));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        ViewCommand testCommand = new ViewCommand(Index.fromZeroBased(4));
        assertTrue(testCommand.equals(testCommand));
    }

    @Test
    public void equals_differentCommand_returnsFalse() {
        ViewCommand testCommand = new ViewCommand(Index.fromZeroBased(3));
        ViewCommand targetCommand = new ViewCommand(Index.fromZeroBased(4));
        assertFalse(testCommand.equals(targetCommand));
    }

    @Test
    public void equals_equalCommand_returnsTrue() {
        ViewCommand testCommand = new ViewCommand(Index.fromZeroBased(3));
        ViewCommand targetCommand = new ViewCommand(Index.fromZeroBased(3));
        ViewCommand targetCommand2 = new ViewCommand(Index.fromOneBased(4));
        assertTrue(testCommand.equals(targetCommand));
        assertTrue(testCommand.equals(targetCommand2));
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        ViewCommand testCommand = new ViewCommand(Index.fromZeroBased(2));
        Object targetObj = new String("2");
        assertFalse(testCommand.equals(targetObj));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        ViewCommand testCommand = new ViewCommand(Index.fromZeroBased(1));
        assertThrows(NullPointerException.class, () -> testCommand.execute(null));
    }

    @Test
    public void execute_targetIndexLargerThanList_throwsCommandException() {
        ViewCommand testCommand = new ViewCommand(Index.fromZeroBased(3));
        ViewCommandTest.IngredientModelStubWithIngredient testModel =
                new ViewCommandTest.IngredientModelStubWithIngredient();
        assertThrows(CommandException.class, () -> testCommand.execute(testModel));
    }

    @Test
    public void execute_validTargetIndex_changesSpecifiedActiveRecipe() {
        ViewCommand testCommand = new ViewCommand(Index.fromZeroBased(0));
        ViewCommandTest.IngredientModelStubWithIngredient testModel =
                new IngredientModelStubWithIngredient();
        testModel.add(BANANA);
        CommandResult expected = new CommandResult(
                String.format(ViewCommand.MESSAGE_SUCCESS, APPLE));
        try {
            CommandResult result = testCommand.execute(testModel);
            assertTrue(result.equals(expected));
            assertTrue(testModel.getActive().equals(APPLE));
            assertFalse(testModel.getActive().equals(BANANA));
        } catch (CommandException e) {
            Assertions.fail("CommandException thrown!");
        }
    }

    private class IngredientModelStub implements IngredientModel {

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getInventoryFilePath() {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void setInventoryFilePath(Path inventoryFilePath) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void setInventory(ReadOnlyDatabase<Ingredient> inventory) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public ReadOnlyDatabase<Ingredient> getInventory() {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public boolean has(Ingredient ingredient) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void delete(Ingredient target) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void add(Ingredient ingredient) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void sortIngredient(Comparator<Ingredient> comparator) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void set(Ingredient target, Ingredient editedIngredient) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public ObservableList<Ingredient> getFilteredIngredientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setActiveIngredient(Ingredient ingredient) {
            throw new AssertionError("Should not be used!");
        }

        @Override
        public void updateFilteredIngredientList(Predicate<Ingredient> predicate) {
            throw new AssertionError("Should not be used!");
        }
    }

    private class IngredientModelStubNoIngredient extends IngredientModelStub {
        private Inventory inventory = new Inventory();
        private Ingredient active;

        @Override
        public ObservableList<Ingredient> getFilteredIngredientList() {
            return inventory.getList();
        }

        @Override
        public boolean has(Ingredient target) {
            return inventory.has(target);
        }

        @Override
        public void delete(Ingredient target) {
            this.inventory.remove(target);
        }

        @Override
        public void add(Ingredient ingredient) {
            this.inventory.add(ingredient);
        }

        @Override
        public void set(Ingredient ingredient, Ingredient editedIngredient) {
            this.inventory.set(ingredient, editedIngredient);
        }

        @Override
        public void setActiveIngredient(Ingredient ingredient) {
            this.active = ingredient;
        }

        public Ingredient getActive() {
            return this.active;
        }
    }

    private class IngredientModelStubWithIngredient extends IngredientModelStubNoIngredient {
        public IngredientModelStubWithIngredient() {
            super.inventory.add(APPLE);
        }
    }
}
