package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.commands.StartCommand;
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
 * @Author Kseniya Oznobishina
 * @Date 29.12.2020
 */
public class ShowUsersByRoleCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(ShowUsersByRoleCommand.class);

    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            String sRole = request.getParameter("role");
            if (sRole != null) {
                Role role = Role.valueOf(sRole.toUpperCase());

                UserService userService = factory.getService(UserService.class);
                List<User> userList = userService.findUserByRole(role);

                request.setAttribute("lst", userList);
            }
        } catch (PersistentException e) {
            logger.error("Exception in command", e);
        }
        return new Forward("user/list.jsp");
    }
}
