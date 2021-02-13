package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.SubscriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Prepare data to show on page with all subscriptions.
 *
 * @Author Kseniya Oznobishina
 * @Date 14.01.2021
 */
public class SubscriptionsShowAllCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(SubscriptionsShowAllCommand.class);

    /**
     * Finds subscriptions according user choice: all or only active.
     *
     * @throws PersistentException if any exception occur in service layout.
     * @see Subscription
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        String action = request.getParameter(AttrName.ACTION);
        SubscriptionService service = factory.getService(SubscriptionService.class);
        if (action != null) {
            List<Subscription> subscriptionList = action.equalsIgnoreCase(AttrName.ALL)
                    ? service.findAll()
                    : service.findAllActive();

            request.setAttribute("lst", subscriptionList);
            request.setAttribute(AttrName.ACTION, action);
        }
        return new ForwardState("subscription/list.jsp");
    }
}
