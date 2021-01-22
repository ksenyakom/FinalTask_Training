package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.SubscriptionService;
import by.ksu.training.service.validator.SubscriptionValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * Updates subscription with values came in request.
 *
 * @Author Kseniya Oznobishina
 * @Date 16.01.2021
 * @see  by.ksu.training.entity.Subscription
 */
public class SubscriptionUpdateCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(SubscriptionUpdateCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        Subscription subscription;
        try {
            Validator<Subscription> validator = new SubscriptionValidator();
            subscription = validator.validate(request);
            SubscriptionService service = factory.getService(SubscriptionService.class);
            service.save(subscription);
            request.setAttribute(AttrName.SUCCESS_MESSAGE, "message.success.updated");

            return new RedirectState("subscription/list.html");
        } catch (IncorrectFormDataException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.WARNING_MESSAGE, e.getMessage());
            return new ForwardState("subscription/edit.jsp");
        }catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }
}
