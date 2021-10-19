package fridgy.logic.commands.recipe;

import static fridgy.commons.core.Messages.MESSAGE_RECIPES_LISTED_OVERVIEW;
import static fridgy.testutil.Assert.assertThrows;
import static fridgy.testutil.TypicalRecipes.BURGER;
import static fridgy.testutil.TypicalRecipes.FRIES;
import static fridgy.testutil.TypicalRecipes.MAGGIE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import fridgy.logic.commands.CommandResult;
import fridgy.model.Inventory;
import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.RecipeModel;
import fridgy.model.UserPrefs;
import fridgy.model.recipe.NameContainsKeywordsPredicate;
import fridgy.testutil.TypicalRecipes;

public class FindRecipeCommandTest {

    public static final String VALID_FIND_COMMAND_THREE_PLURAL_MESSAGE = String.format(MESSAGE_RECIPES_LISTED_OVERVIEW,
            3, "s");
    public static final String VALID_FIND_COMMAND_ONE_SINGULAR_MESSAGE = String.format(MESSAGE_RECIPES_LISTED_OVERVIEW,
            1, "");
    public static final String VALID_FIND_COMMAND_ZERO_MESSAGE = String.format(MESSAGE_RECIPES_LISTED_OVERVIEW,
            0, "");

    private Model model = new ModelManager(new Inventory(), TypicalRecipes.getTypicalRecipeBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(new Inventory(), TypicalRecipes.getTypicalRecipeBook(),
            new UserPrefs());

    @Test
    public void constructor_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FindRecipeCommand(null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        RecipeModel nullModel = null;
        assertThrows(NullPointerException.class, () ->
                new FindRecipeCommand(predicate).execute(nullModel));
    }

    @Test
    public void equals_sameCommand_returnsTrue() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        FindRecipeCommand testCommand = new FindRecipeCommand(predicate);
        assertTrue(testCommand.equals(testCommand));
    }

    @Test
    public void equals_differentCommandSameValue_returnsTrue() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate firstCopyPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        FindRecipeCommand testCommand = new FindRecipeCommand(firstPredicate);
        FindRecipeCommand targetCommand = new FindRecipeCommand(firstCopyPredicate);
        assertTrue(testCommand.equals(targetCommand));
    }

    @Test
    public void equals_differentCommandDifferentValue_returnsFalse() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
        FindRecipeCommand testCommand = new FindRecipeCommand(firstPredicate);
        FindRecipeCommand targetCommand = new FindRecipeCommand(secondPredicate);
        assertFalse(testCommand.equals(targetCommand));
    }

    @Test
    public void equals_targetObjectNotCommand_returnsFalse() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        FindRecipeCommand testCommand = new FindRecipeCommand(predicate);
        Object targetObject = "first";
        assertFalse(testCommand.equals(targetObject));
    }

    @Test
    public void equals_targetObjectNull_returnsFalse() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        FindRecipeCommand testCommand = new FindRecipeCommand(predicate);
        assertFalse(testCommand.equals(null));
    }

    @Test
    public void execute_zeroKeywords_noRecipeFound() {
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindRecipeCommand command = new FindRecipeCommand(predicate);
        expectedModel.updateFilteredRecipeList(predicate);
        CommandResult expected = new CommandResult(VALID_FIND_COMMAND_ZERO_MESSAGE);
        assertTrue(command.execute(model).equals(expected));
        assertEquals(Collections.emptyList(), model.getFilteredRecipeList());
    }

    @Test
    public void execute_multipleKeywords_multipleRecipesFound() {
        NameContainsKeywordsPredicate predicate = preparePredicate("Burger Maggie Fries Churros Toast Waffles");
        FindRecipeCommand command = new FindRecipeCommand(predicate);
        CommandResult expected = new CommandResult(VALID_FIND_COMMAND_THREE_PLURAL_MESSAGE);
        expectedModel.updateFilteredRecipeList(predicate);
        command.execute(model);
        assertTrue(command.execute(model).equals(expected));
        assertEquals(Arrays.asList(BURGER, MAGGIE, FRIES),
                model.getFilteredRecipeList());
    }

    @Test
    public void execute_multipleKeywords_oneRecipesFound() {
        NameContainsKeywordsPredicate predicate = preparePredicate("Burger Steak Pizza Churros Toast Waffles");
        FindRecipeCommand command = new FindRecipeCommand(predicate);
        CommandResult expected = new CommandResult(VALID_FIND_COMMAND_ONE_SINGULAR_MESSAGE);
        expectedModel.updateFilteredRecipeList(predicate);
        assertTrue(command.execute(model).equals(expected));
        assertEquals(Arrays.asList(BURGER),
                model.getFilteredRecipeList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
