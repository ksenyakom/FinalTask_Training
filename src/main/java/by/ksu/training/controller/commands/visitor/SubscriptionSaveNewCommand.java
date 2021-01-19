package by.ksu.training.controller.commands.visitor;

import by.ksu.training.controller.commands.admin.SubscriptionDeleteCommand;
import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.SubscriptionService;
import by.ksu.training.service.validator.NewSubscriptionValidator;
import by.ksu.training.service.validator.SubscriptionValidator;
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
public class SubscriptionSaveNewCommand extends VisitorCommand{
    private static Logger logger = LogManager.getLogger(SubscriptionSaveNewCommand.class);
    private static final String PERIOD = "period";

    private  static int[] prices = {100, 150, 200}; //todo move to class PriceService
    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        Subscription subscription;
        try {
            Validator<Subscription> validator = new NewSubscriptionValidator();
            subscription = validator.validate(request);

            String period = request.getParameter(PERIOD);
            int months = Integer.parseInt(period);
            subscription.setPrice(new BigDecimal(prices[months-1]));

            User user = (User) request.getSession().getAttribute("authorizedUser");
            subscription.setVisitor(user);

            SubscriptionService service = factory.getService(SubscriptionService.class);
            service.save(subscription);
            request.getSession().setAttribute("success_message","Подписка успешно оформлена"); // опять потеряю сообщение

        } catch(PersistentException | IncorrectFormDataException e){
            logger.error("Exception while update subscription", e);
            request.setAttribute("err_message",e.getMessage());
            return null;
        }

        return new Forward("/visitor/subscription.html",true);
    }

}
