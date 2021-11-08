package fridgy.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fridgy.logic.commands.ingredient.AddIngredientCommand;
import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.RecipeBook;
import fridgy.model.UserPrefs;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.IngredientDefaultComparator;
import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.TypicalIngredients;

/**
 * Contains integration tests (interaction with the Model) for {@code AddIngredientCommand}.
 */
public class AddIngredientCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalIngredients.getTypicalInventory(), new RecipeBook(), new UserPrefs());
    }

    @Test
    public void execute_newIngredient_success() {
        Ingredient validIngredient = new IngredientBuilder().build();

        Model expectedModel = new ModelManager(model.getInventory(), new RecipeBook(), new UserPrefs());
        expectedModel.add(validIngredient);
        expectedModel.sortIngredient(new IngredientDefaultComparator());

        CommandTestUtil.assertCommandSuccess(new AddIngredientCommand(validIngredient), model,
                String.format(fridgy.logic.commands.ingredient.AddIngredientCommand.MESSAGE_SUCCESS, validIngredient), expectedModel);
    }

    @Test
    public void execute_duplicateIngredient_throwsCommandException() {
        model.sortIngredient(new IngredientDefaultComparator());
        Ingredient ingredientInList = model.getInventory().getList().get(0);
        CommandTestUtil.assertCommandFailure(new AddIngredientCommand(ingredientInList), model,
                fridgy.logic.commands.ingredient.AddIngredientCommand.MESSAGE_DUPLICATE_INGREDIENT);
    }

}
