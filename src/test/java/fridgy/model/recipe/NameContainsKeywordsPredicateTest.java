package fridgy.model.recipe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.RecipeBuilder;

public class NameContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different recipe -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        fridgy.model.recipe.NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("Burger"));
        assertTrue(predicate.test(new RecipeBuilder().withName("Chicken Burger").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Chicken", "Burger"));
        assertTrue(predicate.test(new RecipeBuilder().withName("Chicken Burger").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Burger", "Mushroom"));
        assertTrue(predicate.test(new RecipeBuilder().withName("Chicken Burger").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("burGer", "cHicKen"));
        assertTrue(predicate.test(new RecipeBuilder().withName("Chicken Burger").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new RecipeBuilder().withName("Burger").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Fries"));
        assertFalse(predicate.test(new RecipeBuilder().withName("Chicken Burger").build()));

        // Keywords match ingredients, steps and description, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Mushroom", "Chicken", "Cheese", "Toast", "the",
                "bun", "Cook", "Good", "Stuff"));
        assertFalse(predicate.test(new RecipeBuilder().withName("Burger")
                .withIngredients(Arrays.asList(
                    new IngredientBuilder().withName("Mushroom").withQuantity("15").buildBaseIngredient(),
                    new IngredientBuilder().withName("Chicken").withQuantity("300g").buildBaseIngredient(),
                    new IngredientBuilder().withName("Cheese").withQuantity("50g").buildBaseIngredient()
                ))
                .withSteps(Arrays.asList("Toast the bun", "Cook the chicken"))
                .withDescription("Good stuff").build()));
    }
}
