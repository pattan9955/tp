package fridgy.testutil;

import static fridgy.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static fridgy.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static fridgy.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static fridgy.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static fridgy.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static fridgy.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static fridgy.logic.commands.CommandTestUtil.VALID_QUANTITY_AMY;
import static fridgy.logic.commands.CommandTestUtil.VALID_QUANTITY_BOB;
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

    public static final Ingredient ALICE = new IngredientBuilder().withName("Alice Pauline")
            .withDescription("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withQuantity("94351253")
            .withTags("friends").build();
    public static final Ingredient BENSON = new IngredientBuilder().withName("Benson Meier")
            .withDescription("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withQuantity("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Ingredient CARL = new IngredientBuilder().withName("Carl Kurz").withQuantity("95352563")
            .withEmail("heinz@example.com").withDescription("wall street").build();
    public static final Ingredient DANIEL = new IngredientBuilder().withName("Daniel Meier").withQuantity("87652533")
            .withEmail("cornelia@example.com").withDescription("10th street").withTags("friends").build();
    public static final Ingredient ELLE = new IngredientBuilder().withName("Elle Meyer").withQuantity("9482224")
            .withEmail("werner@example.com").withDescription("michegan ave").build();
    public static final Ingredient FIONA = new IngredientBuilder().withName("Fiona Kunz").withQuantity("9482427")
            .withEmail("lydia@example.com").withDescription("little tokyo").build();
    public static final Ingredient GEORGE = new IngredientBuilder().withName("George Best").withQuantity("9482442")
            .withEmail("anna@example.com").withDescription("4th street").build();

    // Manually added
    public static final Ingredient HOON = new IngredientBuilder().withName("Hoon Meier").withQuantity("8482424")
            .withEmail("stefan@example.com").withDescription("little india").build();
    public static final Ingredient IDA = new IngredientBuilder().withName("Ida Mueller").withQuantity("8482131")
            .withEmail("hans@example.com").withDescription("chicago ave").build();

    // Manually added - Ingredient's details found in {@code CommandTestUtil}
    public static final Ingredient AMY = new IngredientBuilder().withName(VALID_NAME_AMY).withQuantity(VALID_QUANTITY_AMY)
            .withEmail(VALID_EMAIL_AMY).withDescription(VALID_DESCRIPTION_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Ingredient BOB = new IngredientBuilder().withName(VALID_NAME_BOB).withQuantity(VALID_QUANTITY_BOB)
            .withEmail(VALID_EMAIL_BOB).withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
