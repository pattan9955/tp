package fridgy.model.ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static fridgy.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static fridgy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static fridgy.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.UniqueIngredientList;
import fridgy.model.ingredient.exceptions.DuplicateIngredientException;
import fridgy.model.ingredient.exceptions.IngredientNotFoundException;
import fridgy.testutil.Assert;
import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.TypicalIngredients;
import org.junit.jupiter.api.Test;

public class UniqueRecipeIngredientListTest {

    private final UniqueIngredientList uniqueIngredientList = new UniqueIngredientList();

    @Test
    public void contains_nullIngredient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueIngredientList.contains(null));
    }

    @Test
    public void contains_ingredientNotInList_returnsFalse() {
        assertFalse(uniqueIngredientList.contains(TypicalIngredients.ALICE));
    }

    @Test
    public void contains_ingredientInList_returnsTrue() {
        uniqueIngredientList.add(TypicalIngredients.ALICE);
        assertTrue(uniqueIngredientList.contains(TypicalIngredients.ALICE));
    }

    @Test
    public void contains_ingredientWithSameIdentityFieldsInList_returnsTrue() {
        uniqueIngredientList.add(TypicalIngredients.ALICE);
        Ingredient editedAlice = new IngredientBuilder(TypicalIngredients.ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueIngredientList.contains(editedAlice));
    }

    @Test
    public void add_nullIngredient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueIngredientList.add(null));
    }

    @Test
    public void add_duplicateIngredient_throwsDuplicateIngredientException() {
        uniqueIngredientList.add(TypicalIngredients.ALICE);
        Assert.assertThrows(DuplicateIngredientException.class, () -> uniqueIngredientList.add(TypicalIngredients.ALICE));
    }

    @Test
    public void setIngredient_nullTargetIngredient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueIngredientList.setIngredient(null, TypicalIngredients.ALICE));
    }

    @Test
    public void setIngredient_nullEditedIngredient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueIngredientList.setIngredient(TypicalIngredients.ALICE, null));
    }

    @Test
    public void setIngredient_targetIngredientNotInList_throwsIngredientNotFoundException() {
        Assert.assertThrows(IngredientNotFoundException.class, () -> uniqueIngredientList.setIngredient(TypicalIngredients.ALICE, TypicalIngredients.ALICE));
    }

    @Test
    public void setIngredient_editedIngredientIsSameIngredient_success() {
        uniqueIngredientList.add(TypicalIngredients.ALICE);
        uniqueIngredientList.setIngredient(TypicalIngredients.ALICE, TypicalIngredients.ALICE);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(TypicalIngredients.ALICE);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredient_editedIngredientHasSameIdentity_success() {
        uniqueIngredientList.add(TypicalIngredients.ALICE);
        Ingredient editedAlice = new IngredientBuilder(TypicalIngredients.ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueIngredientList.setIngredient(TypicalIngredients.ALICE, editedAlice);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(editedAlice);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredient_editedIngredientHasDifferentIdentity_success() {
        uniqueIngredientList.add(TypicalIngredients.ALICE);
        uniqueIngredientList.setIngredient(TypicalIngredients.ALICE, TypicalIngredients.BOB);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(TypicalIngredients.BOB);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredient_editedIngredientHasNonUniqueIdentity_throwsDuplicateIngredientException() {
        uniqueIngredientList.add(TypicalIngredients.ALICE);
        uniqueIngredientList.add(TypicalIngredients.BOB);
        Assert.assertThrows(DuplicateIngredientException.class, () -> uniqueIngredientList.setIngredient(TypicalIngredients.ALICE, TypicalIngredients.BOB));
    }

    @Test
    public void remove_nullIngredient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueIngredientList.remove(null));
    }

    @Test
    public void remove_ingredientDoesNotExist_throwsIngredientNotFoundException() {
        Assert.assertThrows(IngredientNotFoundException.class, () -> uniqueIngredientList.remove(TypicalIngredients.ALICE));
    }

    @Test
    public void remove_existingIngredient_removesIngredient() {
        uniqueIngredientList.add(TypicalIngredients.ALICE);
        uniqueIngredientList.remove(TypicalIngredients.ALICE);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredients_nullUniqueIngredientList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueIngredientList.setIngredients((UniqueIngredientList) null));
    }

    @Test
    public void setIngredients_uniqueIngredientList_replacesOwnListWithProvidedUniqueIngredientList() {
        uniqueIngredientList.add(TypicalIngredients.ALICE);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(TypicalIngredients.BOB);
        uniqueIngredientList.setIngredients(expectedUniqueIngredientList);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredients_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueIngredientList.setIngredients((List<Ingredient>) null));
    }

    @Test
    public void setIngredients_list_replacesOwnListWithProvidedList() {
        uniqueIngredientList.add(TypicalIngredients.ALICE);
        List<Ingredient> ingredientList = Collections.singletonList(TypicalIngredients.BOB);
        uniqueIngredientList.setIngredients(ingredientList);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(TypicalIngredients.BOB);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredients_listWithDuplicateIngredients_throwsDuplicateIngredientException() {
        List<Ingredient> listWithDuplicateIngredients = Arrays.asList(TypicalIngredients.ALICE, TypicalIngredients.ALICE);
        Assert.assertThrows(DuplicateIngredientException.class, () -> uniqueIngredientList.setIngredients(listWithDuplicateIngredients));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueIngredientList.asUnmodifiableObservableList().remove(0));
    }
}
