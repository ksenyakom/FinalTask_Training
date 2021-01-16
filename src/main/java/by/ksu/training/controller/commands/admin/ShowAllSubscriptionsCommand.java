package by.ksu.training.controller.commands.admin;

import by.ksu.training.entity.Role;
import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.SubscriptionService;
import by.ksu.training.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author Kseniya Oznobishina
 * @Date 14.01.2021
 */
public class ShowAllSubscriptionsCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(ShowAllSubscriptionsCommand.class);
    public static final String ACTION = "action";
    public static final String ALL = "all";

    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            String action = request.getParameter(ACTION);
            SubscriptionService service = factory.getService(SubscriptionService.class);
            if (action != null) {
                List<Subscription> subscriptionList = action.equalsIgnoreCase(ALL)
                        ? service.findAll()
                        : service.findAllActive();

                request.setAttribute("lst", subscriptionList);
            }
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute("err_message",e.getMessage());
            return null;
        }
        return new Forward("subscription/list.jsp");
    }

    @Override
    public Set<Role> getAllowedRoles() {
        return null; //TODO
    }
}
