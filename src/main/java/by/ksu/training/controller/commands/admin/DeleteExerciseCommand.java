package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ExerciseService;
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
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
            Validator<Exercise> validator = new ExerciseValidator();
            List<Integer> listId = validator.validateListId(AttrName.REMOVE, request);
            ExerciseService service = factory.getService(ExerciseService.class);
            for (Integer id : listId) {
                service.delete(id);
            }

            request.getSession().setAttribute(AttrName.SUCCESS_MESSAGE, "message.success.delete");
            return new RedirectState("exercise/list.html" );


    }
}
