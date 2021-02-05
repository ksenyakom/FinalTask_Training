package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedTrainerService;
import by.ksu.training.service.validator.AssignedTrainerValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Kseniya Oznobishina
 * @Date 20.01.2021
 */
public class DeleteAssignedTrainerCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(DeleteAssignedTrainerCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
            Validator<AssignedTrainer> validator = new AssignedTrainerValidator();
            List<Integer> listId = validator.validateListId(AttrName.REMOVE, request);

            AssignedTrainerService atService = factory.getService(AssignedTrainerService.class);
            for (Integer id : listId) {
                atService.delete(id);
            }

            request.getSession().setAttribute(AttrName.SUCCESS_MESSAGE, "message.success.delete");
            String action = request.getParameter(AttrName.ACTION);
            String parameter = action == null ? "" : "?" + AttrName.ACTION + "=" + action;
            return new RedirectState("assigned_trainer/list.html" + parameter);

    }
}
