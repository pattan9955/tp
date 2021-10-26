package fridgy.ui;

import java.util.Comparator;

import fridgy.model.ingredient.Ingredient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Ingredient}.
 */
public class IngredientDisplay extends UiPart<Region> {

    private static final String FXML = "IngredientDisplay.fxml";

    private static final int TAG_CHAR_LIMIT = 55;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Inventory level 4</a>
     */

    public final Ingredient ingredient;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label quantity;
    @FXML
    private Label description;
    @FXML
    private Label expiryDate;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code IngredientCode} with the given {@code Ingredient}.
     */
    public IngredientDisplay(Ingredient ingredient) {
        super(FXML);
        this.ingredient = ingredient;

        name.setText(ingredient.getName().fullName);
        quantity.setText("Quantity: " + ingredient.getQuantity().value);
        expiryDate.setText("Expiring on: " + ingredient.getExpiryDate().toString());
        description.setText(ingredient.getDescription().value.orElse(""));
        ingredient.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    String name = tag.tagName;
                    Label tagLabel = new Label(UiUtil.truncateText(name, TAG_CHAR_LIMIT));
                    tagLabel.setWrapText(true);
                    if (name == "expired") {
                        tagLabel.setStyle("-fx-background-color: #CF1259;");
                    } else if (name == "expiring") {
                        tagLabel.setStyle("-fx-background-color: #F77F00;");
                    }
                    tags.getChildren().add(tagLabel);
                });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IngredientDisplay)) {
            return false;
        }

        // state check
        IngredientDisplay card = (IngredientDisplay) other;
        return name.getText().equals(card.name.getText())
                && ingredient.equals(card.ingredient);
    }
}
