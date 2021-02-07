package by.ksu.training.controller.commands.visitor;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
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

/**
 * Prepares data to show on page for buying new subscription.
 *
 * @Author Kseniya Oznobishina
 * @Date 18.01.2021
 */
public class ShowSubscriptionBuyCommand extends VisitorCommand {
    private static Logger logger = LogManager.getLogger(ShowSubscriptionBuyCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        SubscriptionService service = factory.getService(SubscriptionService.class);
        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);

        Subscription subscription = service.findActiveByUser(user);
        request.setAttribute(AttrName.SUBSCRIPTION, subscription);
        return new ForwardState("subscription/buy.jsp");
    }
}
