package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedTrainerService;
import by.ksu.training.service.validator.AssignedTrainerValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Saves assignment of trainer for visitor.
 *
 * @Author Kseniya Oznobishina
 * @Date 19.01.2021
 */
public class AssignedTrainerSaveCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(AssignedTrainerSaveCommand.class);

    /**
     * Saves assignment of trainer for visitor.
     *
     * @throws PersistentException if any exception occur in service layout.
     * @see by.ksu.training.entity.AssignedTrainer
     */

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        EntityValidator<AssignedTrainer> validator = new AssignedTrainerValidator();
        AssignedTrainerService atService = factory.getService(AssignedTrainerService.class);

        String action = request.getParameter(AttrName.ACTION);
        String parameter = "?" + AttrName.ACTION + "=" + action;
        ResponseState state = new RedirectState("assigned_trainer/list.html" + parameter);

        try {
            AssignedTrainer assignedTrainer = validator.validate(request);
            Integer visitorId = validator.validateIntAttr(AttrName.VISITOR_ID, request);
            Integer trainerId = validator.validateIntAttr(AttrName.TRAINER_ID, request);
            assignedTrainer.setTrainer(new User(trainerId));
            assignedTrainer.setVisitor(new User(visitorId));
            atService.save(assignedTrainer);
            logger.debug("Admin assigned trainer id={} to user id={}", trainerId, visitorId);
            state.getAttributes().put(AttrName.SUCCESS_MESSAGE, "message.success.trainer_assigned");
            return state;
        } catch (IncorrectFormDataException e) {
            //never thrown
        }
        return state;
    }
}
