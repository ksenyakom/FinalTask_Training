package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.commands.trainer.DeleteAssignedComplexCommand;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.ExerciseService;
import by.ksu.training.service.validator.AssignedComplexValidator;
import by.ksu.training.service.validator.ExerciseValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Kseniya Oznobishina
 * @Date 24.01.2021
 */
public class DeleteExerciseCommand extends AdminCommand{
    private static Logger logger = LogManager.getLogger(DeleteExerciseCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<Exercise> validator = new ExerciseValidator();
            List<Integer> listId = validator.validateRemoveId(request);
            ExerciseService service = factory.getService(ExerciseService.class);
            for (Integer id : listId) {
                service.delete(id);
            }

            request.getSession().setAttribute(AttrName.SUCCESS_MESSAGE, "message.success.delete");
            return new RedirectState("exercise/list.html" );

        } catch (IncorrectFormDataException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.WARNING_MESSAGE, e.getMessage());
            return new ForwardState("exercise/list.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }
}
