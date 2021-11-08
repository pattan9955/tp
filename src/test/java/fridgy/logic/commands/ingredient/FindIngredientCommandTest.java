package fridgy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.ingredient.FindIngredientCommand;
import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.RecipeBook;
import fridgy.model.UserPrefs;
import fridgy.model.ingredient.NameContainsKeywordsPredicate;
import fridgy.testutil.TypicalIngredients;

/**
 * Contains integration tests (interaction with the Model) for {@code FindIngredientCommand}.
 */
public class FindIngredientCommandTest {
    private Model model = new ModelManager(TypicalIngredients.getTypicalInventory(), new RecipeBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalIngredients.getTypicalInventory(), new RecipeBook(),
            new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindIngredientCommand findFirstCommand = new FindIngredientCommand(firstPredicate);
        FindIngredientCommand findSecondCommand = new FindIngredientCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindIngredientCommand findFirstCommandCopy = new FindIngredientCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different ingredient -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noIngredientFound() {
        String expectedMessage = String.format(Messages.MESSAGE_INGREDIENTS_LISTED_OVERVIEW, 0, "");
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindIngredientCommand command = new FindIngredientCommand(predicate);
        expectedModel.updateFilteredIngredientList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredIngredientList());
    }

    @Test
    public void execute_multipleKeywords_oneIngredientFound() {
        String expectedMessage = String.format(Messages.MESSAGE_INGREDIENTS_LISTED_OVERVIEW, 1, "");
        NameContainsKeywordsPredicate predicate = preparePredicate("Carrot Strawberry Dragon Fruit");
        FindIngredientCommand command = new FindIngredientCommand(predicate);
        expectedModel.updateFilteredIngredientList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalIngredients.CARROT), model.getFilteredIngredientList());
    }

    @Test
    public void execute_multipleKeywords_multipleIngredientsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_INGREDIENTS_LISTED_OVERVIEW, 3, "s");
        NameContainsKeywordsPredicate predicate = preparePredicate("Carrot Slices Egg mayo Fig jam");
        FindIngredientCommand command = new FindIngredientCommand(predicate);
        expectedModel.updateFilteredIngredientList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalIngredients.CARROT, TypicalIngredients.EGG, TypicalIngredients.FIGS),
                model.getFilteredIngredientList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
