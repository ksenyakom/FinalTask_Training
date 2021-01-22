package by.ksu.training.controller.commands.visitor;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.SubscriptionService;
import by.ksu.training.service.validator.NewSubscriptionValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * @Author Kseniya Oznobishina
 * @Date 18.01.2021
 */
public class SaveNewSubscriptionCommand extends VisitorCommand{
    private static Logger logger = LogManager.getLogger(SaveNewSubscriptionCommand.class);

    private  static int[] prices = {100, 150, 200}; //todo move to class PriceService

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        Subscription subscription;
        try {
            Validator<Subscription> validator = new NewSubscriptionValidator();
            subscription = validator.validate(request);

            String period = request.getParameter(AttrName.PERIOD);
            int months = Integer.parseInt(period);
            subscription.setPrice(new BigDecimal(prices[months-1]));

            User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
            subscription.setVisitor(user);

            SubscriptionService service = factory.getService(SubscriptionService.class);
            service.save(subscription);
            request.getSession().setAttribute(AttrName.SUCCESS_MESSAGE,"message.success.subscription_bought");
            return new RedirectState("visitor/subscription.html");
        } catch(PersistentException | IncorrectFormDataException e){
            logger.error("Exception while buying subscription", e);
            request.setAttribute(AttrName.ERROR_MESSAGE,e.getMessage());
            return new ErrorState();
        }


    }

}
