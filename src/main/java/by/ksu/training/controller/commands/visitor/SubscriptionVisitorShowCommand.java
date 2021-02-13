package by.ksu.training.controller.commands.visitor;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.SubscriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Prepares data to show on page of visitor's subscriptions.
 * @Author Kseniya Oznobishina
 * @Date 18.01.2021
 */
public class SubscriptionVisitorShowCommand extends VisitorCommand {
    private static Logger logger = LogManager.getLogger(SubscriptionVisitorShowCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        SubscriptionService service = factory.getService(SubscriptionService.class);
        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);

        List<Subscription> subscriptionList = service.findByUser(user);
        request.setAttribute("lst", subscriptionList);

        return new ForwardState("visitor/subscription.jsp");
    }
}
