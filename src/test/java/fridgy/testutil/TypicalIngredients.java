package fridgy.testutil;

import static fridgy.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ALMOND;
import static fridgy.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_EMAIL_ALMOND;
import static fridgy.logic.commands.CommandTestUtil.VALID_EMAIL_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_EMAIL_FISH;
import static fridgy.logic.commands.CommandTestUtil.VALID_NAME_ALMOND;
import static fridgy.logic.commands.CommandTestUtil.VALID_NAME_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_NAME_FISH;
import static fridgy.logic.commands.CommandTestUtil.VALID_QUANTITY_ALMOND;
import static fridgy.logic.commands.CommandTestUtil.VALID_QUANTITY_BASIL;
import static fridgy.logic.commands.CommandTestUtil.VALID_QUANTITY_FISH;
import static fridgy.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static fridgy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fridgy.model.Inventory;
import fridgy.model.ingredient.Ingredient;

/**
 * A utility class containing a list of {@code Ingredient} objects to be used in tests.
 */
public class TypicalIngredients {

    public static final Ingredient APPLE = new IngredientBuilder().withName("Apple Sauce")
            .withDescription("123, Jurong West Ave 6, #08-111").withEmail("almond@example.com")
            .withQuantity("94351253")
            .withTags("friends").build();
    public static final Ingredient BANANA = new IngredientBuilder().withName("Banana")
            .withDescription("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withQuantity("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Ingredient CARROT = new IngredientBuilder().withName("Carrot Slices").withQuantity("95352563")
            .withEmail("heinz@example.com").withDescription("wall street").build();
    public static final Ingredient DUCK = new IngredientBuilder().withName("Duck breast").withQuantity("87652533")
            .withEmail("cornelia@example.com").withDescription("10th street").withTags("friends").build();
    public static final Ingredient EGG = new IngredientBuilder().withName("Egg mayo").withQuantity("9482224")
            .withEmail("werner@example.com").withDescription("michegan ave").build();
    public static final Ingredient FIGS = new IngredientBuilder().withName("Fig jam").withQuantity("9482427")
            .withEmail("lydia@example.com").withDescription("little tokyo").build();
    public static final Ingredient GRAPES = new IngredientBuilder().withName("Grape juice").withQuantity("9482442")
            .withEmail("anna@example.com").withDescription("4th street").build();

    // Manually added
    public static final Ingredient HOON = new IngredientBuilder().withName("Hoon Meier").withQuantity("8482424")
            .withEmail("stefan@example.com").withDescription("little india").build();
    public static final Ingredient IDA = new IngredientBuilder().withName("Ida Mueller").withQuantity("8482131")
            .withEmail("hans@example.com").withDescription("chicago ave").build();

    // Manually added - Ingredient's details found in {@code CommandTestUtil}
    public static final Ingredient ALMOND = new IngredientBuilder().withName(VALID_NAME_ALMOND).withQuantity(VALID_QUANTITY_ALMOND)
            .withEmail(VALID_EMAIL_ALMOND).withDescription(VALID_DESCRIPTION_ALMOND).withTags(VALID_TAG_FRIEND).build();
    public static final Ingredient BASIL = new IngredientBuilder().withName(VALID_NAME_BASIL).withQuantity(VALID_QUANTITY_BASIL)
            .withEmail(VALID_EMAIL_BASIL).withDescription(VALID_DESCRIPTION_BASIL).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();
    public static final Ingredient FISH = new IngredientBuilder().withName(VALID_NAME_FISH).withQuantity(VALID_QUANTITY_FISH)
            .withEmail(VALID_EMAIL_FISH).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalIngredients() {} // prevents instantiation

    /**
     * Returns an {@code Inventory} with all the typical ingredients.
     */
    public static Inventory getTypicalInventory() {
        Inventory ab = new Inventory();
        for (Ingredient ingredient : getTypicalIngredients()) {
            ab.addIngredient(ingredient);
        }
        return ab;
    }

    public static List<Ingredient> getTypicalIngredients() {
        return new ArrayList<>(Arrays.asList(APPLE, BANANA, CARROT, DUCK, EGG, FIGS, GRAPES));
    }
}
