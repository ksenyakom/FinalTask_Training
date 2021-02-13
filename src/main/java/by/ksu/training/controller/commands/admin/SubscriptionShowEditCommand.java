package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.SubscriptionService;
import by.ksu.training.service.validator.SubscriptionValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Shows page for edit subscription.
 *
 * @Author Kseniya Oznobishina
 * @Date 16.01.2021
 */
public class SubscriptionShowEditCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(SubscriptionShowEditCommand.class);

    /**
     * Shows page for edit subscription with id, written in parameter EDIT_ID.
     *
     * @throws PersistentException if any exception occur in service layout.
     * @see by.ksu.training.entity.Subscription
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        SubscriptionService service = factory.getService(SubscriptionService.class);
        EntityValidator<Subscription> validator = new SubscriptionValidator();
        int id = validator.validateIntAttr(AttrName.EDIT_ID, request);
        Subscription subscription = service.findById(id);
        if (subscription != null) {
            request.setAttribute(AttrName.SUBSCRIPTION, subscription);
            String action = request.getParameter(AttrName.ACTION);
            request.setAttribute(AttrName.ACTION, action);

            return new ForwardState("subscription/edit.jsp");
        } else {
            logger.warn("Subscription with id={} not found", id);
            ResponseState state = new RedirectState("subscription/list.html");
            state.getAttributes().put(AttrName.WARNING_MAP, Map.of("Error", "message.warning.subscription_not_found"));
            return state;
        }
    }
}
