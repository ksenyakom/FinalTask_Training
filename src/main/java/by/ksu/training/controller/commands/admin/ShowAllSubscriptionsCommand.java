package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.SubscriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * Finds subscriptions according user choice: all or only active.
 *
 * @Author Kseniya Oznobishina
 * @Date 14.01.2021
 * @see Subscription
 */
public class ShowAllSubscriptionsCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(ShowAllSubscriptionsCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            String action = request.getParameter(AttrName.ACTION);
            SubscriptionService service = factory.getService(SubscriptionService.class);
            if (action != null) {
                List<Subscription> subscriptionList = action.equalsIgnoreCase(AttrName.ALL)
                        ? service.findAll()
                        : service.findAllActive();

                request.setAttribute("lst", subscriptionList);
            }
            return new ForwardState("subscription/list.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }
}
