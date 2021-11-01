package fridgy.testutil;

import fridgy.model.Inventory;
import fridgy.model.ingredient.Ingredient;

/**
 * A utility class to help with building Descriptionbook objects.
 * Example usage: <br>
 *     {@code Inventory ab = new InventoryBuilder().withIngredient("John", "Doe").build();}
 */
public class InventoryBuilder {

    private Inventory inventory;

    public InventoryBuilder() {
        inventory = new Inventory();
    }

    public InventoryBuilder(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Adds a new {@code Ingredient} to the {@code Inventory} that we are building.
     */
    public InventoryBuilder withIngredient(Ingredient ingredient) {
        inventory.add(ingredient);
        return this;
    }

    public Inventory build() {
        return inventory;
    }
}
