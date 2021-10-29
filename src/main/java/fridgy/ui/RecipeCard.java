package fridgy.ui;

import java.util.Comparator;
import java.util.function.Function;

import fridgy.model.ingredient.BaseIngredient;
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
    private static final int DESCRIPTION_CHAR_LIMIT = 100;
    private static final int INGREDIENT_CHAR_LIMIT = 30;
    private static final int NAME_CHAR_LIMIT = 20;

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
    public RecipeCard(Recipe recipe, int displayedIndex,
                      Function<BaseIngredient, Boolean> isEnough) {
        super(FXML);
        this.recipe = recipe;

        String recipeName = UiUtil.truncateText(recipe.getName().fullName, NAME_CHAR_LIMIT);
        String recipeDescription = UiUtil.truncateText(recipe.getDescription().orElse(""),
                DESCRIPTION_CHAR_LIMIT);

        id.setText(displayedIndex + ". ");
        name.setText(recipeName);

        // sort and iterate through the ingredients in recipe and add it to ingredients FlowPane as tags.
        recipe.getIngredients().stream()
                .sorted(Comparator.comparing(ingredient -> ingredient.getName().toString()))
                .forEach(ingredient -> {
                    Label ingredientLabel = new Label(UiUtil.truncateText(
                        ingredient.getName().toString()
                            + " "
                            + ingredient.getQuantity().toString(),
                        INGREDIENT_CHAR_LIMIT)
                    );
                    if (!isEnough.apply(ingredient)) {
                        ingredientLabel.setStyle("-fx-background-color: #CF1259;");
                    }
                    ingredients.getChildren().add(ingredientLabel);
                });

        description.setText(recipeDescription);
        if (recipeDescription.equals("")) {
            description.setVisible(false);
            description.managedProperty().bind(description.visibleProperty());
        }
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
