package fridgy.model.ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static fridgy.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static fridgy.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fridgy.model.ingredient.exceptions.DuplicateIngredientException;
import fridgy.model.ingredient.exceptions.IngredientNotFoundException;
import fridgy.testutil.Assert;
import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.TypicalIngredients;
import org.junit.jupiter.api.Test;

public class UniqueIngredientListTest {

    private final UniqueIngredientList uniqueIngredientList = new UniqueIngredientList();

    @Test
    public void contains_nullIngredient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueIngredientList.contains(null));
    }

    @Test
    public void contains_ingredientNotInList_returnsFalse() {
        assertFalse(uniqueIngredientList.contains(TypicalIngredients.APPLE));
    }

    @Test
    public void contains_ingredientInList_returnsTrue() {
        uniqueIngredientList.add(TypicalIngredients.APPLE);
        assertTrue(uniqueIngredientList.contains(TypicalIngredients.APPLE));
    }

    @Test
    public void contains_ingredientWithSameIdentityFieldsInList_returnsTrue() {
        uniqueIngredientList.add(TypicalIngredients.APPLE);
        Ingredient editedAlice = new IngredientBuilder(TypicalIngredients.APPLE).withDescription(VALID_DESCRIPTION_BASIL).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueIngredientList.contains(editedAlice));
    }

    @Test
    public void add_nullIngredient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueIngredientList.add(null));
    }

    @Test
    public void add_duplicateIngredient_throwsDuplicateIngredientException() {
        uniqueIngredientList.add(TypicalIngredients.APPLE);
        Assert.assertThrows(DuplicateIngredientException.class, () -> uniqueIngredientList.add(TypicalIngredients.APPLE));
    }

    @Test
    public void setIngredient_nullTargetIngredient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueIngredientList.setIngredient(null, TypicalIngredients.APPLE));
    }

    @Test
    public void setIngredient_nullEditedIngredient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueIngredientList.setIngredient(TypicalIngredients.APPLE, null));
    }

    @Test
    public void setIngredient_targetIngredientNotInList_throwsIngredientNotFoundException() {
        Assert.assertThrows(IngredientNotFoundException.class, () -> uniqueIngredientList.setIngredient(TypicalIngredients.APPLE, TypicalIngredients.APPLE));
    }

    @Test
    public void setIngredient_editedIngredientIsSameIngredient_success() {
        uniqueIngredientList.add(TypicalIngredients.APPLE);
        uniqueIngredientList.setIngredient(TypicalIngredients.APPLE, TypicalIngredients.APPLE);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(TypicalIngredients.APPLE);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredient_editedIngredientHasSameIdentity_success() {
        uniqueIngredientList.add(TypicalIngredients.APPLE);
        Ingredient editedAlice = new IngredientBuilder(TypicalIngredients.APPLE).withDescription(VALID_DESCRIPTION_BASIL).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueIngredientList.setIngredient(TypicalIngredients.APPLE, editedAlice);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(editedAlice);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredient_editedIngredientHasDifferentIdentity_success() {
        uniqueIngredientList.add(TypicalIngredients.APPLE);
        uniqueIngredientList.setIngredient(TypicalIngredients.APPLE, TypicalIngredients.BASIL);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(TypicalIngredients.BASIL);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredient_editedIngredientHasNonUniqueIdentity_throwsDuplicateIngredientException() {
        uniqueIngredientList.add(TypicalIngredients.APPLE);
        uniqueIngredientList.add(TypicalIngredients.BASIL);
        Assert.assertThrows(DuplicateIngredientException.class, () -> uniqueIngredientList.setIngredient(TypicalIngredients.APPLE, TypicalIngredients.BASIL));
    }

    @Test
    public void remove_nullIngredient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueIngredientList.remove(null));
    }

    @Test
    public void remove_ingredientDoesNotExist_throwsIngredientNotFoundException() {
        Assert.assertThrows(IngredientNotFoundException.class, () -> uniqueIngredientList.remove(TypicalIngredients.APPLE));
    }

    @Test
    public void remove_existingIngredient_removesIngredient() {
        uniqueIngredientList.add(TypicalIngredients.APPLE);
        uniqueIngredientList.remove(TypicalIngredients.APPLE);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredients_nullUniqueIngredientList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueIngredientList.setIngredients((UniqueIngredientList) null));
    }

    @Test
    public void setIngredients_uniqueIngredientList_replacesOwnListWithProvidedUniqueIngredientList() {
        uniqueIngredientList.add(TypicalIngredients.APPLE);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(TypicalIngredients.BASIL);
        uniqueIngredientList.setIngredients(expectedUniqueIngredientList);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredients_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueIngredientList.setIngredients((List<Ingredient>) null));
    }

    @Test
    public void setIngredients_list_replacesOwnListWithProvidedList() {
        uniqueIngredientList.add(TypicalIngredients.APPLE);
        List<Ingredient> ingredientList = Collections.singletonList(TypicalIngredients.BASIL);
        uniqueIngredientList.setIngredients(ingredientList);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(TypicalIngredients.BASIL);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredients_listWithDuplicateIngredients_throwsDuplicateIngredientException() {
        List<Ingredient> listWithDuplicateIngredients = Arrays.asList(TypicalIngredients.APPLE, TypicalIngredients.APPLE);
        Assert.assertThrows(DuplicateIngredientException.class, () -> uniqueIngredientList.setIngredients(listWithDuplicateIngredients));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueIngredientList.asUnmodifiableObservableList().remove(0));
    }
}
