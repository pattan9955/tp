package fridgy.model;

import static fridgy.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import fridgy.model.base.ReadOnlyDatabase;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.exceptions.DuplicateIngredientException;
import fridgy.testutil.Assert;
import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.TypicalIngredients;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InventoryTest {

    private final Inventory addressBook = new Inventory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> addressBook.resetDatabase(null));
    }

    @Test
    public void resetData_withValidReadOnlyDatabase_replacesData() {
        Inventory newData = TypicalIngredients.getTypicalInventory();
        addressBook.resetDatabase(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateIngredients_throwsDuplicateIngredientException() {
        // Two ingredients with the same identity fields
        Ingredient editedAlmond = new IngredientBuilder(TypicalIngredients.APPLE)
                .withDescription(VALID_DESCRIPTION_BASIL).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Ingredient> newIngredients = Arrays.asList(TypicalIngredients.APPLE, editedAlmond);
        InventoryStub newData = new InventoryStub(newIngredients);

        Assert.assertThrows(DuplicateIngredientException.class, () -> addressBook.resetDatabase(newData));
    }

    @Test
    public void hasIngredient_nullIngredient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> addressBook.has(null));
    }

    @Test
    public void hasIngredient_ingredientNotInInventory_returnsFalse() {
        assertFalse(addressBook.has(TypicalIngredients.APPLE));
    }

    @Test
    public void hasIngredient_ingredientInInventory_returnsTrue() {
        addressBook.add(TypicalIngredients.APPLE);
        assertTrue(addressBook.has(TypicalIngredients.APPLE));
    }

    @Test
    public void hasIngredient_ingredientWithSameIdentityFieldsInInventory_returnsTrue() {
        addressBook.add(TypicalIngredients.APPLE);
        Ingredient editedAlmond = new IngredientBuilder(TypicalIngredients.APPLE)
                .withDescription(VALID_DESCRIPTION_BASIL).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.has(editedAlmond));
    }

    @Test
    public void getIngredientList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> addressBook.getList().remove(0));
    }

    /**
     * A stub ReadOnlyDatabase whose ingredients list can violate interface constraints.
     */
    private static class InventoryStub implements ReadOnlyDatabase<Ingredient> {
        private final ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();

        InventoryStub(Collection<Ingredient> ingredients) {
            this.ingredients.setAll(ingredients);
        }

        @Override
        public ObservableList<Ingredient> getList() {
            return ingredients;
        }
    }

}
