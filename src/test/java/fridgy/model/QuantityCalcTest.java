package fridgy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fridgy.model.util.QuantityCalc;

public class QuantityCalcTest {

    @Test
    public void convertToStandardUnits() {
        // converting differently prefixed grams to grams
        Assertions.assertEquals("69000.000 g", QuantityCalc.standardiseQuantity("69 kg"));
        assertEquals("127.069 g", QuantityCalc.standardiseQuantity("127069mg"));

        // converting differently prefixed litres to litres
        assertEquals("69000.000 l", QuantityCalc.standardiseQuantity("69 kl"));
        assertEquals("0.069 l", QuantityCalc.standardiseQuantity("69 ml"));

        // no units should just return
        assertEquals("126.000", QuantityCalc.standardiseQuantity("126"));
        assertEquals("126.290", QuantityCalc.standardiseQuantity("126.290"));
    }
}
