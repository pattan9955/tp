package fridgy.ui;

import java.util.Comparator;

import fridgy.model.recipe.Recipe;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Recipe}.
 */
public class RecipeCard extends UiPart<Region> {

    private static final String FXML = "RecipeListCard.fxml";

    // Char limits
    private static final int DESCRIPTION_CHAR_LIMIT = 155;
    private static final int INGREDIENT_CHAR_LIMIT = 55;
    private static final int NAME_CHAR_LIMIT = 25;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Inventory level 4</a>
     */

    public final Recipe recipe;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane ingredients;
    @FXML
    private Label description;

    /**
     * Creates a {@code RecipeCode} with the given {@code Recipe} and index to display.
     */
    public RecipeCard(Recipe recipe, int displayedIndex) {
        super(FXML);
        this.recipe = recipe;

        String recipeName = UiUtil.truncateText(recipe.getName().fullName, NAME_CHAR_LIMIT);
        String recipeDescription = UiUtil.truncateText(recipe.getDescription().orElse(""),
                DESCRIPTION_CHAR_LIMIT);

        id.setText(displayedIndex + ". ");
        name.setText(recipeName);
        recipe.getIngredients().stream()
                .sorted(Comparator.comparing(ingredient -> ingredient.getName()))
                .forEach(ingredient -> ingredients.getChildren().add(
                        new Label(UiUtil.truncateText(ingredient.getName(), INGREDIENT_CHAR_LIMIT)
                        )));
        description.setText(recipeDescription);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecipeCard)) {
            return false;
        }

        // state check
        RecipeCard card = (RecipeCard) other;
        return id.getText().equals(card.id.getText())
                && recipe.equals(card.recipe);
    }
}
