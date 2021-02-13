package by.ksu.training.controller.commands.visitor;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.SubscriptionService;
import by.ksu.training.service.validator.NewSubscriptionValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * Saves new subscription.
 *
 * @Author Kseniya Oznobishina
 * @Date 18.01.2021
 */
public class SubscriptionSaveCommand extends VisitorCommand {
    private static Logger logger = LogManager.getLogger(SubscriptionSaveCommand.class);

    private static int[] prices = {100, 150, 200}; //todo move to class PriceService

    /**
     * Saves new subscription. Performs check of begin date to be later
     * then end date of active subscription.
     */

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Subscription subscription;
        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
        SubscriptionService subscriptionService = factory.getService(SubscriptionService.class);
        EntityValidator<Subscription> validator = new NewSubscriptionValidator();
        ResponseState state;
        Subscription activeSubscription=null;
        try {
            activeSubscription = subscriptionService.findActiveByUser(user);
            subscription = validator.validate(request);
            int months = validator.validateIntAttr(AttrName.PERIOD, request);
            subscription.setPrice(new BigDecimal(prices[months - 1]));
            subscription.setVisitor(user);
            // check dates of last subscription

            if (activeSubscription == null || subscription.getBeginDate().isAfter(activeSubscription.getEndDate())) {
                subscriptionService.save(subscription);
                logger.debug("User {} bought subscription for {} months", user.getLogin(), months);
                state = new RedirectState("visitor/subscription.html");
                state.getAttributes().put(AttrName.SUCCESS_MESSAGE, "message.success.subscription_bought");
            } else {
                request.setAttribute(AttrName.WARNING_MESSAGE, "message.warning.new_subscription_must_be_later");
                request.setAttribute(AttrName.SUBSCRIPTION, activeSubscription);
                state = new ForwardState("subscription/buy.jsp");
            }
        } catch (IncorrectFormDataException e) {
            logger.error("User {} was unsuccessfully tried to buy new subscription", user.getLogin(), e);
            request.setAttribute(AttrName.SUBSCRIPTION, activeSubscription);
            request.setAttribute(AttrName.WARNING_MAP, validator.getWarningMap());
            state = new ForwardState("subscription/buy.jsp");

        }
        return state;
    }
}
