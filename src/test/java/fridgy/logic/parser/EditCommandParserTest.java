package fridgy.logic.parser;

import org.junit.jupiter.api.Test;

import fridgy.commons.core.Messages;
import fridgy.commons.core.index.Index;
import fridgy.logic.commands.CommandTestUtil;
import fridgy.logic.commands.ingredient.EditCommand;
import fridgy.logic.parser.ingredient.EditCommandParser;
import fridgy.model.ingredient.Description;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Quantity;
import fridgy.model.tag.Tag;
import fridgy.testutil.EditIngredientDescriptorBuilder;
import fridgy.testutil.TypicalIndexes;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + CliSyntax.PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + CommandTestUtil.VALID_NAME_ALMOND, MESSAGE_INVALID_FORMAT);

        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + "-5" + CommandTestUtil.NAME_DESC_ALMOND, MESSAGE_INVALID_FORMAT);

        // zero index
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + "0" + CommandTestUtil.NAME_DESC_ALMOND, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + "1" + CommandTestUtil.INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + "1" + CommandTestUtil.INVALID_QUANTITY_DESC, Quantity.MESSAGE_CONSTRAINTS); // invalid quantity
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + "1" + CommandTestUtil.INVALID_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS); // invalid address
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + "1" + CommandTestUtil.INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid quantity followed by valid email
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + "1" + CommandTestUtil.INVALID_QUANTITY_DESC,
                Quantity.MESSAGE_CONSTRAINTS);

        // valid quantity followed by invalid quantity. The test case for invalid quantity followed by valid quantity
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + "1" + CommandTestUtil.QUANTITY_DESC_BASIL + CommandTestUtil.INVALID_QUANTITY_DESC,
                Quantity.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Ingredient} being edited,
        // parsing it together with a valid tag results in error
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + "1" + CommandTestUtil.TAG_DESC_SNACK + CommandTestUtil.TAG_DESC_VEGETABLE + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + "1" + CommandTestUtil.TAG_DESC_SNACK + TAG_EMPTY + CommandTestUtil.TAG_DESC_VEGETABLE,
                Tag.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                + "1" + TAG_EMPTY + CommandTestUtil.TAG_DESC_SNACK + CommandTestUtil.TAG_DESC_VEGETABLE,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT
                        + "1" + CommandTestUtil.INVALID_NAME_DESC
                        + CommandTestUtil.VALID_DESCRIPTION_ALMOND + CommandTestUtil.VALID_QUANTITY_ALMOND,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_INGREDIENT;
        String userInput = CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + targetIndex.getOneBased()
                + CommandTestUtil.QUANTITY_DESC_BASIL + CommandTestUtil.TAG_DESC_VEGETABLE
                + CommandTestUtil.DESCRIPTION_DESC_ALMOND
                + CommandTestUtil.NAME_DESC_ALMOND + CommandTestUtil.TAG_DESC_SNACK;

        EditCommand.EditIngredientDescriptor descriptor =
                new EditIngredientDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_ALMOND)
                        .withQuantity(CommandTestUtil.VALID_QUANTITY_BASIL)
                        .withDescription(CommandTestUtil.VALID_DESCRIPTION_ALMOND)
                        .withTags(CommandTestUtil.VALID_TAG_VEGETABLE, CommandTestUtil.VALID_TAG_SNACK).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_INGREDIENT;
        String userInput = CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + targetIndex.getOneBased()
                + CommandTestUtil.QUANTITY_DESC_BASIL;

        EditCommand.EditIngredientDescriptor descriptor =
                new EditIngredientDescriptorBuilder()
                        .withQuantity(CommandTestUtil.VALID_QUANTITY_BASIL)
                        .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = TypicalIndexes.INDEX_THIRD_INGREDIENT;
        String userInput = CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + targetIndex.getOneBased()
                + CommandTestUtil.NAME_DESC_ALMOND;
        EditCommand.EditIngredientDescriptor descriptor =
                new EditIngredientDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_ALMOND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // quantity
        userInput = CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + targetIndex.getOneBased()
                + CommandTestUtil.QUANTITY_DESC_ALMOND;
        descriptor = new EditIngredientDescriptorBuilder().withQuantity(CommandTestUtil.VALID_QUANTITY_ALMOND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + targetIndex.getOneBased()
                + CommandTestUtil.DESCRIPTION_DESC_ALMOND;
        descriptor = new EditIngredientDescriptorBuilder()
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_ALMOND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + targetIndex.getOneBased()
                + CommandTestUtil.TAG_DESC_SNACK;
        descriptor = new EditIngredientDescriptorBuilder().withTags(CommandTestUtil.VALID_TAG_SNACK).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_INGREDIENT;
        String userInput = CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + targetIndex.getOneBased()
                + CommandTestUtil.QUANTITY_DESC_ALMOND + CommandTestUtil.DESCRIPTION_DESC_ALMOND
                + CommandTestUtil.TAG_DESC_SNACK
                + CommandTestUtil.QUANTITY_DESC_ALMOND + CommandTestUtil.DESCRIPTION_DESC_ALMOND
                + CommandTestUtil.TAG_DESC_SNACK
                + CommandTestUtil.QUANTITY_DESC_BASIL + CommandTestUtil.DESCRIPTION_DESC_BASIL
                + CommandTestUtil.TAG_DESC_VEGETABLE;

        EditCommand.EditIngredientDescriptor descriptor =
                new EditIngredientDescriptorBuilder()
                        .withQuantity(CommandTestUtil.VALID_QUANTITY_BASIL)
                        .withDescription(CommandTestUtil.VALID_DESCRIPTION_BASIL)
                        .withTags(CommandTestUtil.VALID_TAG_SNACK,
                                CommandTestUtil.VALID_TAG_VEGETABLE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = TypicalIndexes.INDEX_FIRST_INGREDIENT;
        String userInput = CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + targetIndex.getOneBased()
                + CommandTestUtil.INVALID_QUANTITY_DESC + CommandTestUtil.QUANTITY_DESC_BASIL;
        EditCommand.EditIngredientDescriptor descriptor =
                new EditIngredientDescriptorBuilder().withQuantity(CommandTestUtil.VALID_QUANTITY_BASIL).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + targetIndex.getOneBased()
                + CommandTestUtil.INVALID_QUANTITY_DESC
                + CommandTestUtil.DESCRIPTION_DESC_BASIL + CommandTestUtil.QUANTITY_DESC_BASIL;
        descriptor = new EditIngredientDescriptorBuilder()
                .withQuantity(CommandTestUtil.VALID_QUANTITY_BASIL)
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_BASIL).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_INGREDIENT;
        String userInput = CommandTestUtil.VALID_INGREDIENT_ARGUMENT_FORMAT + targetIndex.getOneBased() + TAG_EMPTY;

        EditCommand.EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }
}
