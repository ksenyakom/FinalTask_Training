package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ExerciseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Prepares data to show on page for exercise adding.
 *
 * @Author Kseniya Oznobishina
 * @Date 24.01.2021
 */
public class ExerciseShowAddPageCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(ExerciseShowAddPageCommand.class);

    /**
     * Prepares data to show on list for exercise adding:
     * list of exercise types.
     *
     * @throws PersistentException if any exception occur in service layout.
     */

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        ExerciseService service = factory.getService(ExerciseService.class);

        List<String> types = service.findExerciseTypes();
        request.setAttribute("lst", types);
        return new ForwardState("exercise/add.jsp");
    }
}
