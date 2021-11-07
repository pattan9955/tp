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
public class IngredientCard extends UiPart<Region> {

    private static final String FXML = "IngredientListCard.fxml";

    // Char limits
    private static final int DESCRIPTION_CHAR_LIMIT = 75;
    private static final int NAME_CHAR_LIMIT = 20;
    private static final int QUANTITY_CHAR_LIMIT = 50;
    private static final int TAG_CHAR_LIMIT = 30;

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
    private Label id;
    @FXML
    private Label quantity;
    @FXML
    private Label description;
    @FXML
    private Label expiryDate;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code IngredientCode} with the given {@code Ingredient} and index to display.
     */
    public IngredientCard(Ingredient ingredient, int displayedIndex) {
        super(FXML);
        this.ingredient = ingredient;

        String ingredientName = UiUtil.truncateText(ingredient.getName().fullName, NAME_CHAR_LIMIT);
        String ingredientDescription = UiUtil.truncateText(ingredient.getDescription().value.orElse(""),
                DESCRIPTION_CHAR_LIMIT);
        String ingredientQuantity = UiUtil.truncateText(ingredient.getQuantity().toString(), QUANTITY_CHAR_LIMIT);

        id.setText(displayedIndex + ". ");
        name.setText(ingredientName);
        quantity.setText("Quantity: " + ingredientQuantity);
        description.setText(ingredientDescription);
        if (ingredientDescription.equals("")) {
            description.setVisible(false);
            description.managedProperty().bind(description.visibleProperty());
        }
        expiryDate.setText("Expiry Date: " + ingredient.getExpiryDate().toString());
        ingredient.getTags().stream()
                .sorted(Comparator.comparing(tag ->
                        tag.tagName.equals("expired") || tag.tagName.equals("expiring")
                                ? "" // comes first in ordering
                                : tag.tagName)
                )
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
        if (ingredient.getTags().size() == 0) {
            tags.setVisible(false);
            tags.managedProperty().bind(tags.visibleProperty());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IngredientCard)) {
            return false;
        }

        // state check
        IngredientCard card = (IngredientCard) other;
        return id.getText().equals(card.id.getText())
                && ingredient.equals(card.ingredient);
    }
}
