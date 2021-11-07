package fridgy.model.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles calculations for quantity.
 */
public class QuantityCalc {

    private static final String STANDARD_SOLID_UNITS = "g";
    private static final String STANDARD_LIQUID_UNITS = "l";
    private static final char GRAMS = 'g';
    private static final char LITRES = 'l';
    private static final char KILO_PREFIX = 'k';
    private static final char MILLI_PREFIX = 'm';
    private static final Double KILO_MULTIPLIER = 1000.0;
    private static final Double MILLI_MULTIPLIER = 0.001;

    /**
     * Convert quantity of ingredients units' (if any) to standard units.
     *
     * @param quantity the quantity of ingredients with or without units
     * @return the string representation with units converted to standardised units
     */
    public static String standardiseQuantity(String quantity) {
        List<String> parsedResults = parseQuantityString(quantity);
        if (parsedResults.size() <= 1) {
            Double amount = Double.parseDouble(parsedResults.get(0));
            // return in quantity standardised to 3 decimal places
            return String.format("%.3f", amount);
        }
        Double amount = Double.parseDouble(parsedResults.get(0));
        String units = parsedResults.get(1);
        char[] unitsChar = units.toCharArray();
        char baseUnit = unitsChar.length == 1 ? unitsChar[0] : unitsChar[1];

        switch (baseUnit) {
        case GRAMS:
            // conversion to kilograms
            if (unitsChar.length == 2) {
                amount = removeUnitPrefix(unitsChar[0], amount);
            }
            units = STANDARD_SOLID_UNITS;
            break;
        case LITRES:
            // conversion to liters
            if (unitsChar.length == 2) {
                amount = removeUnitPrefix(unitsChar[0], amount);
            }
            units = STANDARD_LIQUID_UNITS;
            break;
        default:
            // other units are currently not supported
        }
        // return in quantity standardised to 3 decimal places
        return String.format("%.3f %s", amount, units);
    }

    private static List<String> parseQuantityString(String quantity) {
        Matcher match = Pattern.compile("(\\d+([.]\\d*)?|[.]\\d+)|[a-z]+").matcher(quantity);
        List<String> regexMatchResults = new ArrayList<>();
        while (match.find()) {
            regexMatchResults.add(match.group());
        }
        return regexMatchResults;
    }

    private static Double removeUnitPrefix(char prefix, Double amount) {
        switch (prefix) {
        case KILO_PREFIX:
            amount *= KILO_MULTIPLIER;
            break;
        case MILLI_PREFIX:
            amount *= MILLI_MULTIPLIER;
            break;
        default:
            // no other prefix currently supported
        }
        return amount;
    }

    private static Double addUnitPrefix(char prefix, Double amount) {
        switch (prefix) {
        case KILO_PREFIX:
            amount /= KILO_MULTIPLIER;
            break;
        case MILLI_PREFIX:
            amount /= MILLI_MULTIPLIER;
            break;
        default:
            // no other prefix currently supported
        }
        return amount;
    }
}
