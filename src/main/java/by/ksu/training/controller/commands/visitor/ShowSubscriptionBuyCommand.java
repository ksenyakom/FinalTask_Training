package by.ksu.training.controller.commands.visitor;

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
 * @Author Kseniya Oznobishina
 * @Date 18.01.2021
 */
public class ShowSubscriptionBuyCommand extends VisitorCommand {
    private static Logger logger = LogManager.getLogger(ShowSubscriptionBuyCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            SubscriptionService service = factory.getService(SubscriptionService.class);
            User user = (User) request.getSession().getAttribute("authorizedUser");
            Subscription subscription = service.findActiveByUser(user);

            request.setAttribute("subscription", subscription);

        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute("err_message", e.getMessage());
            return null;
        }
        return new ResponseState("subscription/buy.jsp");
    }
}
