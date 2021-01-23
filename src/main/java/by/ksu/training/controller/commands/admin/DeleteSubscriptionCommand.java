package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
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
import java.util.List;

/**
 * Deletes subscriptions with id in parameter REMOVE.
 *
 * @Author Kseniya Oznobishina
 * @Date 14.01.2021
 * @see Subscription
 */
public class DeleteSubscriptionCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(DeleteSubscriptionCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<Subscription> validator = new SubscriptionValidator();
            List<Integer> listId = validator.validateRemoveId(request);
            SubscriptionService service = factory.getService(SubscriptionService.class);
            for (Integer id : listId) {
                service.delete(id);
            }

            request.getSession().setAttribute(AttrName.SUCCESS_MESSAGE, "message.success.delete");
            String action = request.getParameter(AttrName.ACTION);
            String parameter = action == null ? "" : "?" + AttrName.ACTION + "=" + action;
            return new RedirectState("subscription/list.html" + parameter);

        } catch (IncorrectFormDataException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.WARNING_MESSAGE, e.getMessage());
            return new ForwardState("subscription/list.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }
}
