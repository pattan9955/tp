package fridgy.logic.commands;

import static fridgy.testutil.TypicalIngredients.CHICKEN;
import static fridgy.testutil.TypicalIngredients.EXPIRED_CHICKEN;
import static fridgy.testutil.TypicalIngredients.EXPIRED_FLOUR;
import static fridgy.testutil.TypicalIngredients.FLOUR;
import static fridgy.testutil.TypicalIngredients.getTypicalInventory;

import org.junit.jupiter.api.Test;

import fridgy.logic.commands.ingredient.ClearIngredientCommand;
import fridgy.model.Inventory;
import fridgy.model.Model;
import fridgy.model.ModelManager;
import fridgy.model.RecipeBook;
import fridgy.model.UserPrefs;

public class ClearIngredientCommandTest {

    /*================================= testing clearing of all ingredients ==========================================*/
    @Test
    public void execute_emptyInventory_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandTestUtil.assertCommandSuccess(new ClearIngredientCommand(false), model, fridgy.logic.commands.ingredient.ClearIngredientCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyInventory_success() {
        Model model = new ModelManager(getTypicalInventory(), new RecipeBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalInventory(), new RecipeBook(),
                new UserPrefs());
        expectedModel.setInventory(new Inventory());

        CommandTestUtil.assertCommandSuccess(new ClearIngredientCommand(false), model, fridgy.logic.commands.ingredient.ClearIngredientCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    /*================================= testing clearing of expired ingredients ======================================*/
    @Test
    public void execute_allExpiredIngredients_success() {
        Model model = new ModelManager(new Inventory(), new RecipeBook(), new UserPrefs());
        model.add(EXPIRED_FLOUR);
        model.add(EXPIRED_CHICKEN);
        Model expectedModel = new ModelManager(new Inventory(), new RecipeBook(), new UserPrefs());
        CommandTestUtil.assertCommandSuccess(new ClearIngredientCommand(true), model,
                fridgy.logic.commands.ingredient.ClearIngredientCommand.MESSAGE_SUCCESS_EXPIRED, expectedModel);
    }

    @Test
    public void execute_someExpiredIngredients_success() {
        Model model = new ModelManager(getTypicalInventory(), new RecipeBook(), new UserPrefs());
        model.add(EXPIRED_CHICKEN);
        model.add(EXPIRED_FLOUR);
        Model expectedModel = new ModelManager(getTypicalInventory(), new RecipeBook(), new UserPrefs());
        CommandTestUtil.assertCommandSuccess(new ClearIngredientCommand(true), model,
                fridgy.logic.commands.ingredient.ClearIngredientCommand.MESSAGE_SUCCESS_EXPIRED, expectedModel);
    }

    @Test
    public void execute_noExpiredIngredients_success() {
        Model model = new ModelManager(new Inventory(), new RecipeBook(), new UserPrefs());
        model.add(CHICKEN);
        model.add(FLOUR);
        Model expectedModel = new ModelManager(new Inventory(), new RecipeBook(), new UserPrefs());
        expectedModel.add(CHICKEN);
        expectedModel.add(FLOUR);
        CommandTestUtil.assertCommandSuccess(new ClearIngredientCommand(true), model,
                ClearIngredientCommand.MESSAGE_SUCCESS_EXPIRED, expectedModel);
    }


}
