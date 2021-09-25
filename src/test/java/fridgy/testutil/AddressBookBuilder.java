package fridgy.testutil;

import fridgy.model.AddressBook;
import fridgy.model.ingredient.Ingredient;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withIngredient("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Ingredient} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withIngredient(Ingredient Ingredient) {
        addressBook.addIngredient(Ingredient);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
