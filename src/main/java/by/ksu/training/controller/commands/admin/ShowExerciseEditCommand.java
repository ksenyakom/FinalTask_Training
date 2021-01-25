package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Exercise;
import by.ksu.training.entity.Person;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ExerciseService;
import by.ksu.training.service.PersonService;
import by.ksu.training.service.validator.ExerciseValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Kseniya Oznobishina
 * @Date 25.01.2021
 */
public class ShowExerciseEditCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(AddExerciseCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<Exercise> validator = new ExerciseValidator();
            Integer id = validator.validateId(request);

            ExerciseService exerciseService = factory.getService(ExerciseService.class);
            Exercise exercise = exerciseService.findById(id);
            List<String> types = exerciseService.findExerciseTypes();

            request.setAttribute("lst",types);
            if (exercise != null) {
                request.setAttribute(AttrName.EXERCISE, exercise);

                return new ForwardState("exercise/edit.jsp");
            } else {
                request.setAttribute(AttrName.WARNING_MESSAGE, "Incorrect parameters request");
                return new ForwardState("exercise/list.jsp");
            }

        } catch (PersistentException | IncorrectFormDataException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE,e.getMessage());
            return new ErrorState();
        }
    }
}
