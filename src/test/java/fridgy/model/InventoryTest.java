package fridgy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static fridgy.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static fridgy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static fridgy.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.exceptions.DuplicateIngredientException;
import fridgy.testutil.Assert;
import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.TypicalIngredients;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InventoryTest {

    private final Inventory addressBook = new Inventory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getIngredientList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyInventory_replacesData() {
        Inventory newData = TypicalIngredients.getTypicalInventory();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateIngredients_throwsDuplicateIngredientException() {
        // Two ingredients with the same identity fields
        Ingredient editedAlice = new IngredientBuilder(TypicalIngredients.ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Ingredient> newIngredients = Arrays.asList(TypicalIngredients.ALICE, editedAlice);
        InventoryStub newData = new InventoryStub(newIngredients);

        Assert.assertThrows(DuplicateIngredientException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasIngredient_nullIngredient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> addressBook.hasIngredient(null));
    }

    @Test
    public void hasIngredient_ingredientNotInInventory_returnsFalse() {
        assertFalse(addressBook.hasIngredient(TypicalIngredients.ALICE));
    }

    @Test
    public void hasIngredient_ingredientInInventory_returnsTrue() {
        addressBook.addIngredient(TypicalIngredients.ALICE);
        assertTrue(addressBook.hasIngredient(TypicalIngredients.ALICE));
    }

    @Test
    public void hasIngredient_ingredientWithSameIdentityFieldsInInventory_returnsTrue() {
        addressBook.addIngredient(TypicalIngredients.ALICE);
        Ingredient editedAlice = new IngredientBuilder(TypicalIngredients.ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasIngredient(editedAlice));
    }

    @Test
    public void getIngredientList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> addressBook.getIngredientList().remove(0));
    }

    /**
     * A stub ReadOnlyInventory whose ingredients list can violate interface constraints.
     */
    private static class InventoryStub implements ReadOnlyInventory {
        private final ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();

        InventoryStub(Collection<Ingredient> ingredients) {
            this.ingredients.setAll(ingredients);
        }

        @Override
        public ObservableList<Ingredient> getIngredientList() {
            return ingredients;
        }
    }

}
