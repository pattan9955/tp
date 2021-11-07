package fridgy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fridgy.logic.parser.exceptions.ParseException;

public class FridgyParserTestUtil {
    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseFailure(FridgyParser parser, String userInput, String expectedMessage) {
        try {
            parser.parseCommand(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
