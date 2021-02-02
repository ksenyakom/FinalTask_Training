package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Finds users by role.
 *
 * @Author Kseniya Oznobishina
 * @Date 29.12.2020
 */
public class ShowUsersByRoleCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(ShowUsersByRoleCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            String sRole = request.getParameter(AttrName.ROLE);
            if (sRole != null) {
                Role role = Role.valueOf(sRole.toUpperCase());

                UserService userService = factory.getService(UserService.class);
                List<User> userList = userService.findUserByRole(role);

                request.setAttribute("lst", userList);
            }

            return new ForwardState("user/list.jsp");
        } catch (IllegalArgumentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.WARNING_MESSAGE, e.getMessage());
            return new ForwardState("user/list.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        }

    }
}
