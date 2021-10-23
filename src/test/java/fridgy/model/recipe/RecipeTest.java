package fridgy.model.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fridgy.model.ingredient.BaseIngredient;
import fridgy.testutil.RecipeBuilder;
import fridgy.testutil.TypicalBaseIngredients;

class RecipeTest {

    private static Recipe burger;
    private static Recipe maggie;
    private static final List<BaseIngredient> BURGER_INGREDIENTS = Arrays.asList(
        TypicalBaseIngredients.APPLE,
        TypicalBaseIngredients.BANANA,
        TypicalBaseIngredients.ALMOND
    );
    private static final List<String> BURGER_STEPS = Arrays.asList("Put patty in bread", "Add sauce");
    private static final String BURGER_NAME = "Burger";
    private static final String BURGER_DESCRIPTION = "Healthier choice";
    private static final List<BaseIngredient> MAGGIE_INGREDIENTS = Arrays.asList(TypicalBaseIngredients.INGR1);
    private static final List<String> MAGGIE_STEPS = Arrays.asList("Boil water", "add maggie");
    private static final String MAGGIE_NAME = "Maggie";
    private static final String MAGGIE_DESCRIPTION = "Easy";

    @BeforeAll
    public static void setup() {
        burger = new RecipeBuilder()
                .withName(BURGER_NAME)
                .withIngredients(BURGER_INGREDIENTS)
                .withSteps(BURGER_STEPS)
                .withDescription(BURGER_DESCRIPTION)
                .build();
        maggie = new RecipeBuilder()
                .withName(MAGGIE_NAME)
                .withIngredients(MAGGIE_INGREDIENTS)
                .withSteps(MAGGIE_STEPS)
                .withDescription(MAGGIE_DESCRIPTION)
                .build();
    }

    @Test
    public void isSameRecipe() {
        // same object -> returns true
        assertTrue(burger.isSameRecipe(burger));

        // null -> returns false
        assertFalse(burger.isSameRecipe(null));

        // same name, all other attributes different -> returns true
        Recipe editedBurger = new RecipeBuilder(burger)
                .withName(burger.getName().toString())
                .withSteps(MAGGIE_STEPS)
                .withIngredients(MAGGIE_INGREDIENTS)
                .withDescription(MAGGIE_DESCRIPTION)
                .build();
        assertTrue(burger.isSameRecipe(editedBurger));

        // different name, all other attributes same -> returns false
        editedBurger = new RecipeBuilder(burger).withName(MAGGIE_NAME).build();
        assertFalse(burger.isSameRecipe(editedBurger));

        // name differs in case, all other attributes same -> returns false
        Recipe editedMaggie = new RecipeBuilder(maggie).withName(BURGER_NAME).build();
        assertFalse(maggie.isSameRecipe(editedMaggie));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = MAGGIE_NAME + " ";
        editedMaggie = new RecipeBuilder(maggie).withName(nameWithTrailingSpaces).build();
        assertFalse(maggie.isSameRecipe(editedMaggie));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Recipe burgerCopy = new RecipeBuilder(burger).build();
        assertEquals(burger, burgerCopy);

        // same object -> returns true
        assertEquals(burger, burger);

        // null -> returns false
        assertNotEquals(null, burger);

        // different type -> returns false
        assertNotEquals(5, burger);

        // different ingredient -> returns false
        assertNotEquals(burger, maggie);

        // different name -> returns false
        Recipe editedBurger = new RecipeBuilder(burger).withName(MAGGIE_NAME).build();
        assertNotEquals(burger, editedBurger);

        // different ingredients -> returns false
        editedBurger = new RecipeBuilder(burger).withIngredients(MAGGIE_INGREDIENTS).build();
        assertFalse(burger.equals(editedBurger));

        // different steps -> returns false
        editedBurger = new RecipeBuilder(burger).withSteps(MAGGIE_STEPS).build();
        assertFalse(burger.equals(editedBurger));

        // different descriptions -> returns false
        editedBurger = new RecipeBuilder(burger).withDescription(MAGGIE_DESCRIPTION).build();
        assertFalse(burger.equals(editedBurger));
    }
}
