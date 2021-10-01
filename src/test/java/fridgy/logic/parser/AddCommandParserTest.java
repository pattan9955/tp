package fridgy.logic.parser;

import fridgy.commons.core.Messages;
import fridgy.logic.commands.AddCommand;
import fridgy.logic.commands.CommandTestUtil;
import fridgy.model.tag.Tag;
import fridgy.testutil.IngredientBuilder;
import fridgy.testutil.TypicalIngredients;
import org.junit.jupiter.api.Test;

import fridgy.model.ingredient.Description;
import fridgy.model.ingredient.Email;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Ingredient;
import fridgy.model.ingredient.Quantity;

import java.util.Optional;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Ingredient expectedIngredient = new IngredientBuilder(TypicalIngredients.BOB).withTags(CommandTestUtil.VALID_TAG_FRIEND).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.QUANTITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.DESCRIPTION_DESC_BOB + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedIngredient));

        // multiple names - last name accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.QUANTITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.DESCRIPTION_DESC_BOB + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedIngredient));

        // multiple quantities - last quantity accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.QUANTITY_DESC_AMY + CommandTestUtil.QUANTITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.DESCRIPTION_DESC_BOB + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedIngredient));

        // multiple emails - last email accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.QUANTITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_AMY + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.DESCRIPTION_DESC_BOB + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedIngredient));

        // multiple descriptions - last description accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.QUANTITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.DESCRIPTION_DESC_AMY
                + CommandTestUtil.DESCRIPTION_DESC_BOB + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedIngredient));

        // multiple tags - all accepted
        Ingredient expectedIngredientMultipleTags = new IngredientBuilder(TypicalIngredients.BOB).withTags(CommandTestUtil.VALID_TAG_FRIEND, CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.QUANTITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.DESCRIPTION_DESC_BOB
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedIngredientMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Ingredient expectedIngredient = new IngredientBuilder(TypicalIngredients.AMY).withTags().build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.QUANTITY_DESC_AMY + CommandTestUtil.EMAIL_DESC_AMY + CommandTestUtil.DESCRIPTION_DESC_AMY,
                new AddCommand(expectedIngredient));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB + CommandTestUtil.QUANTITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.DESCRIPTION_DESC_BOB,
                expectedMessage);

        // missing quantity prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.VALID_QUANTITY_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.DESCRIPTION_DESC_BOB,
                expectedMessage);

        // missing email prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.QUANTITY_DESC_BOB + CommandTestUtil.VALID_EMAIL_BOB + CommandTestUtil.DESCRIPTION_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB + CommandTestUtil.VALID_QUANTITY_BOB + CommandTestUtil.VALID_EMAIL_BOB + CommandTestUtil.VALID_DESCRIPTION_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC + CommandTestUtil.QUANTITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.DESCRIPTION_DESC_BOB
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid quantity
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.INVALID_QUANTITY_DESC + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.DESCRIPTION_DESC_BOB
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, Quantity.MESSAGE_CONSTRAINTS);

        // invalid email
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.QUANTITY_DESC_BOB + CommandTestUtil.INVALID_EMAIL_DESC + CommandTestUtil.DESCRIPTION_DESC_BOB
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid description
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.QUANTITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.INVALID_DESCRIPTION_DESC
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, Description.MESSAGE_CONSTRAINTS);

        // invalid description
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.QUANTITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.INVALID_DESCRIPTION_DESC_TWO
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, Description.MESSAGE_CONSTRAINTS);

        // invalid tag
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.QUANTITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.DESCRIPTION_DESC_BOB
                + CommandTestUtil.INVALID_TAG_DESC + CommandTestUtil.VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC + CommandTestUtil.QUANTITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.INVALID_DESCRIPTION_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.QUANTITY_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.DESCRIPTION_DESC_BOB + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
