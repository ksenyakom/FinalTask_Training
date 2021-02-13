package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
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
 * Prepares data to show on page list of users.
 *
 * @Author Kseniya Oznobishina
 * @Date 29.12.2020
 */
public class UsersByRoleShowCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(UsersByRoleShowCommand.class);

    /**
     * Prepares data to show  on page list users:
     * list of users by Role (Trainer or Visitor).
     *
     * @throws PersistentException if any exception occur in service layout.
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        UserService userService = factory.getService(UserService.class);
        try {
            String sRole = request.getParameter(AttrName.ROLE);
            if (sRole != null) {
                Role role = Role.valueOf(sRole.toUpperCase());
                List<User> userList = userService.findUserByRole(role);

                request.setAttribute("lst", userList);
                request.setAttribute(AttrName.ROLE, sRole);
            }
            return new ForwardState("user/list.jsp");
        } catch (IllegalArgumentException e) {
            throw new PersistentException("Exception while parsing role", e);
        }
    }
}
