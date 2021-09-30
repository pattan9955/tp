package fridgy.logic.commands;

import static fridgy.logic.commands.CommandTestUtil.assertCommandFailure;
import static fridgy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static fridgy.testutil.TypicalIngredients.getTypicalInventory;
import static fridgy.testutil.TypicalRecipes.getTypicalRecipeBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.UserPrefs;
import fridgy.model.ingredient.Ingredient;
import fridgy.testutil.IngredientBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalInventory(), getTypicalRecipeBook(), new UserPrefs());
    }

    @Test
    public void execute_newIngredient_success() {
        Ingredient validIngredient = new IngredientBuilder().build();

        Model expectedModel = new ModelManager(model.getInventory(), model.getRecipeBook(), new UserPrefs());
        expectedModel.addIngredient(validIngredient);

        assertCommandSuccess(new AddCommand(validIngredient), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validIngredient), expectedModel);
    }

    @Test
    public void execute_duplicateIngredient_throwsCommandException() {
        Ingredient ingredientInList = model.getInventory().getIngredientList().get(0);
        assertCommandFailure(new AddCommand(ingredientInList), model, AddCommand.MESSAGE_DUPLICATE_INGREDIENT);
    }

}
