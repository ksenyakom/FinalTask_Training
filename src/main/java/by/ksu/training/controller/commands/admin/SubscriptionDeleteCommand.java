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
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @Author Kseniya Oznobishina
 * @Date 14.01.2021
 */
public class SubscriptionDeleteCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(SubscriptionDeleteCommand.class);
    private static final String REMOVE = "remove";

    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        String[] subscriptionsId = request.getParameterValues(REMOVE);

        try {
            SubscriptionService service = factory.getService(SubscriptionService.class);
            for (String stringId : subscriptionsId) {
                int id = Integer.parseInt(stringId);
                service.delete(id);
            }

                String action = request.getParameter(ShowAllSubscriptionsCommand.ACTION);
                if (action != null) {
                    List<Subscription> subscriptionList = action.equalsIgnoreCase(ShowAllSubscriptionsCommand.ALL) ?
                            service.findAll() :
                            service.findAllActive();

                    request.setAttribute("lst", subscriptionList);
                }
            } catch(PersistentException e){
                logger.error("Exception while delete subscription id = {}", Arrays.toString(subscriptionsId), e);
            }

            return new Forward("subscription/list.jsp");
        }

        @Override
        public Set<Role> getAllowedRoles () {
            return null;
            //TODO
        }
    }
