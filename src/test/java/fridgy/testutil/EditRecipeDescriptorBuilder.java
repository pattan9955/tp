package fridgy.testutil;

import static fridgy.logic.commands.recipe.EditRecipeCommand.EditRecipeDescriptor;
import static fridgy.logic.parser.ParserUtil.parseBaseIngredient;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fridgy.logic.parser.exceptions.ParseException;
import fridgy.model.ingredient.BaseIngredient;
import fridgy.model.recipe.Name;
import fridgy.model.recipe.Recipe;
import fridgy.model.recipe.Step;

public class EditRecipeDescriptorBuilder {

    private EditRecipeDescriptor descriptor;

    public EditRecipeDescriptorBuilder() {
        this.descriptor = new EditRecipeDescriptor();
    }

    public EditRecipeDescriptorBuilder(EditRecipeDescriptor descriptor) {
        this.descriptor = new EditRecipeDescriptor(descriptor);
    }

    /** Returns an {@code EditRecipeDescriptor} with fields containing {@code recipe}'s details */
    public EditRecipeDescriptorBuilder(Recipe recipe) {
        descriptor = new EditRecipeDescriptor();
        descriptor.setName(recipe.getName());
        descriptor.setIngredients(recipe.getIngredients());
        descriptor.setDescription(recipe.getDescription().get());
        descriptor.setSteps(recipe.getSteps());
    }

    /**
     * Sets the name of the {@code EditRecipeDescriptor} that we are building to {@code String}.
     */
    public EditRecipeDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the description of the {@code EditRecipeDescriptor} that we are building to {@code String}.
     */
    public EditRecipeDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(description);
        return this;
    }

    /**
     * Sets the steps of the {@code EditRecipeDescriptor} that we are building to {@code String[]}.
     */
    public EditRecipeDescriptorBuilder withSteps(String... steps) {
        List<Step> stepList = Stream.of(steps).map(Step::new).collect(Collectors.toList());
        descriptor.setSteps(stepList);
        return this;
    }

    /**
     * Sets the ingredients of the {@code EditRecipeDescriptor} that we are building to {@code String[]}.
     */
    public EditRecipeDescriptorBuilder withIngredients(String... ingredients) {
        Set<BaseIngredient> ingredientSet = Stream
                .of(ingredients)
                .map(x -> {
                    try {
                        return !x.equals("") ? parseBaseIngredient(x) : null;
                    } catch (ParseException pe) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        descriptor.setIngredients(ingredientSet);
        return this;
    }

    public EditRecipeDescriptor build() {
        return descriptor;
    }
}
