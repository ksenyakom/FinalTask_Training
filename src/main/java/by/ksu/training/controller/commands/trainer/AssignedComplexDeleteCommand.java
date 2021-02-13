package by.ksu.training.controller.commands.trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.AssignedTrainerService;
import by.ksu.training.service.validator.AssignedComplexValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Deletes records of AssignedComplex.
 *
 * @Author Kseniya Oznobishina
 * @Date 23.01.2021
 */

public class AssignedComplexDeleteCommand extends TrainerCommand {
    private static Logger logger = LogManager.getLogger(AssignedComplexDeleteCommand.class);

    /**
     * Deletes records of AssignedComplex with id which came in parameter "remove".
     *
     * @return ResponseState
     * @throws PersistentException if any exception occur in service layout.
     * @see by.ksu.training.controller.state.ResponseState
     */

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        EntityValidator<AssignedComplex> validator = new AssignedComplexValidator();
        AssignedComplexService service = factory.getService(AssignedComplexService.class);
        AssignedTrainerService assignedTrainerService = factory.getService(AssignedTrainerService.class);

        int visitorId = validator.validateIntAttr(AttrName.USER_ID, request);
        String parameter = "?" + AttrName.USER_ID + "=" + visitorId;
        ResponseState state = new RedirectState("assigned_complex/list.html" + parameter);
        User visitor = new User(visitorId);
        User trainer = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
        boolean allowed = assignedTrainerService.checkTrainerByVisitor(trainer, visitor);

        if (allowed) {
            List<Integer> listId = validator.validateListId(AttrName.REMOVE, request);
            if (!listId.isEmpty()) {
                for (Integer id : listId) {
                    service.delete(id);
                    logger.debug("Trainer id={} deleted AssignedTrainer with id={}", trainer.getLogin(), id);
                }
                state.getAttributes().put(AttrName.SUCCESS_MESSAGE, "message.success.delete");
            } else {
                state.getAttributes().put(AttrName.WARNING_MAP, validator.getWarningMap());
            }
            return state;
        } else {
            throw new PersistentException(String.format("User %s are not allowed to delete AssignedComplex for visitor %s", trainer.getLogin(), visitor.getId()));
        }
    }
}
