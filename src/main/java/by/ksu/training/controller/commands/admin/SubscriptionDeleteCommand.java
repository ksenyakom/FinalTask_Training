package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.SubscriptionService;
import by.ksu.training.service.validator.SubscriptionValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Deletes subscriptions.
 *
 * @Author Kseniya Oznobishina
 * @Date 14.01.2021
 */
public class SubscriptionDeleteCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(SubscriptionDeleteCommand.class);

    /**
     * Deletes subscriptions with id in parameter REMOVE.
     *
     * @return ResponseState
     * @throws PersistentException if any exception occur in service layout.
     * @see by.ksu.training.entity.Subscription
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        EntityValidator<Subscription> validator = new SubscriptionValidator();
        SubscriptionService service = factory.getService(SubscriptionService.class);

        String action = request.getParameter(AttrName.ACTION);
        String parameter = action == null ? "" : "?" + AttrName.ACTION + "=" + action;
        ResponseState state = new RedirectState("subscription/list.html" + parameter);

        List<Integer> listId = validator.validateListId(AttrName.REMOVE, request);
        if (!listId.isEmpty()) {
            for (Integer id : listId) {
                service.delete(id);
            }
            state.getAttributes().put(AttrName.SUCCESS_MESSAGE, "message.success.delete");
        } else {
            state.getAttributes().put(AttrName.WARNING_MAP, validator.getWarningMap());
        }
        return state;
    }
}
