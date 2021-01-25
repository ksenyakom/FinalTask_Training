package by.ksu.training.service.validator;

import by.ksu.training.controller.AttrName;
import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Kseniya Oznobishina
 * @Date 24.01.2021
 */
public class ExerciseValidator implements Validator<Exercise> {
    private static final int maxTextLength = 1000;

    @Override
    public Exercise validate(HttpServletRequest request) throws IncorrectFormDataException {
        Exercise exercise = new Exercise();

        String title = request.getParameter(AttrName.TITLE);
        if (title != null && title.length() > 0 && title.length() < 255) {
            exercise.setTitle(title);
        } else {
            throw new IncorrectFormDataException(AttrName.TITLE, title);
        }

        String adjusting = request.getParameter(AttrName.ADJUSTING);
        if (adjusting != null && adjusting.length() > 0 && adjusting.length() < maxTextLength) {
            exercise.setAdjusting(adjusting);
        } else {
            throw new IncorrectFormDataException(AttrName.ADJUSTING, adjusting);
        }

        String mistakes = request.getParameter(AttrName.MISTAKES);
        if (mistakes != null && mistakes.length() > 0 && mistakes.length() < maxTextLength) {
            exercise.setMistakes(mistakes);
        } else {
            throw new IncorrectFormDataException(AttrName.MISTAKES, mistakes);
        }

        String type = request.getParameter(AttrName.EXERCISE_TYPE);
        if (type != null && type.length() > 0 && type.length() < maxTextLength) {
            exercise.setType(type);
        } else {
            throw new IncorrectFormDataException(AttrName.EXERCISE_TYPE, type);
        }


            return exercise;
        }


        @Override
        public Integer validateId (HttpServletRequest request) throws IncorrectFormDataException {
            String stringId = request.getParameter(AttrName.EXERCISE_ID);
            try {
                if (stringId != null) {
                    return Integer.parseInt(stringId);
                } else {
                    throw new IncorrectFormDataException(AttrName.EXERCISE_ID, stringId);
                }
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(AttrName.EXERCISE_ID, stringId);
            }
        }
    }
