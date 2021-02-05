package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.FileTooBigException;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ExerciseService;
import by.ksu.training.service.validator.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Adds exercise in base.
 *
 * @Author Kseniya Oznobishina
 * @Date 24.01.2021
 */
public class AddExerciseCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(AddExerciseCommand.class);

    /**
     * Adds exercise in base.
     *
     * @throws PersistentException - if any exception occur while performing command.
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Exercise exercise = null;
        Validator<Exercise> validator = new ExerciseValidator();
        FileValidator fileValidator = new ExerciseFileValidator();
        ResponseState state =null;

        try {
            exercise = validator.validate(request);
            List<String> paths = fileValidator.validateNew(request);
            if (!paths.isEmpty()) {
                exercise.setPicturePath(paths.get(0));
                exercise.setAudioPath(paths.get(1));
                ExerciseService exerciseService = factory.getService(ExerciseService.class);
                exerciseService.save(exercise);

                logger.debug("Admin created new exercise id = {}", exercise.getId());
                state = new RedirectState("exercise/list.html");
                state.getAttributes().put(AttrName.SUCCESS_MESSAGE, "message.success.exercise_creation");
                return state;
            } else {
                state =  new RedirectState("exercise/add.html");
                state.getAttributes().put(AttrName.WARNING_MAP, Map.of("attr.picture", "message.warning.no_picture_chosen"));
            }
        } catch (IncorrectFormDataException e) {
            logger.debug("User entered invalid data",  e);

            state =  new RedirectState("exercise/add.html");
            exercise = validator.getInvalid();
            state.getAttributes().put(AttrName.WARNING_MAP, validator.getWarningMap());
        } catch (FileTooBigException e) {
            logger.debug("User tried to load too big file", e);
            state =  new RedirectState("exercise/add.html");
            state.getAttributes().put(AttrName.WARNING_MAP, Map.of("attr.picture", "message.warning.file_too_big"));
        }

        state.getAttributes().put(AttrName.EXERCISE,exercise);
        return state;
    }
}