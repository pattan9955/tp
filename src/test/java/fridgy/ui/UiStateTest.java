package fridgy.ui;


import static fridgy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import fridgy.model.ingredient.Ingredient;
import fridgy.model.recipe.Recipe;
import fridgy.testutil.TypicalIngredients;
import fridgy.testutil.TypicalRecipes;

class UiStateTest {

    @Test
    public void constructor_nullParam_throwsException() {
        assertThrows(NullPointerException.class, () -> new UiState(null));
    }

    @Test
    public void constructor_validParam_success() {
        assertDoesNotThrow(() -> new UiState(new ObserverStub()));
    }

    @Test
    public void setActive_validIngredient_success() {
        assertDoesNotThrow(() -> new UiState(new ObserverStub()).setActive(
            TypicalIngredients.ALMOND
        ));
    }

    @Test
    public void setActive_validRecipe_success() {
        assertDoesNotThrow(() -> new UiState(new ObserverStub()).setActive(
            TypicalRecipes.BURGER
        ));
    }

    @Test
    public void setActive_nullRecipe_throwsException() {
        assertThrows(NullPointerException.class, () -> new UiState(new ObserverStub()).setActive((Recipe) null));
    }

    @Test
    public void setActive_nullIngredient_throwsException() {
        assertThrows(NullPointerException.class, () -> new UiState(new ObserverStub()).setActive((Ingredient) null));
    }

    @Test
    public void setActive_multiSwitch_success() {
        UiState state1 = new UiState(new ObserverStub());
        UiState state2 = new UiState(new ObserverStub());

        state1.setActive(TypicalRecipes.BURGER);
        state2.setActive(TypicalIngredients.ALMOND);
        assertNotEquals(state1, state2);

        state1.setActive(TypicalIngredients.ALMOND);
        assertEquals(state1, state2);

        state2.setActive(TypicalRecipes.BURGER);
        assertNotEquals(state1, state2);
    }

    @Test
    public void modify_sameActive_success() {
        UiState state1 = new UiState(new ObserverStub());
        UiState state2 = new UiState(new ObserverStub());
        state1.setActive(TypicalIngredients.ALMOND);
        state2.setActive(TypicalIngredients.APPLE);

        state1.modify(TypicalIngredients.ALMOND, TypicalIngredients.APPLE);
        assertEquals(state1, state2);


        state1.setActive(TypicalRecipes.RICE);
        state2.setActive(TypicalRecipes.BURGER);

        state1.modify(TypicalRecipes.RICE, TypicalRecipes.BURGER);
        assertEquals(state1, state2);
    }

    @Test
    public void modify_differentActive_doNothing() {
        UiState state1 = new UiState(new ObserverStub());
        UiState state2 = new UiState(new ObserverStub());
        state1.setActive(TypicalIngredients.ALMOND);
        state2.setActive(TypicalIngredients.APPLE);

        state1.modify(TypicalIngredients.BANANA, TypicalIngredients.APPLE);
        assertNotEquals(state1, state2);

        state1.setActive(TypicalRecipes.RICE);
        state2.setActive(TypicalRecipes.BURGER);

        state1.modify(TypicalRecipes.MAGGIE, TypicalRecipes.BURGER);
        assertNotEquals(state1, state2);
    }

    @Test
    public void delete_sameActive_success() {
        UiState state1 = new UiState(new ObserverStub());
        UiState state2 = new UiState(new ObserverStub());

        state1.setActive(TypicalIngredients.ALMOND);
        state1.delete(TypicalIngredients.ALMOND);
        assertEquals(state1, state2);

        state1.setActive(TypicalRecipes.RICE);
        state1.delete(TypicalRecipes.RICE);
        assertEquals(state1, state2);
    }

    @Test
    public void delete_differentActive_doNothing() {
        UiState state1 = new UiState(new ObserverStub());
        UiState state2 = new UiState(new ObserverStub());

        state1.setActive(TypicalIngredients.ALMOND);
        state1.delete(TypicalIngredients.BANANA);
        assertNotEquals(state1, state2);

        state1.setActive(TypicalRecipes.RICE);
        state1.delete(TypicalRecipes.MAGGIE);
        assertNotEquals(state1, state2);
    }

    class ObserverStub implements Observer {

        @Override
        public void update(Ingredient newItem) {

        }

        @Override
        public void update(Recipe newItem) {

        }

        @Override
        public void update(TabEnum tab) {

        }

        @Override
        public void clearWindow() {}

        @Override
        public boolean equals(Object other) {
            return other instanceof ObserverStub;
        }
    }
}
