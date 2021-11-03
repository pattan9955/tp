package fridgy.model.recipe;

import static fridgy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class StepTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Step(null));
    }

    @Test
    public void hashCodeTest() {
        Step step = new Step("A");
        Step stepSame = new Step("A");
        Step stepDiff = new Step("B");

        assertEquals(step.hashCode(), step.hashCode());
        assertEquals(step.hashCode(), stepSame.hashCode());
        assertNotEquals(step.hashCode(), stepDiff.hashCode());
    }
}
