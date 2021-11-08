package fridgy.model;

import static fridgy.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_TAG_VEGETABLE;
import static fridgy.testutil.TypicalIngredients.ALMOND;
import static fridgy.testutil.TypicalIngredients.APPLE;
import static fridgy.testutil.TypicalIngredients.CHICKEN;
import static fridgy.testutil.TypicalIngredients.FLOUR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import fridgy.model.base.Database;
import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.ingredient.ExpiryDate;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Quantity;
import fridgy.model.ingredient.exceptions.DuplicateIngredientException;
import fridgy.testutil.Assert;
import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.TypicalIngredients;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InventoryTest {

    private final Inventory ingrInventory = new Inventory();
    private List<Ingredient> inventoryIngrList = Arrays.asList(CHICKEN, FLOUR, APPLE, ALMOND);
    // this ingredient list will be sorted to: CHICKEN, FLOUR, ALMOND, APPLE

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), ingrInventory.getList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ingrInventory.resetDatabase(null));
    }

    @Test
    public void resetData_withValidReadOnlyDatabase_replacesData() {
        Inventory newData = TypicalIngredients.getTypicalInventory();
        ingrInventory.resetDatabase(newData);
        assertEquals(newData, ingrInventory);
    }

    @Test
    public void resetData_withDuplicateIngredients_throwsDuplicateIngredientException() {
        // Two ingredients with the same identity fields
        Ingredient editedAlmond = new IngredientBuilder(TypicalIngredients.APPLE)
                .withDescription(VALID_DESCRIPTION_BASIL).withTags(VALID_TAG_VEGETABLE)
                .build();
        List<Ingredient> newIngredients = Arrays.asList(TypicalIngredients.APPLE, editedAlmond);
        InventoryStub newData = new InventoryStub(newIngredients);

        Assert.assertThrows(DuplicateIngredientException.class, () -> ingrInventory.resetDatabase(newData));
    }

    @Test
    public void hasIngredient_nullIngredient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ingrInventory.has(null));
    }

    @Test
    public void hasIngredient_ingredientNotInInventory_returnsFalse() {
        assertFalse(ingrInventory.has(TypicalIngredients.APPLE));
    }

    @Test
    public void hasIngredient_ingredientInInventory_returnsTrue() {
        ingrInventory.add(TypicalIngredients.APPLE);
        assertTrue(ingrInventory.has(TypicalIngredients.APPLE));
    }

    @Test
    public void hasIngredient_ingredientWithSameIdentityFieldsInInventory_returnsTrue() {
        ingrInventory.add(TypicalIngredients.APPLE);
        Ingredient editedAlmond = new IngredientBuilder(TypicalIngredients.APPLE)
                .withDescription(VALID_DESCRIPTION_BASIL).withTags(VALID_TAG_VEGETABLE)
                .build();
        assertTrue(ingrInventory.has(editedAlmond));
    }

    @Test
    public void getIngredientList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> ingrInventory.getList().remove(0));
    }

    /**
     * A stub ReadOnlyDatabase whose ingredients list can violate interface constraints.
     */
    private static class InventoryStub extends Database<Ingredient> {
        private final ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();

        InventoryStub(Collection<Ingredient> ingredients) {
            this.ingredients.setAll(ingredients);
        }

        @Override
        public ObservableList<Ingredient> getList() {
            return ingredients;
        }
    }

    //=========== Testing Quantity Deduction =============================================================
    @Test
    public void deductIngredients_matchingInventoryIngr_returnsTrue() {
        ingrInventory.setItems(inventoryIngrList);
        Set<BaseIngredient> friedChickenIngr = new HashSet<>();
        friedChickenIngr.add(new BaseIngredient(new Name("chicken"), CHICKEN.getQuantity()));
        friedChickenIngr.add(new BaseIngredient(new Name("flour"), FLOUR.getQuantity()));
        assertTrue(ingrInventory.deductIngredients(friedChickenIngr));
        // chicken and flour should be used up and removed, only apple and almond will be left
        assertEquals(ALMOND.getQuantity(), ingrInventory.getList().get(0).getQuantity());
        assertEquals(APPLE.getQuantity(), ingrInventory.getList().get(1).getQuantity());
    }

    @Test
    public void deductIngredients_differentCaseNameofIngr_returnsTrue() {
        ingrInventory.setItems(inventoryIngrList);
        Set<BaseIngredient> friedChickenIngr = new HashSet<>();
        // names are case-insensitive
        friedChickenIngr.add(new BaseIngredient(new Name("cHiCKen"), new Quantity("500g")));
        friedChickenIngr.add(new BaseIngredient(new Name("FlOuR"), new Quantity("500g")));
        assertTrue(ingrInventory.deductIngredients(friedChickenIngr));
        // chicken should be left with 2kg - 500g = 1500g
        assertEquals(new Quantity("1500 g"), ingrInventory.getList().get(0).getQuantity());
        assertEquals(ALMOND.getQuantity(), ingrInventory.getList().get(1).getQuantity());
        assertEquals(APPLE.getQuantity(), ingrInventory.getList().get(2).getQuantity());
    }

    @Test
    public void deductIngredients_excessInventoryIngr_returnsTrue() {
        ingrInventory.setItems(inventoryIngrList);
        Set<BaseIngredient> friedChickenIngr = new HashSet<>();
        friedChickenIngr.add(new BaseIngredient(new Name("chicken"), new Quantity("20g")));
        friedChickenIngr.add(new BaseIngredient(new Name("flour"), new Quantity("20g")));
        assertTrue(ingrInventory.deductIngredients(friedChickenIngr));
        // almond, apple, chicken and flour should be left
        // chicken should be left with 2kg - 20g = 1980g
        // flour should be left with 500g - 20g = 480g
        assertEquals(new Quantity("1980 g"), ingrInventory.getList().get(0).getQuantity());
        assertEquals(new Quantity("480 g"), ingrInventory.getList().get(1).getQuantity());
        assertEquals(ALMOND.getQuantity(), ingrInventory.getList().get(2).getQuantity());
        assertEquals(APPLE.getQuantity(), ingrInventory.getList().get(3).getQuantity());
    }

    @Test
    public void deductIngredients_insufficientQuantityofIngr_returnsFalse() {
        ingrInventory.setItems(inventoryIngrList);
        Set<BaseIngredient> friedChickenIngr = new HashSet<>();
        friedChickenIngr.add(new BaseIngredient(new Name("chicken"), new Quantity("300kg")));
        friedChickenIngr.add(new BaseIngredient(new Name("flour"), new Quantity("300kg")));
        assertFalse(ingrInventory.deductIngredients(friedChickenIngr));
        // nothing should be deducted, since there is insufficient quantities in inventory
        assertEquals(CHICKEN.getQuantity(), ingrInventory.getList().get(0).getQuantity());
        assertEquals(FLOUR.getQuantity(), ingrInventory.getList().get(1).getQuantity());
        assertEquals(ALMOND.getQuantity(), ingrInventory.getList().get(2).getQuantity());
        assertEquals(APPLE.getQuantity(), ingrInventory.getList().get(3).getQuantity());
    }

    @Test
    public void deductIngredients_expiredIngr_returnsFalse() {
        ingrInventory.setItems(inventoryIngrList);
        // adding expired items
        ingrInventory.add(new Ingredient(new Name("mango"), new Quantity("5"),
                new HashSet<>(), new ExpiryDate("20-10-1975")));
        ingrInventory.add(new Ingredient(new Name("monga"), new Quantity("5"),
                new HashSet<>(), new ExpiryDate("20-10-1975")));
        Set<BaseIngredient> fruityIngr = new HashSet<>();
        fruityIngr.add(new BaseIngredient(new Name("mango"), new Quantity("1")));
        fruityIngr.add(new BaseIngredient(new Name("monga"), new Quantity("1")));
        assertFalse(ingrInventory.deductIngredients(fruityIngr));
        // deductIngredients does not allow deduction of expired ingredients, so nothing should be deducted
        assertEquals(new Quantity("5"), ingrInventory.getList().get(0).getQuantity());
        assertEquals(new Quantity("5"), ingrInventory.getList().get(1).getQuantity());
        assertEquals(CHICKEN.getQuantity(), ingrInventory.getList().get(2).getQuantity());
        assertEquals(FLOUR.getQuantity(), ingrInventory.getList().get(3).getQuantity());
        assertEquals(ALMOND.getQuantity(), ingrInventory.getList().get(4).getQuantity());
        assertEquals(APPLE.getQuantity(), ingrInventory.getList().get(5).getQuantity());
    }
}
