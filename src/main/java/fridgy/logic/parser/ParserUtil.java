package fridgy.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fridgy.commons.core.index.Index;
import fridgy.commons.util.StringUtil;
import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.ingredient.Description;
import fridgy.model.ingredient.ExpiryDate;
import fridgy.model.ingredient.Name;
import fridgy.model.ingredient.Quantity;
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
     * Parses {@code String date} into an {@code ExpiryDate} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified date is invalid (not of DD-MM-YYYY format).
     */
    public static ExpiryDate parseExpiry(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!ExpiryDate.isValidExpiry(trimmedDate)) {
            throw new ParseException(ExpiryDate.MESSAGE_CONSTRAINTS);
        }
        return new ExpiryDate(trimmedDate);
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
     * Parses a {@code String quantity} into a {@code Quantity}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code quantity} is invalid.
     */
    public static Quantity parseQuantity(String quantity) throws ParseException {
        requireNonNull(quantity);
        String trimmedQuantity = quantity.trim();
        if (!Quantity.isValidQuantity(trimmedQuantity)) {
            throw new ParseException(Quantity.MESSAGE_CONSTRAINTS);
        }
        return new Quantity(trimmedQuantity);
    }

    /**
     * Parses a {@code String description} into an {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(Optional<String> description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.get().trim();
        if (!Description.isValidDescription(Optional.of(trimmedDescription))) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(Optional.of(trimmedDescription));
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
