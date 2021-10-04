package fridgy.model.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static fridgy.testutil.Assert.assertThrows;
import static fridgy.testutil.TypicalRecipes.BURGER;
import static fridgy.testutil.TypicalRecipes.MAGGIE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import fridgy.model.recipe.exceptions.DuplicateRecipeException;
import fridgy.model.recipe.exceptions.RecipeNotFoundException;
import fridgy.testutil.RecipeBuilder;


public class UniqueRecipeListTest {

    private final UniqueRecipeList uniqueRecipeList = new UniqueRecipeList();

    @Test
    public void contains_nullRecipe_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecipeList.contains(null));
    }

    @Test
    public void contains_recipeNotInList_returnsFalse() {
        assertFalse(uniqueRecipeList.contains(BURGER));
    }

    @Test
    public void contains_recipeInList_returnsTrue() {
        uniqueRecipeList.add(BURGER);
        assertTrue(uniqueRecipeList.contains(BURGER));
    }

    @Test
    public void contains_recipeWithSameIdentityFieldsInList_returnsTrue() {
        uniqueRecipeList.add(BURGER);
        Recipe editedBurger = new RecipeBuilder(BURGER)
                .withIngredients(Arrays.asList("A"))
                .withSteps(Arrays.asList("B"))
                .build();
        assertTrue(uniqueRecipeList.contains(editedBurger));
    }

    @Test
    public void add_nullRecipe_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecipeList.add(null));
    }

    @Test
    public void add_duplicateRecipe_throwsDuplicateRecipeException() {
        uniqueRecipeList.add(BURGER);
        assertThrows(DuplicateRecipeException.class, () -> uniqueRecipeList.add(BURGER));
    }

    @Test
    public void setRecipe_nullTargetRecipe_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecipeList.setRecipe(null, BURGER));
    }

    @Test
    public void setRecipe_nullEditedRecipe_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecipeList.setRecipe(BURGER, null));
    }

    @Test
    public void setRecipe_targetRecipeNotInList_throwsRecipeNotFoundException() {
        assertThrows(RecipeNotFoundException.class, () -> uniqueRecipeList.setRecipe(BURGER, BURGER));
    }

    @Test
    public void setRecipe_editedRecipeIsSameRecipe_success() {
        uniqueRecipeList.add(BURGER);
        uniqueRecipeList.setRecipe(BURGER, BURGER);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(BURGER);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipe_editedRecipeHasSameIdentity_success() {
        uniqueRecipeList.add(BURGER);
        Recipe editedBurger = new RecipeBuilder(BURGER)
                .withIngredients(Arrays.asList("A"))
                .withSteps(Arrays.asList("B"))
                .build();
        uniqueRecipeList.setRecipe(BURGER, editedBurger);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(editedBurger);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipe_editedRecipeHasDifferentIdentity_success() {
        uniqueRecipeList.add(BURGER);
        uniqueRecipeList.setRecipe(BURGER, MAGGIE);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(MAGGIE);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipe_editedRecipeHasNonUniqueIdentity_throwsDuplicateRecipeException() {
        uniqueRecipeList.add(BURGER);
        uniqueRecipeList.add(MAGGIE);
        assertThrows(DuplicateRecipeException.class, () -> uniqueRecipeList.setRecipe(BURGER, MAGGIE));
    }

    @Test
    public void remove_nullRecipe_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecipeList.remove(null));
    }

    @Test
    public void remove_recipeDoesNotExist_throwsRecipeNotFoundException() {
        assertThrows(RecipeNotFoundException.class, () -> uniqueRecipeList.remove(BURGER));
    }

    @Test
    public void remove_existingRecipe_removesRecipe() {
        uniqueRecipeList.add(BURGER);
        uniqueRecipeList.remove(BURGER);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipes_nullUniqueRecipeList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecipeList.setRecipes((UniqueRecipeList) null));
    }

    @Test
    public void setRecipes_uniqueRecipeList_replacesOwnListWithProvidedUniqueRecipeList() {
        uniqueRecipeList.add(BURGER);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(MAGGIE);
        uniqueRecipeList.setRecipes(expectedUniqueRecipeList);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipes_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecipeList.setRecipes((List<Recipe>) null));
    }

    @Test
    public void setRecipes_list_replacesOwnListWithProvidedList() {
        uniqueRecipeList.add(BURGER);
        List<Recipe> recipeList = Collections.singletonList(MAGGIE);
        uniqueRecipeList.setRecipes(recipeList);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(MAGGIE);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipes_listWithDuplicateRecipes_throwsDuplicateRecipeException() {
        List<Recipe> listWithDuplicateRecipes = Arrays.asList(BURGER, BURGER);
        assertThrows(DuplicateRecipeException.class, () -> uniqueRecipeList.setRecipes(listWithDuplicateRecipes));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueRecipeList.asUnmodifiableObservableList().remove(0));
    }
}
