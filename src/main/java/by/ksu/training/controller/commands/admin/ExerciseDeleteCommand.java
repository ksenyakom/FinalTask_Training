package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
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

/**
 * Deletes records of Exercises.
 *
 * @Author Kseniya Oznobishina
 * @Date 24.01.2021
 */
public class ExerciseDeleteCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(ExerciseDeleteCommand.class);

    /**
     * Deletes records of Exercises with id which came in parameter "remove".
     *
     * @return ResponseState
     * @throws PersistentException if any exception occur in service layout.
     * @see by.ksu.training.controller.state.ResponseState
     */

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        EntityValidator<Exercise> validator = new ExerciseValidator();
        ExerciseService service = factory.getService(ExerciseService.class);
        ResponseState state = new RedirectState("exercise/list.html");
        String currentPage = request.getParameter("currentPage");
        state.getAttributes().put("currentPage", currentPage);

        List<Integer> listId = validator.validateListId(AttrName.REMOVE, request);
        if (!listId.isEmpty()) {
            for (Integer id : listId) {
                service.delete(id);
                logger.debug("Admin deleted Exercise with id={}", id);
            }
            state.getAttributes().put(AttrName.SUCCESS_MESSAGE, "message.success.delete");
        } else {
            state.getAttributes().put(AttrName.WARNING_MAP, validator.getWarningMap());
        }
        return state;
    }
}
