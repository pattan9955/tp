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
    private static final int INGREDIENT_CHAR_LIMIT = 40;
    private static final int QUANTITY_CHAR_LIMIT = 25;

    // Default Empty Step Message
    private static final String EMPTY_STEP_MESSAGE = "This recipe has no step.";

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
    private VBox availableIngredientsSection;
    @FXML
    private VBox missingIngredientsSection;
    @FXML
    private Label availableIngredientTitle;
    @FXML
    private Label missingIngredientTitle;
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

        String recipeDescription = recipe.getDescription().orElse("");

        name.setText(recipe.getName().fullName);
        long missingIngredientsCount = recipe.getIngredients().stream()
                .filter(ingredient -> !isEnough.apply(ingredient)).count();
        long availableIngredientsCount = recipe.getIngredients().size() - missingIngredientsCount;
        if (missingIngredientsCount == 0) {
            missingIngredientsSection.setVisible(false);
            missingIngredientsSection.managedProperty().bind(missingIngredientsSection.visibleProperty());
        }
        if (availableIngredientsCount == 0) {
            availableIngredientsSection.setVisible(false);
            availableIngredientsSection.managedProperty().bind(availableIngredientsSection.visibleProperty());
        }

        availableIngredientTitle.setText("Available Ingredients");

        missingIngredientTitle.setText("Missing Ingredients");

        recipe.getIngredients().stream()
                .sorted(Comparator.comparing(ingredient -> ingredient.getName().toString()))
                .forEach(ingredient -> {
                    Label ingredientLabel = new Label(UiUtil.formatBaseIngredient(ingredient,
                            INGREDIENT_CHAR_LIMIT, QUANTITY_CHAR_LIMIT));
                    ingredientLabel.setWrapText(true);
                    if (!isEnough.apply(ingredient)) {
                        missingIngredientsPlaceholder.getChildren().add(ingredientLabel);
                    } else {
                        availableIngredientsPlaceholder.getChildren().add(ingredientLabel);
                    }
                });
        description.setText(recipeDescription);
        if (recipeDescription.equals("")) {
            description.setVisible(false);
            description.managedProperty().bind(description.visibleProperty());
        }
        if (recipeDescription.length() < 30) {
            description.setFont(Font.font("Montserrat Regular", 20));
        } else {
            description.setFont(Font.font("Montserrat Regular", 16));
        }
        stepsTitle.setText("Steps:");

        String numberedSteps = UiUtil.numberedList(recipe.getSteps());
        String stepsText = numberedSteps.equals("") ? EMPTY_STEP_MESSAGE : numberedSteps;
        steps.setText(stepsText);
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
