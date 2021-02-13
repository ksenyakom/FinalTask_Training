package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.SubscriptionService;
import by.ksu.training.service.search.SearchSubscription;
import by.ksu.training.service.search.Specification;
import by.ksu.training.service.validator.ParameterValidator;
import by.ksu.training.service.validator.ParameterValidatorImpl;
import by.ksu.training.service.validator.SubscriptionValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @Author Kseniya Oznobishina
 * @Date 10.02.2021
 */
public class ReportSubscriptionShowPageCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(ReportSubscriptionShowPageCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        SubscriptionService subscriptionService = factory.getService(SubscriptionService.class);
        ParameterValidator validator = new ParameterValidatorImpl();
        ResponseState state = new ForwardState("report/subscription.jsp");
        LocalDate from = validator.validateDate("from", request);
        LocalDate to = validator.validateDate("to", request);
        String userLogin = validator.validateText("login", request);

        Specification<Subscription> searchSubscription = new SearchSubscription(from, to, userLogin);
        List<Subscription> subscriptionList = searchSubscription.search(subscriptionService);
        if (!subscriptionList.isEmpty()) {
            request.setAttribute("lst", subscriptionList);
        } else {
            request.setAttribute(AttrName.WARNING_MESSAGE, "message.warning.not_found");
        }
        BigDecimal sum = subscriptionList.stream()
                .map(Subscription::getPrice)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        request.setAttribute("sum", sum.toString());
        request.setAttribute("from", from);
        request.setAttribute("to", to);
        request.setAttribute("login", userLogin);
        logger.debug("Admin looks report for user={} from={} to={}", userLogin, from, to);
        return state;
    }
}
