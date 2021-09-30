package fridgy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static fridgy.logic.commands.CommandTestUtil.DESC_AMY;
import static fridgy.logic.commands.CommandTestUtil.DESC_BOB;
import static fridgy.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static fridgy.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static fridgy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static fridgy.logic.commands.CommandTestUtil.assertCommandFailure;
import static fridgy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static fridgy.logic.commands.CommandTestUtil.showIngredientAtIndex;
import static fridgy.testutil.TypicalIndexes.INDEX_FIRST_INGREDIENT;
import static fridgy.testutil.TypicalIndexes.INDEX_SECOND_INGREDIENT;
import static fridgy.testutil.TypicalIngredients.getTypicalInventory;
import static fridgy.testutil.TypicalRecipes.getTypicalRecipeBook;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.logic.commands.EditCommand.EditIngredientDescriptor;
import fridgy.model.Inventory;
import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.RecipeBook;
import fridgy.model.UserPrefs;
import fridgy.model.ingredient.Ingredient;
import fridgy.testutil.EditIngredientDescriptorBuilder;
import fridgy.testutil.IngredientBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalInventory(), getTypicalRecipeBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Ingredient editedIngredient = new IngredientBuilder().build();
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder(editedIngredient).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_INGREDIENT, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_INGREDIENT_SUCCESS, editedIngredient);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()),
                new RecipeBook(model.getRecipeBook()), new UserPrefs());
        expectedModel.setIngredient(model.getFilteredIngredientList().get(0), editedIngredient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastIngredient = Index.fromOneBased(model.getFilteredIngredientList().size());
        Ingredient lastIngredient = model.getFilteredIngredientList().get(indexLastIngredient.getZeroBased());

        IngredientBuilder ingredientInList = new IngredientBuilder(lastIngredient);
        Ingredient editedIngredient = ingredientInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastIngredient, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_INGREDIENT_SUCCESS, editedIngredient);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()),
                new RecipeBook(model.getRecipeBook()), new UserPrefs());
        expectedModel.setIngredient(lastIngredient, editedIngredient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_INGREDIENT, new EditIngredientDescriptor());
        Ingredient editedIngredient = model.getFilteredIngredientList().get(INDEX_FIRST_INGREDIENT.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_INGREDIENT_SUCCESS, editedIngredient);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()),
                new RecipeBook(model.getRecipeBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showIngredientAtIndex(model, INDEX_FIRST_INGREDIENT);

        Ingredient ingredientInFilteredList = model.getFilteredIngredientList().get(INDEX_FIRST_INGREDIENT.getZeroBased());
        Ingredient editedIngredient = new IngredientBuilder(ingredientInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_INGREDIENT,
                new EditIngredientDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_INGREDIENT_SUCCESS, editedIngredient);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()),
                new RecipeBook(model.getRecipeBook()), new UserPrefs());
        expectedModel.setIngredient(model.getFilteredIngredientList().get(0), editedIngredient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateIngredientUnfilteredList_failure() {
        Ingredient firstIngredient = model.getFilteredIngredientList().get(INDEX_FIRST_INGREDIENT.getZeroBased());
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder(firstIngredient).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_INGREDIENT, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_INGREDIENT);
    }

    @Test
    public void execute_duplicateIngredientFilteredList_failure() {
        showIngredientAtIndex(model, INDEX_FIRST_INGREDIENT);

        // edit ingredient in filtered list into a duplicate in address book
        Ingredient ingredientInList = model.getInventory().getIngredientList().get(INDEX_SECOND_INGREDIENT.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_INGREDIENT,
                new EditIngredientDescriptorBuilder(ingredientInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_INGREDIENT);
    }

    @Test
    public void execute_invalidIngredientIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredIngredientList().size() + 1);
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidIngredientIndexFilteredList_failure() {
        showIngredientAtIndex(model, INDEX_FIRST_INGREDIENT);
        Index outOfBoundIndex = INDEX_SECOND_INGREDIENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInventory().getIngredientList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditIngredientDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_INGREDIENT, DESC_AMY);

        // same values -> returns true
        EditIngredientDescriptor copyDescriptor = new EditIngredientDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_INGREDIENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_INGREDIENT, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_INGREDIENT, DESC_BOB)));
    }

}
