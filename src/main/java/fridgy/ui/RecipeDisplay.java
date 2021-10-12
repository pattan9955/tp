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
public class RecipeDisplay extends UiPart<Region> {

    private static final String FXML = "RecipeListCard.fxml";

    // Char limits
    private static final int INGREDIENT_CHAR_LIMIT = 55;

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
    private Label nameDisplay;
    @FXML
    private Label idDisplay;
    @FXML
    private FlowPane ingredientsDisplay;
    @FXML
    private Label descriptionDisplay;
    @FXML
    private Label stepsDisplay;

    /**
     * Creates a {@code RecipeCode} with the given {@code Recipe} and index to display.
     */
    public RecipeDisplay(Recipe recipe, int displayedIndex) {
        super(FXML);
        this.recipe = recipe;

        idDisplay.setText(displayedIndex + ". ");
        nameDisplay.setText(recipe.getName().fullName);
        recipe.getIngredients().stream()
                .sorted(Comparator.comparing(ingredient -> ingredient.getName()))
                .forEach(ingredient -> ingredientsDisplay.getChildren().add(
                        new Label(UiUtil.truncateText(ingredient.getName(), INGREDIENT_CHAR_LIMIT)
                        )));
        descriptionDisplay.setText(recipe.getDescription().orElse(""));
        // stepsDisplay.setText(recipe.getSteps().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecipeDisplay)) {
            return false;
        }

        // state check
        RecipeDisplay card = (RecipeDisplay) other;
        return idDisplay.getText().equals(card.idDisplay.getText())
                && recipe.equals(card.recipe);
    }
}
