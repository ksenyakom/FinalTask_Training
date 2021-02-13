package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ExerciseService;
import by.ksu.training.service.validator.ExerciseValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Prepares data to show on page for exercise edit.
 *
 * @Author Kseniya Oznobishina
 * @Date 25.01.2021
 */
public class ExerciseShowEditPageCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(ExerciseShowEditPageCommand.class);

    /**
     * Prepares data to show on list for exercise edit:
     * exercise data.
     *
     * @throws PersistentException if any exception occur in service layout.
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        EntityValidator<Exercise> validator = new ExerciseValidator();
        ExerciseService exerciseService = factory.getService(ExerciseService.class);
        Integer id = validator.validateId(request);
        Exercise exercise = exerciseService.findById(id);
        List<String> types = exerciseService.findExerciseTypes();

        if (exercise != null) {
            request.setAttribute(AttrName.EXERCISE, exercise);
            request.setAttribute("lst", types);
            String currentPage = request.getParameter("currentPage");
            request.setAttribute("currentPage", currentPage);
            return new ForwardState("exercise/edit.jsp");
        } else {
            logger.warn("Exercise with id={} not found", id);
            ResponseState state = new RedirectState("exercise/list.html");
            state.getAttributes().put(AttrName.WARNING_MAP, Map.of("Error", "message.warning.exercise_not_found"));
            return state;
        }
    }
}
