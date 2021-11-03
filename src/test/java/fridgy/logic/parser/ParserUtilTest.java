package fridgy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.ingredient.Description;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Quantity;
import fridgy.model.recipe.Step;
import fridgy.model.tag.Tag;
import fridgy.testutil.Assert;
import fridgy.testutil.TypicalIndexes;


public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_QUANTITY = "+651234";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_TAG = "#snack";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_QUANTITY = "123456";
    private static final String VALID_DESCRIPTION = "123 Main Street #0505";
    private static final String VALID_TAG_1 = "snack";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String VALID_INGREDIENT = "ingredient 1";
    private static final String VALID_INGREDIENT_UNIT = "ingredient2 1kg";
    private static final String INVALID_INGREDIENT = "ingredient";


    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, ParserUtil.MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        Assertions.assertEquals(TypicalIndexes.INDEX_FIRST_INGREDIENT, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        Assertions.assertEquals(TypicalIndexes.INDEX_FIRST_INGREDIENT, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseQuantity_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseQuantity((String) null));
    }

    @Test
    public void parseQuantity_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseQuantity(INVALID_QUANTITY));
    }

    @Test
    public void parseQuantity_validValueWithoutWhitespace_returnsQuantity() throws Exception {
        Quantity expectedQuantity = new Quantity(VALID_QUANTITY);
        assertEquals(expectedQuantity, ParserUtil.parseQuantity(VALID_QUANTITY));
    }

    @Test
    public void parseQuantity_validValueWithWhitespace_returnsTrimmedQuantity() throws Exception {
        String quantityWithWhitespace = WHITESPACE + VALID_QUANTITY + WHITESPACE;
        Quantity expectedQuantity = new Quantity(VALID_QUANTITY);
        assertEquals(expectedQuantity, ParserUtil.parseQuantity(quantityWithWhitespace));
    }

    @Test
    public void parseDescription_null_returnsEmptyDescription() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDescription(Optional.of("")));
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDescription(Optional.of(" ")));
    }

    @Test
    public void parseDescription_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDescription(Optional.of(INVALID_DESCRIPTION)));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() throws Exception {
        Description expectedDescription = new Description(Optional.of(VALID_DESCRIPTION));
        assertEquals(expectedDescription, ParserUtil.parseDescription(Optional.of(VALID_DESCRIPTION)));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedDescription() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_DESCRIPTION + WHITESPACE;
        Description expectedDescription = new Description(Optional.of(VALID_DESCRIPTION));
        assertEquals(expectedDescription, ParserUtil.parseDescription(Optional.of(addressWithWhitespace)));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseBaseIngredient_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseBaseIngredient(null));
    }

    @Test
    public void parseBaseIngredient_missingQuantity_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseBaseIngredient(INVALID_INGREDIENT));
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseBaseIngredient(INVALID_INGREDIENT + " "));
    }

    @Test
    public void parseBaseIngredient_validIngredient_success() throws Exception {
        BaseIngredient expected = new BaseIngredient(new Name("ingredient"), new Quantity("1"));
        assertEquals(expected, ParserUtil.parseBaseIngredient(VALID_INGREDIENT));

        expected = new BaseIngredient(new Name("ingredient2"), new Quantity("1kg"));
        assertEquals(expected, ParserUtil.parseBaseIngredient(VALID_INGREDIENT_UNIT));
    }

    @Test
    public void parseIngredients_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseIngredients(null));
    }

    @Test
    public void parseIngredients_invalidIngredient_throwsParseException() {
        List<String> toParse = Arrays.asList(VALID_INGREDIENT, INVALID_INGREDIENT);
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseIngredients(toParse));
    }

    @Test
    public void parseIngredients_validIngredients_success() throws Exception {
        List<String> toParse = Arrays.asList(VALID_INGREDIENT, VALID_INGREDIENT_UNIT);
        Set<BaseIngredient> expected = new HashSet<>(Arrays.asList(
            new BaseIngredient(new Name("ingredient"), new Quantity("1")),
            new BaseIngredient(new Name("ingredient2"), new Quantity("1kg"))
            ));

        assertEquals(expected, ParserUtil.parseIngredients(toParse));
    }

    @Test
    public void parseIngredients_emptyIngredients_throwsParseException() {
        // should reject having no ingredients
        List<String> nothing = new ArrayList<>();
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseIngredients(nothing));
    }

    @Test
    public void parseIngredients_blankIngredients_throwsParseException() {
        // should reject having blank strings as an ingredient
        List<String> toParse = Arrays.asList(VALID_INGREDIENT, VALID_INGREDIENT_UNIT, "");
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseIngredients(toParse));

        List<String> toParse2 = Arrays.asList(VALID_INGREDIENT, VALID_INGREDIENT_UNIT, "       ");
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseIngredients(toParse2));
    }

    @Test
    public void parseSteps_emptySteps_success() {
        List<String> nothing = new ArrayList<>();
        try {
            assertEquals(new ArrayList<Step>(), ParserUtil.parseSteps(nothing));
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void parseSteps_validSteps_success() {
        List<String> validSteps = Arrays.asList("A", "B", "C");
        List<Step> expected = Arrays.asList(new Step("A"), new Step("B"), new Step("C"));
        try {
            assertEquals(expected, ParserUtil.parseSteps(validSteps));
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void parseSteps_blankSteps_throwsParseException() {
        List<String> validSteps = Arrays.asList("A", "", "C");
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseSteps(validSteps));
    }

    @Test
    public void parseStep_validStep_success() {
        assertEquals(new Step(""), ParserUtil.parseStep(""));
        assertEquals(new Step(""), ParserUtil.parseStep("       "));
        assertEquals(new Step("123AbCd"), ParserUtil.parseStep("   123AbCd   "));
        assertEquals(new Step("123AbCd"), ParserUtil.parseStep("123AbCd"));
    }
}
