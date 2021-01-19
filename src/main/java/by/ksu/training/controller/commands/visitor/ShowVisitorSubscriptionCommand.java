package by.ksu.training.controller.commands.visitor;

import by.ksu.training.controller.commands.admin.ShowAllSubscriptionsCommand;
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
 * @Author Kseniya Oznobishina
 * @Date 18.01.2021
 */
public class ShowVisitorSubscriptionCommand extends VisitorCommand {
    private static Logger logger = LogManager.getLogger(ShowVisitorSubscriptionCommand.class);

    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            SubscriptionService service = factory.getService(SubscriptionService.class);
            User user = (User) request.getSession().getAttribute("authorizedUser");
            List<Subscription> subscriptionList = service.findByUser(user);

            request.setAttribute("lst", subscriptionList);
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute("err_message", e.getMessage());
            return null;
        }
        return new Forward("visitor/subscription.jsp");
    }
}
