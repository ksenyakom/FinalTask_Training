package by.ksu.training.service.validator;

import by.ksu.training.controller.AttrName;
import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author Kseniya Oznobishina
 * @Date 24.01.2021
 */
public class ExerciseValidator extends BaseValidator<Exercise> implements Validator<Exercise> {


    @Override
    public Map<String, String> getWarningMap() {
        return warningMap;
    }

    @Override
    public Exercise getInvalid() {
        return invalid;
    }

    @Override
    public Exercise validate(HttpServletRequest request) throws IncorrectFormDataException {
        final int maxTextLength = 1000;
        final int minTextLength = 1;
        final int maxTitleLength = 255;
        final int minTitleLength = 3;
        Exercise exercise = new Exercise();

        String title = request.getParameter(AttrName.TITLE);
        title = validateText(AttrName.TITLE, title, minTitleLength, maxTitleLength);
        exercise.setTitle(title);

        String adjusting = request.getParameter(AttrName.ADJUSTING);
        adjusting = validateText(AttrName.ADJUSTING, adjusting, minTextLength, maxTextLength);
        exercise.setAdjusting(adjusting);

        String mistakes = request.getParameter(AttrName.MISTAKES);
        mistakes = validateText(AttrName.MISTAKES, mistakes, minTextLength, maxTextLength);
        exercise.setMistakes(mistakes);

        String type = request.getParameter(AttrName.EXERCISE_TYPE);
        type = validateText(AttrName.EXERCISE_TYPE, type, minTextLength, maxTextLength);
        exercise.setType(type);

        if (warningMap != null) {
            invalid = exercise;
            throw new IncorrectFormDataException(warningMap.keySet().toString(), warningMap.values().toString());
        }
        return exercise;
    }

    private String validateText(String paramName, String text, int minLength, int maxLength) {
        if (text != null) {
            text = text.replaceAll("<", "&lt;").replaceAll(">", "&gt;").trim();
        }
        if (text == null || text.isEmpty()) {
            addWarning("attr." + paramName, "message.warning.emptyParameter");
        } else {
            if (text.length() < minLength) {
                addWarning("attr." + paramName, "message.warning.shortParameter");
            } else if (text.length() > maxLength) {
                addWarning("attr." + paramName, "message.warning.longParameter");
                return text.substring(0, maxLength);
            }
        }
        return text;
    }

    @Override
    public Integer validateId(HttpServletRequest request) throws PersistentException {
        String stringId = request.getParameter(AttrName.EXERCISE_ID);
        if (stringId == null || stringId.isEmpty()) {
            throw new PersistentException(String.format("Record id was not found: %s", AttrName.EXERCISE_ID));
        }
        try {
            return Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            throw new PersistentException(String.format("Record id cannot be read: %s, was found %s", AttrName.EXERCISE_ID, stringId));
        }
    }
}
