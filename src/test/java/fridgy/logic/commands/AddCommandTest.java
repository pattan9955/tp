package fridgy.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.GuiSettings;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.IngredientModel;
import fridgy.model.Inventory;
import fridgy.model.ReadOnlyUserPrefs;
import fridgy.model.base.ReadOnlyDatabase;
import fridgy.model.ingredient.Ingredient;
import fridgy.testutil.Assert;
import fridgy.testutil.IngredientBuilder;
import javafx.collections.ObservableList;


public class AddCommandTest {

    @Test
    public void constructor_nullIngredient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_ingredientAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingIngredientAdded modelStub = new ModelStubAcceptingIngredientAdded();
        Ingredient validIngredient = new IngredientBuilder().build();
        CommandResult commandResult = new AddCommand(validIngredient).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validIngredient), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validIngredient), modelStub.ingredientsAdded);
    }

    @Test
    public void execute_duplicateIngredient_throwsCommandException() {
        Ingredient validIngredient = new IngredientBuilder().build();
        AddCommand addCommand = new AddCommand(validIngredient);
        ModelStub modelStub = new ModelStubWithIngredient(validIngredient);

        Assert.assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_INGREDIENT, () ->
                addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Ingredient almond = new IngredientBuilder().withName("Almond").build();
        Ingredient basil = new IngredientBuilder().withName("Basil").build();
        AddCommand addAlmondCommand = new AddCommand(almond);
        AddCommand addBasilCommand = new AddCommand(basil);

        // same object -> returns true
        assertTrue(addAlmondCommand.equals(addAlmondCommand));

        // same values -> returns true
        AddCommand addAlmondCommandCopy = new AddCommand(almond);
        assertTrue(addAlmondCommand.equals(addAlmondCommandCopy));

        // different types -> returns false
        assertFalse(addAlmondCommand.equals(1));

        // null -> returns false
        assertFalse(addAlmondCommand.equals(null));

        // different Ingredient -> returns false
        assertFalse(addAlmondCommand.equals(addBasilCommand));

    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements IngredientModel {
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
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInventoryFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void add(Ingredient ingredient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortIngredient(Comparator<Ingredient> comparator) {}

        @Override
        public void setInventory(ReadOnlyDatabase<Ingredient> newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyDatabase<Ingredient> getInventory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean has(Ingredient ingredient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void delete(Ingredient target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void set(Ingredient target, Ingredient editedIngredient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Ingredient> getFilteredIngredientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredIngredientList(Predicate<Ingredient> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setActiveIngredient(Ingredient activeIngredient) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single ingredient.
     */
    private class ModelStubWithIngredient extends ModelStub {
        private final Ingredient ingredient;

        ModelStubWithIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            this.ingredient = ingredient;
        }

        @Override
        public boolean has(Ingredient ingredient) {
            requireNonNull(ingredient);
            return this.ingredient.isSameIngredient(ingredient);
        }
    }

    /**
     * A Model stub that always accept the ingredient being added.
     */
    private class ModelStubAcceptingIngredientAdded extends ModelStub {
        final ArrayList<Ingredient> ingredientsAdded = new ArrayList<>();

        @Override
        public boolean has(Ingredient ingredient) {
            requireNonNull(ingredient);
            return ingredientsAdded.stream().anyMatch(ingredient::isSameIngredient);
        }

        @Override
        public void add(Ingredient ingredient) {
            requireNonNull(ingredient);
            ingredientsAdded.add(ingredient);
        }

        @Override
        public ReadOnlyDatabase<Ingredient> getInventory() {
            return new Inventory();
        }
    }

}
