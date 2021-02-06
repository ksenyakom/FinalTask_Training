package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.FileTooBigException;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ExerciseService;
import by.ksu.training.service.validator.ExerciseFileValidator;
import by.ksu.training.service.validator.ExerciseValidator;
import by.ksu.training.service.validator.FileValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Updates exercise.
 *
 * @Author Kseniya Oznobishina
 * @Date 25.01.2021
 */
public class UpdateExerciseCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(UpdateExerciseCommand.class);

    /**
     * Updates exercise with id (parameter exercise_id) with values came with request.
     *
     * @throws PersistentException if any exception occur in service layout.
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Validator<Exercise> validator = new ExerciseValidator();
        FileValidator fileValidator = new ExerciseFileValidator();
        ResponseState state =null;
        Exercise exercise = null;

        try {
            exercise = validator.validate(request);
            exercise.setId(validator.validateIntAttr(AttrName.EXERCISE_ID,request));
            List<String> paths = fileValidator.validateUpdate(request);
            if (!paths.isEmpty()) {
                exercise.setPicturePath(paths.get(0));
                exercise.setAudioPath(paths.get(1));
                ExerciseService exerciseService = factory.getService(ExerciseService.class);
                exerciseService.save(exercise);

                logger.debug("Admin update new exercise id = {}", exercise.getId());
                state = new RedirectState("exercise/list.html");
                state.getAttributes().put(AttrName.SUCCESS_MESSAGE, "message.success.updated");
                return state;
            } else {
                state =  new RedirectState("exercise/edit.html");
                state.getAttributes().put(AttrName.WARNING_MAP, Map.of("attr.picture", "message.warning.no_picture_chosen"));
            }

        } catch (IncorrectFormDataException e) {
            Map<String, String> warningMap = validator.getWarningMap();
            logger.debug("User entered invalid data while updating exercise: {}", e);

            state =  new RedirectState("exercise/edit.html");
            exercise = validator.getInvalid();
            state.getAttributes().put(AttrName.WARNING_MAP, warningMap);
        } catch (FileTooBigException e) { //TODO поразбираться еще
            logger.debug("User tried to load too big file while updating exercise", e);
            state =  new RedirectState("exercise/edit.html");
            state.getAttributes().put(AttrName.WARNING_MAP, Map.of("attr.picture", "message.warning.file_too_big"));
        }
        state.getAttributes().put(AttrName.EXERCISE,exercise);
        return state;
    }
}