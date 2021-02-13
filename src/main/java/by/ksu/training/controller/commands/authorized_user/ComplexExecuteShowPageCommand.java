package by.ksu.training.controller.commands.authorized_user;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.*;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.SubscriptionService;
import by.ksu.training.service.validator.AssignedComplexValidator;
import by.ksu.training.service.validator.ComplexValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Prepares data to show complex for execute page.
 *
 * @Author Kseniya Oznobishina
 * @Date 20.01.2021
 */
public class ComplexExecuteShowPageCommand extends AuthorizedUserCommand {
    private static Logger logger = LogManager.getLogger(ComplexExecuteShowPageCommand.class);

    /**
     * Prepares data to show complex for execute page.
     * For Visitor: checks for active subscription,
     * checks for assigned complex is for user;
     *
     * @throws PersistentException - if visitor has no active subscription, or assignedComplex
     *                             with requested id was not found or not for this user.
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
        Complex complex = null;
        ComplexService complexService = factory.getService(ComplexService.class);
        SubscriptionService subscriptionService = factory.getService(SubscriptionService.class);
        AssignedComplexService acService = factory.getService(AssignedComplexService.class);
        EntityValidator<AssignedComplex> validator = new AssignedComplexValidator();

        if (user.getRole() == Role.VISITOR) {
            //for Visitor
            Subscription activeSubscription = subscriptionService.findActiveByUser(user);
            if (activeSubscription != null) {
                int assignedComplexId = validator.validateId(request);
                AssignedComplex assignedComplex = acService.findById(assignedComplexId);

                if (assignedComplex != null && user.getId().equals(assignedComplex.getVisitor().getId())) {
                    complex = complexService.findById(assignedComplex.getComplex().getId());
                    request.setAttribute(AttrName.ASSIGNED_COMPLEX, assignedComplex);
                } else {
                    throw new PersistentException("Wrong parameter request: assignment not found or access forbidden");
                }
            } else {
                // сообщение о том что закончилась подписка
                ResponseState state = new RedirectState("complex/list.html");
                state.getAttributes().put(AttrName.WARNING_MESSAGE, "message.warning.no_active_subscription");
                logger.debug("User {} received warning that subscription ended", user.getLogin(), complex.getId());
                return state;
            }
        } else {
            //for role admin and trainer
            EntityValidator<Complex> complexValidator = new ComplexValidator();
            int complexId = complexValidator.validateId(request);
            complex = complexService.findById(complexId);
            if (complex == null) {
                throw new PersistentException("Wrong parameter request: complex not found");
            }
        }
        logger.debug("User {} started execute of complex id={}", user.getLogin(), complex.getId());
        request.setAttribute(AttrName.COMPLEX, complex);
        return new ForwardState("complex/execute.jsp");
    }
}