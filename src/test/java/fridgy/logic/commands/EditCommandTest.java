package fridgy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.model.Inventory;
import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.RecipeBook;
import fridgy.model.UserPrefs;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.IngredientDefaultComparator;
import fridgy.testutil.EditIngredientDescriptorBuilder;
import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.TypicalIndexes;
import fridgy.testutil.TypicalIngredients;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(TypicalIngredients.getTypicalInventory(), new RecipeBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Ingredient editedIngredient = new IngredientBuilder().build();
        EditCommand.EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder(editedIngredient).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_INGREDIENT_SUCCESS, editedIngredient);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new RecipeBook(), new UserPrefs());
        expectedModel.set(model.getFilteredIngredientList().get(0), editedIngredient);

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastIngredient = Index.fromOneBased(model.getFilteredIngredientList().size());
        Ingredient lastIngredient = model.getFilteredIngredientList().get(indexLastIngredient.getZeroBased());

        IngredientBuilder ingredientInList = new IngredientBuilder(lastIngredient);
        Ingredient editedIngredient = ingredientInList
                .withName(CommandTestUtil.VALID_NAME_BASIL)
                .withQuantity(CommandTestUtil.VALID_QUANTITY_BASIL)
                .withTags(CommandTestUtil.VALID_TAG_VEGETABLE).build();

        EditCommand.EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_BASIL)
                .withQuantity(CommandTestUtil.VALID_QUANTITY_BASIL)
                .withTags(CommandTestUtil.VALID_TAG_VEGETABLE).build();
        EditCommand editCommand = new EditCommand(indexLastIngredient, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_INGREDIENT_SUCCESS, editedIngredient);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new RecipeBook(), new UserPrefs());
        expectedModel.set(lastIngredient, editedIngredient);
        expectedModel.sortIngredient(new IngredientDefaultComparator());
        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT,
                new EditCommand.EditIngredientDescriptor());
        Ingredient editedIngredient = model
                .getFilteredIngredientList()
                .get(TypicalIndexes.INDEX_FIRST_INGREDIENT.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_INGREDIENT_SUCCESS, editedIngredient);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new RecipeBook(), new UserPrefs());

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showIngredientAtIndex(model, TypicalIndexes.INDEX_FIRST_INGREDIENT);

        Ingredient ingredientInFilteredList = model.getFilteredIngredientList()
                .get(TypicalIndexes.INDEX_FIRST_INGREDIENT.getZeroBased());
        Ingredient editedIngredient = new IngredientBuilder(ingredientInFilteredList)
                .withName(CommandTestUtil.VALID_NAME_BASIL).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT,
                new EditIngredientDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BASIL).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_INGREDIENT_SUCCESS, editedIngredient);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new RecipeBook(), new UserPrefs());
        expectedModel.set(model.getFilteredIngredientList().get(0), editedIngredient);
        expectedModel.sortIngredient(new IngredientDefaultComparator());

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateIngredientUnfilteredList_failure() {
        Ingredient firstIngredient = model.getFilteredIngredientList()
                .get(TypicalIndexes.INDEX_FIRST_INGREDIENT.getZeroBased());
        EditCommand.EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder(firstIngredient).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_SECOND_INGREDIENT, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_INGREDIENT);
    }

    @Test
    public void execute_duplicateIngredientFilteredList_failure() {
        CommandTestUtil.showIngredientAtIndex(model, TypicalIndexes.INDEX_FIRST_INGREDIENT);

        // edit Ingredient in filtered list into a duplicate in inventory
        Ingredient ingredientInList = model.getInventory().getList()
                .get(TypicalIndexes.INDEX_SECOND_INGREDIENT.getZeroBased());
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT,
                new EditIngredientDescriptorBuilder(ingredientInList).build());

        CommandTestUtil.assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_INGREDIENT);
    }

    @Test
    public void execute_invalidIngredientIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredIngredientList().size() + 1);
        EditCommand.EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_BASIL).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of inventory
     */
    @Test
    public void execute_invalidIngredientIndexFilteredList_failure() {
        CommandTestUtil.showIngredientAtIndex(model, TypicalIndexes.INDEX_FIRST_INGREDIENT);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_INGREDIENT;
        // ensures that outOfBoundIndex is still in bounds of inventory list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInventory().getList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditIngredientDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BASIL).build());

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand =
                new EditCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT, CommandTestUtil.DESC_ALMOND);

        // same values -> returns true
        EditCommand.EditIngredientDescriptor copyDescriptor =
                new EditCommand.EditIngredientDescriptor(CommandTestUtil.DESC_ALMOND);
        EditCommand commandWithSameValues =
                new EditCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand(false)));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_SECOND_INGREDIENT,
                CommandTestUtil.DESC_ALMOND)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_FIRST_INGREDIENT,
                CommandTestUtil.DESC_BASIL)));
    }

}
