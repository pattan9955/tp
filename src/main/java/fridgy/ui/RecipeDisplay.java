package fridgy.ui;

import java.util.Comparator;

import fridgy.model.recipe.Recipe;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * An UI component that displays information of a {@code Recipe}.
 */
public class RecipeDisplay extends UiPart<Region> {

    private static final String FXML = "RecipeDisplay.fxml";

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
    private Label name;
    @FXML
    private FlowPane ingredients;
    @FXML
    private Label description;
    @FXML
    private Label steps;

    /**
     * Creates a {@code RecipeCode} with the given {@code Recipe}.
     */
    public RecipeDisplay(Recipe recipe) {
        super(FXML);
        this.recipe = recipe;

        name.setText(recipe.getName().fullName);
        recipe.getIngredients().stream()
                .sorted(Comparator.comparing(ingredient -> ingredient.getName().toString()))
                .forEach(ingredient -> ingredients.getChildren().add(
                        new Label(UiUtil.truncateText(ingredient.getName().toString(), INGREDIENT_CHAR_LIMIT)
                        )));
        description.setText(recipe.getDescription().orElse(""));
        steps.setText("Steps:\n" + UiUtil.numberedList(recipe.getSteps()));
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
        return name.getText().equals(card.name.getText())
                && recipe.equals(card.recipe);
    }
}
