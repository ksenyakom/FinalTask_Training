package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
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
 * @Author Kseniya Oznobishina
 * @Date 24.01.2021
 */
public class ShowExerciseAddPageCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(ShowExerciseAddPageCommand.class);
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            ExerciseService service = factory.getService(ExerciseService.class);
            List<String> types = service.findExerciseTypes();

            request.setAttribute("lst",types);

            return new ForwardState("exercise/add.jsp");
        }  catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.WARNING_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }
}
