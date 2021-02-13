package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.SubscriptionService;
import by.ksu.training.service.validator.SubscriptionValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Updates subscription with values came in request.
 *
 * @Author Kseniya Oznobishina
 * @Date 16.01.2021
 * @see by.ksu.training.entity.Subscription
 */
public class SubscriptionUpdateCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(SubscriptionUpdateCommand.class);

    /**
     * Updates subscription with values came in request.
     *
     * @throws PersistentException if any exception occur in service layout.
     * @see by.ksu.training.entity.Subscription
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Subscription subscription;
        Integer subscriptionId = null;
        Integer visitorId = null;
        SubscriptionService service = factory.getService(SubscriptionService.class);
        EntityValidator<Subscription> validator = new SubscriptionValidator();

        try {
            subscriptionId = validator.validateIntAttr(AttrName.SUBSCRIPTION_ID, request);
            visitorId = validator.validateIntAttr(AttrName.VISITOR_ID, request);
            subscription = validator.validate(request);
            subscription.setVisitor(new User(visitorId));
            subscription.setId(subscriptionId);

            service.save(subscription);
            logger.debug("Admin updated subscription: {} ", subscription);

            String action = request.getParameter(AttrName.ACTION);
            String parameter = "?" + AttrName.ACTION + "=" + action;
            ResponseState state = new RedirectState("subscription/list.html" + parameter);
            state.getAttributes().put(AttrName.SUCCESS_MESSAGE, "message.success.updated");
            return state;
        } catch (IncorrectFormDataException e) {
            logger.debug("User entered invalid data while updating subscription.", e);
            String action = request.getParameter(AttrName.ACTION);
            request.setAttribute(AttrName.ACTION, action);
            request.setAttribute(AttrName.WARNING_MAP, validator.getWarningMap());
            subscription = validator.getInvalid();
            subscription.setVisitor(new User(visitorId));
            subscription.setId(subscriptionId);

            request.setAttribute(AttrName.SUBSCRIPTION, subscription);
            return new ForwardState("subscription/edit.jsp");
        }
    }
}
