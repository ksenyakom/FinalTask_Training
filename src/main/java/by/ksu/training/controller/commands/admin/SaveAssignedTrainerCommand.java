package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedTrainerService;
import by.ksu.training.service.validator.AssignedTrainerValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Saves assignment of trainer for visitor.
 *
 * @Author Kseniya Oznobishina
 * @Date 19.01.2021
 * @see AssignedTrainer
 */
public class SaveAssignedTrainerCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(SaveAssignedTrainerCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<AssignedTrainer> validator = new AssignedTrainerValidator();
            AssignedTrainer assignedTrainer = validator.validate(request);

            AssignedTrainerService atService = factory.getService(AssignedTrainerService.class);
            atService.save(assignedTrainer);

            request.getSession().setAttribute(AttrName.SUCCESS_MESSAGE, "message.success.trainer_assigned");

            return new RedirectState("assigned_trainer/list.html");
        } catch (IncorrectFormDataException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.WARNING_MESSAGE, e.getMessage());
            return new ForwardState("assigned_trainer/set.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }
}
