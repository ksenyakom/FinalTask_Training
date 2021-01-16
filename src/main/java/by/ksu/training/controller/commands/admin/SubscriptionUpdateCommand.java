package by.ksu.training.controller.commands.admin;

import by.ksu.training.entity.Role;
import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.SubscriptionService;
import by.ksu.training.service.validator.SubscriptionValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @Author Kseniya Oznobishina
 * @Date 16.01.2021
 */
public class SubscriptionUpdateCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(SubscriptionDeleteCommand.class);

    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        Subscription subscription;
        try {
            Validator<Subscription> validator = new SubscriptionValidator();
             subscription = validator.validate(request);
            SubscriptionService service = factory.getService(SubscriptionService.class);
            service.save(subscription);
            request.setAttribute("success_message","Запись успешно обновлена");

//            String action = request.getParameter(ShowAllSubscriptionsCommand.ACTION);
//            if (action != null) {
//                List<Subscription> subscriptionList = action.equalsIgnoreCase(ShowAllSubscriptionsCommand.ALL) ?
//                        service.findAll() :
//                        service.findAllActive();
//
//                request.setAttribute("lst", subscriptionList);
//            }
        } catch(PersistentException | IncorrectFormDataException e){
            logger.error("Exception while update subscription", e);
            request.setAttribute("err_message",e.getMessage());
            return null;
        }

        return new Forward("subscription/list.jsp");
    }

    @Override
    public Set<Role> getAllowedRoles () {
        return null;
        //TODO
    }
}
