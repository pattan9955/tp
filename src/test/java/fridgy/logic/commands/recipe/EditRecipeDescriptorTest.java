package fridgy.logic.commands.recipe;

import static fridgy.logic.commands.recipe.EditRecipeCommand.EditRecipeDescriptor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fridgy.testutil.EditRecipeDescriptorBuilder;

public class EditRecipeDescriptorTest {
    private final String ingr1 = "ingr1 100mg";
    private final String ingr2 = "ingr2 200mg";

    @Test
    public void equals_sameObject_returnsTrue() {
        EditRecipeDescriptor testDescriptor = new EditRecipeDescriptorBuilder()
                .withName("Test Name")
                .withDescription("Test Description")
                .withIngredients(ingr1, ingr2)
                .withSteps("Step 1", "Step 2")
                .build();
        assertEquals(testDescriptor, testDescriptor);
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        EditRecipeDescriptor testDescriptor = new EditRecipeDescriptorBuilder()
                .withName("Test Name")
                .withDescription("Test Description")
                .withIngredients(ingr1, ingr2)
                .withSteps("Step 1", "Step 2")
                .build();
        EditRecipeDescriptor targetDescriptor = new EditRecipeDescriptorBuilder()
                .withName("Test Name")
                .withDescription("Test Description")
                .withIngredients(ingr1, ingr2)
                .withSteps("Step 1", "Step 2")
                .build();

        assertEquals(testDescriptor, targetDescriptor);
    }

    @Test
    public void equals_nullValue_returnsFalse() {
        EditRecipeDescriptor testDescriptor = new EditRecipeDescriptorBuilder()
                .withName("Test Name")
                .withDescription("Test Description")
                .withIngredients(ingr1, ingr2)
                .withSteps("Step 1", "Step 2")
                .build();

        assertNotEquals(testDescriptor, null);
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        EditRecipeDescriptor testDescriptor = new EditRecipeDescriptorBuilder()
                .withName("Test Name")
                .withDescription("Test Description")
                .withIngredients(ingr1, ingr2)
                .withSteps("Step 1", "Step 2")
                .build();

        assertNotEquals(testDescriptor, 8);
    }

    @Test
    public void equals_differentName_returnsFalse() {
        EditRecipeDescriptor testDescriptor = new EditRecipeDescriptorBuilder()
                .withName("Test Name")
                .withDescription("Test Description")
                .withIngredients(ingr1, ingr2)
                .withSteps("Step 1", "Step 2")
                .build();
        EditRecipeDescriptor targetDescriptor = new EditRecipeDescriptorBuilder(testDescriptor)
                .withName("Different Test Name")
                .build();

        assertNotEquals(testDescriptor, targetDescriptor);
    }

    @Test
    public void equals_differentSteps_returnsFalse() {
        EditRecipeDescriptor testDescriptor = new EditRecipeDescriptorBuilder()
                .withName("Test Name")
                .withDescription("Test Description")
                .withIngredients(ingr1, ingr2)
                .withSteps("Step 1", "Step 2")
                .build();
        EditRecipeDescriptor targetDescriptor = new EditRecipeDescriptorBuilder(testDescriptor)
                .withSteps("New Step 1", "New Step 2")
                .build();

        assertNotEquals(testDescriptor, targetDescriptor);
    }

    @Test
    public void equals_differentIngredients_returnsFalse() {
        EditRecipeDescriptor testDescriptor = new EditRecipeDescriptorBuilder()
                .withName("Test Name")
                .withDescription("Test Description")
                .withIngredients(ingr1, ingr2)
                .withSteps("Step 1", "Step 2")
                .build();
        EditRecipeDescriptor targetDescriptor = new EditRecipeDescriptorBuilder(testDescriptor)
                .withIngredients("New ingr1 100mg", "New ingr2 200mg")
                .build();

        assertNotEquals(testDescriptor, targetDescriptor);
    }

    @Test
    public void equals_differentDescription_returnsFalse() {
        EditRecipeDescriptor testDescriptor = new EditRecipeDescriptorBuilder()
                .withName("Test Name")
                .withDescription("Test Description")
                .withIngredients(ingr1, ingr2)
                .withSteps("Step 1", "Step 2")
                .build();
        EditRecipeDescriptor targetDescriptor = new EditRecipeDescriptorBuilder(testDescriptor)
                .withDescription("New Test Description")
                .build();

        assertNotEquals(testDescriptor, targetDescriptor);
    }

    @Test
    public void isAnyFieldEdited_noFieldEdited_returnsFalse() {
        EditRecipeDescriptor testDescriptor = new EditRecipeDescriptor();
        assertFalse(testDescriptor.isAnyFieldEdited());
    }

    @Test
    public void isAnyFieldEdited_fieldEdited_returnsTrue() {
        EditRecipeDescriptor testDescriptor = new EditRecipeDescriptorBuilder()
                .withName("Edited name")
                .build();
        assertTrue(testDescriptor.isAnyFieldEdited());
    }
}
