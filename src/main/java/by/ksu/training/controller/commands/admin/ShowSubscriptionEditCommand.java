package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.SubscriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Shows page for edit subscription with id, written in parameter EDIT_ID.
 *
 * @Author Kseniya Oznobishina
 * @Date 16.01.2021
 * @see by.ksu.training.entity.Subscription
 */
public class ShowSubscriptionEditCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(ShowSubscriptionEditCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            String editId = request.getParameter(AttrName.EDIT_ID);
            SubscriptionService service = factory.getService(SubscriptionService.class);
            if (editId != null) {
                int id = Integer.parseInt(editId);
                Subscription subscription = service.findById(id);
                request.setAttribute(AttrName.SUBSCRIPTION, subscription);
            }

            return new ForwardState("subscription/edit.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }
}
