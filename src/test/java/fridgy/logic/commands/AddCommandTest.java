package fridgy.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static fridgy.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import fridgy.commons.core.GuiSettings;
import fridgy.logic.commands.exceptions.CommandException;
import fridgy.model.AddressBook;
import fridgy.model.Model;
import fridgy.model.ReadOnlyAddressBook;
import fridgy.model.ReadOnlyUserPrefs;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.Ingredient;
import fridgy.testutil.Assert;
import fridgy.testutil.IngredientBuilder;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

public class AddCommandTest {

    @Test
    public void constructor_nullIngredient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_IngredientAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingIngredientAdded modelStub = new ModelStubAcceptingIngredientAdded();
        Ingredient validIngredient = new IngredientBuilder().build();

        CommandResult commandResult = new AddCommand(validIngredient).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validIngredient), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validIngredient), modelStub.IngredientsAdded);
    }

    @Test
    public void execute_duplicateIngredient_throwsCommandException() {
        Ingredient validIngredient = new IngredientBuilder().build();
        AddCommand addCommand = new AddCommand(validIngredient);
        ModelStub modelStub = new ModelStubWithIngredient(validIngredient);

        Assert.assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_INGREDIENT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Ingredient alice = new IngredientBuilder().withName("Alice").build();
        Ingredient bob = new IngredientBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different Ingredient -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addIngredient(Ingredient ingredient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasIngredient(Ingredient ingredient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteIngredient(Ingredient target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIngredient(Ingredient target, Ingredient editedIngredient) {
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
    }

    /**
     * A Model stub that contains a single Ingredient.
     */
    private class ModelStubWithIngredient extends ModelStub {
        private final Ingredient Ingredient;

        ModelStubWithIngredient(Ingredient Ingredient) {
            requireNonNull(Ingredient);
            this.Ingredient = Ingredient;
        }

        @Override
        public boolean hasIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            return this.Ingredient.isSameIngredient(ingredient);
        }
    }

    /**
     * A Model stub that always accept the Ingredient being added.
     */
    private class ModelStubAcceptingIngredientAdded extends ModelStub {
        final ArrayList<Ingredient> IngredientsAdded = new ArrayList<>();

        @Override
        public boolean hasIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            return IngredientsAdded.stream().anyMatch(ingredient::isSameIngredient);
        }

        @Override
        public void addIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            IngredientsAdded.add(ingredient);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
