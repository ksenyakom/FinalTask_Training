package by.ksu.training.controller.commands.admin_and_trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.commands.admin.AdminCommand;
import by.ksu.training.controller.commands.authorized_user.ShowExecuteComplexCommand;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ExerciseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Kseniya Oznobishina
 * @Date 23.01.2021
 */
public class ShowExerciseListCommand extends AdminAndTrainerCommand {
    private static Logger logger = LogManager.getLogger(ShowExerciseListCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            ExerciseService exerciseService = factory.getService(ExerciseService.class);
            List<Exercise> exerciseList = exerciseService.findAll();

            request.setAttribute("lst", exerciseList);

            return new ForwardState("exercise/list.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }
}
