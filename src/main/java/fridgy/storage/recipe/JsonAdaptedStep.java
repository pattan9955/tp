package fridgy.storage.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import fridgy.model.recipe.Step;

/**
 * Jackson-friendly version of {@link Step}
 */
public class JsonAdaptedStep {

    private final String step;

    /**
     * Constructs a {@code JsonAdaptedStep} with the given {@code step}.
     */
    @JsonCreator
    public JsonAdaptedStep(String step) {
        this.step = step;
    }

    /**
     * Converts a given {@code Step} into this class for Jackson use.
     */
    public JsonAdaptedStep(Step source) {
        step = source.getStep();
    }

    @JsonValue
    public String getStepName() {
        return step;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Step} object.
     */
    public Step toModelType() {
        return new Step(step);
    }
}
