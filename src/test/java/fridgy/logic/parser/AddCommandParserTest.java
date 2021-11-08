package fridgy.logic.parser;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.CommandTestUtil;
import fridgy.logic.commands.ingredient.AddCommand;
import fridgy.logic.parser.ingredient.AddCommandParser;
import fridgy.model.ingredient.Description;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Quantity;
import fridgy.model.tag.Tag;
import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.TypicalIngredients;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Ingredient expectedIngredient = new IngredientBuilder(TypicalIngredients.BASIL)
                .withTags(CommandTestUtil.VALID_TAG_SNACK).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + CommandTestUtil.NAME_DESC_BASIL + CommandTestUtil.QUANTITY_DESC_BASIL
                + CommandTestUtil.DESCRIPTION_DESC_BASIL + CommandTestUtil.TAG_DESC_SNACK
                + CommandTestUtil.EXPIRY_DATE_DESC, new AddCommand(expectedIngredient));

        // multiple names - last name accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + CommandTestUtil.NAME_DESC_ALMOND + CommandTestUtil.NAME_DESC_BASIL
                + CommandTestUtil.QUANTITY_DESC_BASIL
                + CommandTestUtil.DESCRIPTION_DESC_BASIL + CommandTestUtil.TAG_DESC_SNACK
                + CommandTestUtil.EXPIRY_DATE_DESC, new AddCommand(expectedIngredient));

        // multiple quantities - last quantity accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + CommandTestUtil.NAME_DESC_BASIL + CommandTestUtil.QUANTITY_DESC_ALMOND
                + CommandTestUtil.QUANTITY_DESC_BASIL + CommandTestUtil.DESCRIPTION_DESC_BASIL
                + CommandTestUtil.TAG_DESC_SNACK + CommandTestUtil.EXPIRY_DATE_DESC,
                new AddCommand(expectedIngredient));

        // multiple descriptions - last description accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + CommandTestUtil.NAME_DESC_BASIL + CommandTestUtil.QUANTITY_DESC_BASIL
                + CommandTestUtil.DESCRIPTION_DESC_ALMOND + CommandTestUtil.DESCRIPTION_DESC_BASIL
                + CommandTestUtil.TAG_DESC_SNACK + CommandTestUtil.EXPIRY_DATE_DESC,
                new AddCommand(expectedIngredient));

        // multiple tags - all accepted
        Ingredient expectedIngredientMultipleTags = new IngredientBuilder(TypicalIngredients.BASIL)
                .withTags(CommandTestUtil.VALID_TAG_SNACK, CommandTestUtil.VALID_TAG_VEGETABLE)
                .build();

        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + CommandTestUtil.NAME_DESC_BASIL + CommandTestUtil.QUANTITY_DESC_BASIL
                + CommandTestUtil.DESCRIPTION_DESC_BASIL + CommandTestUtil.TAG_DESC_VEGETABLE
                + CommandTestUtil.TAG_DESC_SNACK + CommandTestUtil.EXPIRY_DATE_DESC,
                new AddCommand(expectedIngredientMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags

        Ingredient expectedIngredient = new IngredientBuilder(TypicalIngredients.ALMOND).withTags().build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + CommandTestUtil.NAME_DESC_ALMOND + CommandTestUtil.QUANTITY_DESC_ALMOND
                + CommandTestUtil.DESCRIPTION_DESC_ALMOND + CommandTestUtil.EXPIRY_DATE_DESC,
                new AddCommand(expectedIngredient));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                    + CommandTestUtil.VALID_NAME_BASIL + CommandTestUtil.QUANTITY_DESC_BASIL
                    + CommandTestUtil.DESCRIPTION_DESC_BASIL + CommandTestUtil.VALID_EXPIRY_DATE, expectedMessage);

        // missing quantity prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                        + CommandTestUtil.NAME_DESC_BASIL + CommandTestUtil.VALID_QUANTITY_BASIL
                        + CommandTestUtil.DESCRIPTION_DESC_BASIL + CommandTestUtil.VALID_EXPIRY_DATE, expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                        + CommandTestUtil.VALID_NAME_BASIL + CommandTestUtil.VALID_QUANTITY_BASIL
                        + CommandTestUtil.VALID_DESCRIPTION_BASIL + CommandTestUtil.VALID_EXPIRY_DATE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name=
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + CommandTestUtil.INVALID_NAME_DESC + CommandTestUtil.QUANTITY_DESC_BASIL
                + CommandTestUtil.DESCRIPTION_DESC_BASIL + CommandTestUtil.TAG_DESC_VEGETABLE
                + CommandTestUtil.TAG_DESC_SNACK + CommandTestUtil.EXPIRY_DATE_DESC, Name.MESSAGE_CONSTRAINTS);

        // invalid quantity
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + CommandTestUtil.NAME_DESC_BASIL + CommandTestUtil.INVALID_QUANTITY_DESC
                + CommandTestUtil.DESCRIPTION_DESC_BASIL + CommandTestUtil.TAG_DESC_VEGETABLE
                + CommandTestUtil.TAG_DESC_SNACK + CommandTestUtil.EXPIRY_DATE_DESC, Quantity.MESSAGE_CONSTRAINTS);

        // invalid description
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + CommandTestUtil.NAME_DESC_BASIL + CommandTestUtil.QUANTITY_DESC_BASIL
                + CommandTestUtil.INVALID_DESCRIPTION_DESC + CommandTestUtil.TAG_DESC_VEGETABLE
                + CommandTestUtil.TAG_DESC_SNACK + CommandTestUtil.EXPIRY_DATE_DESC, Description.MESSAGE_CONSTRAINTS);

        // invalid description
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + CommandTestUtil.NAME_DESC_BASIL + CommandTestUtil.QUANTITY_DESC_BASIL
                + CommandTestUtil.INVALID_DESCRIPTION_DESC_TWO + CommandTestUtil.TAG_DESC_VEGETABLE
                + CommandTestUtil.TAG_DESC_SNACK + CommandTestUtil.EXPIRY_DATE_DESC, Description.MESSAGE_CONSTRAINTS);

        // invalid tag
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + CommandTestUtil.NAME_DESC_BASIL + CommandTestUtil.QUANTITY_DESC_BASIL
                + CommandTestUtil.DESCRIPTION_DESC_BASIL + CommandTestUtil.INVALID_TAG_DESC
                + CommandTestUtil.VALID_TAG_SNACK + CommandTestUtil.EXPIRY_DATE_DESC, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + CommandTestUtil.INVALID_NAME_DESC + CommandTestUtil.QUANTITY_DESC_BASIL
                + CommandTestUtil.INVALID_DESCRIPTION_DESC + CommandTestUtil.EXPIRY_DATE_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + CommandTestUtil.PREAMBLE_NON_EMPTY + CommandTestUtil.NAME_DESC_BASIL
                + CommandTestUtil.QUANTITY_DESC_BASIL + CommandTestUtil.DESCRIPTION_DESC_BASIL
                + CommandTestUtil.TAG_DESC_VEGETABLE
                        + CommandTestUtil.TAG_DESC_SNACK + CommandTestUtil.EXPIRY_DATE_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
