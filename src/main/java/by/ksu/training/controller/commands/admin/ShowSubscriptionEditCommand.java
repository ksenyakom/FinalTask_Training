package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.SubscriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @Author Kseniya Oznobishina
 * @Date 16.01.2021
 */
public class ShowSubscriptionEditCommand extends AdminCommand{
    private static Logger logger = LogManager.getLogger(ShowSubscriptionEditCommand.class);
    private static final String EDIT_ID = "editId";
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            String editId = request.getParameter(EDIT_ID);
            SubscriptionService service = factory.getService(SubscriptionService.class);
            if (editId != null) {
                int id = Integer.parseInt(editId);
                Subscription subscription = service.findById(id);

                request.setAttribute("subscription", subscription);
            }
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute("err_message",e.getMessage());
            return null;
        }
        return new ResponseState("subscription/edit_login.jsp");
    }

    @Override
    public Set<Role> getAllowedRoles() {
        return null; //TODO
    }
}
