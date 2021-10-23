package fridgy.testutil;

import static fridgy.logic.commands.CommandTestUtil.VALID_NAME_ALMOND;
import static fridgy.logic.commands.CommandTestUtil.VALID_NAME_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_NAME_FISH;
import static fridgy.logic.commands.CommandTestUtil.VALID_QUANTITY_ALMOND;
import static fridgy.logic.commands.CommandTestUtil.VALID_QUANTITY_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_QUANTITY_FISH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fridgy.model.ingredient.BaseIngredient;

/**
 * A utility class containing a list of {@code BaseIngredient} objects to be used in tests.
 */
public class TypicalBaseIngredients {

    public static final BaseIngredient APPLE = new IngredientBuilder().withName("Apple")
            .withQuantity("94351253").buildBaseIngredient();
    public static final BaseIngredient BANANA = new IngredientBuilder().withName("Banana")
            .withQuantity("98765432").buildBaseIngredient();
    public static final BaseIngredient CARROT = new IngredientBuilder().withName("Carrot Slices")
            .withQuantity("95352563").buildBaseIngredient();
    public static final BaseIngredient DUCK = new IngredientBuilder().withName("Duck breast")
            .withQuantity("87652533").buildBaseIngredient();
    public static final BaseIngredient EGG = new IngredientBuilder().withName("Egg mayo")
            .withQuantity("9482224").buildBaseIngredient();
    public static final BaseIngredient FIGS = new IngredientBuilder().withName("Fig jam")
            .withQuantity("9482427").buildBaseIngredient();
    public static final BaseIngredient GRAPES = new IngredientBuilder().withName("Grape")
            .withQuantity("9482442").buildBaseIngredient();
    public static final BaseIngredient INGR1 = new IngredientBuilder().withName("ingr1")
            .withQuantity("1kg").buildBaseIngredient();
    public static final BaseIngredient INGR2 = new IngredientBuilder().withName("ingr2")
        .withQuantity("1ml").buildBaseIngredient();


    // Manually added - Base Ingredient's details found in {@code CommandTestUtil}
    public static final BaseIngredient ALMOND = new IngredientBuilder().withName(VALID_NAME_ALMOND)
            .withQuantity(VALID_QUANTITY_ALMOND).buildBaseIngredient();
    public static final BaseIngredient BASIL = new IngredientBuilder().withName(VALID_NAME_BASIL)
            .withQuantity(VALID_QUANTITY_BASIL).buildBaseIngredient();
    public static final BaseIngredient FISH = new IngredientBuilder().withName(VALID_NAME_FISH)
            .withQuantity(VALID_QUANTITY_FISH).buildBaseIngredient();

    private TypicalBaseIngredients() {} // prevents instantiation

    public static List<BaseIngredient> getTypicalBaseIngredients() {
        return new ArrayList<>(Arrays.asList(APPLE, BANANA, CARROT, DUCK, EGG, FIGS, GRAPES));
    }
}
