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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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
    private VBox availableIngredientsPlaceholder;
    @FXML
    private VBox missingIngredientsPlaceholder;
    @FXML
    private Label description;
    @FXML
    private Label stepsTitle;
    @FXML
    private Label steps;

    /**
     * Creates a {@code RecipeCode} with the given {@code Recipe}.
     */
    public RecipeDisplay(Recipe recipe, Function<BaseIngredient, Boolean> isEnough) {
        super(FXML);
        this.recipe = recipe;


        name.setText(recipe.getName().fullName);
        long missingIngredientsCount = recipe.getIngredients().stream()
                .filter(ingredient -> !isEnough.apply(ingredient)).count();
        long availableIngredientsCount = recipe.getIngredients().size() - missingIngredientsCount;
        if (missingIngredientsCount == 0) {
            missingIngredientsPlaceholder.setVisible(false);
            missingIngredientsPlaceholder.managedProperty().bind(missingIngredientsPlaceholder.visibleProperty());
        }
        if (availableIngredientsCount == 0) {
            availableIngredientsPlaceholder.setVisible(false);
            availableIngredientsPlaceholder.managedProperty().bind(availableIngredientsPlaceholder.visibleProperty());
        }
        recipe.getIngredients().stream()
                .sorted(Comparator.comparing(ingredient -> ingredient.getName().toString()))
                .forEach(ingredient -> {
                    Label ingredientLabel = new Label(ingredient.getQuantity().toString()
                                    + " "
                                    + UiUtil.truncateText(
                            ingredient.getName().toString(),
                            INGREDIENT_CHAR_LIMIT)
                    );
                    if (!isEnough.apply(ingredient)) {
                        missingIngredientsPlaceholder.getChildren().add(ingredientLabel);
                    } else {
                        availableIngredientsPlaceholder.getChildren().add(ingredientLabel);
                    }
                });
        String recipeDescription = recipe.getDescription().orElse("");
        description.setText(recipe.getDescription().orElse(""));
        if (recipeDescription.length() < 20) {
            description.setFont(Font.font("Montserrat Regular", 32));
        } else if (recipeDescription.length() < 30) {
            description.setFont(Font.font("Montserrat Regular", 25));
        } else {
            description.setFont(Font.font("Montserrat Regular", 20));
        }
        stepsTitle.setText("Steps:");
        steps.setText(UiUtil.numberedList(recipe.getSteps()));
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
