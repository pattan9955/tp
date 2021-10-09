package fridgy.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fridgy.commons.core.index.Index;
import fridgy.commons.util.StringUtil;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.ingredient.Address;
import fridgy.model.ingredient.Email;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Phone;
import fridgy.model.recipe.RecipeIngredient;
import fridgy.model.recipe.Step;
import fridgy.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String name} into a {@code Name}
     */
    public static fridgy.model.recipe.Name parseRecipeName(String name) {
        requireNonNull(name);
        String trimmedName = name.trim();
        return new fridgy.model.recipe.Name(trimmedName);
    }

    /**
     * Parses {@code List<String> ingredients} into a {@code Set<RecipeIngredient>}.
     */
    public static Set<RecipeIngredient> parseIngredients(List<String> ingredients) {
        requireNonNull(ingredients);
        final Set<RecipeIngredient> ingredientSet = new HashSet<>();
        for (String ingredient : ingredients) {
            ingredientSet.add(parseIngredient(ingredient));
        }
        return ingredientSet;
    }

    /**
     * Parses {@code String ingredient} into a {@code RecipeIngredient}.
     */
    public static RecipeIngredient parseIngredient(String ingredient) {
        requireNonNull(ingredient);
        String trimmedIngredient = ingredient.trim();
        return new RecipeIngredient(trimmedIngredient);
    }

    /**
     * Parses {@code List<String> steps} into a {@code List<Step>}.
     */
    public static List<Step> parseSteps(List<String> steps) {
        requireNonNull(steps);
        final ArrayList<Step> stepList = new ArrayList<>();
        for (String step : steps) {
            stepList.add(parseStep(step));
        }
        return stepList;
    }

    /**
     * Parses {@code String step} into a {@code Step}.
     */
    public static Step parseStep(String step) {
        requireNonNull(step);
        String trimmedStep = step.trim();
        return new Step(trimmedStep);
    }
}
