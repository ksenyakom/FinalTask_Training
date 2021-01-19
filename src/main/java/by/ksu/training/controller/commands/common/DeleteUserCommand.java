package by.ksu.training.controller.commands.common;

import by.ksu.training.controller.commands.admin.ShowUsersByRoleCommand;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * A class deletes all users which id came in request parameter "remove".
 * Then reads all users with role chosen on page in previous step.
 *
 * @Author Kseniya Oznobishina
 * @Date 05.01.2021
 */
public class DeleteUserCommand extends AuthorizedUserCommand {
    private static Logger logger = LogManager.getLogger(DeleteUserCommand.class);
    private static final String REMOVE = "remove";

    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        String[] usersId = request.getParameterValues(REMOVE);
        try {

            UserService userService = factory.getService(UserService.class);
            for (String stringId : usersId) {
                int id = Integer.parseInt(stringId);
                //TODO Проверкку какую может?
                userService.delete(id);
                request.setAttribute("success_message", "Юзер удален успешно");
                request.setAttribute("warn_message", "Юзер удален неуспешно");
                request.setAttribute("err_message", "Юзер вообще не удален");

            }

            String sRole = request.getParameter(ShowUsersByRoleCommand.ROLE);
            if (sRole != null) {
                Role role = Role.valueOf(sRole.toUpperCase());
                List<User> userList = userService.findUserByRole(role);

                request.setAttribute("lst", userList);
            }

        } catch (PersistentException e) {
            logger.error("Exception while delete user(s) {}", Arrays.toString(usersId), e);
        }

        return new Forward("user/list.jsp");
    }

    @Override
    public Set<Role> getAllowedRoles() {
        // return super.getAllowedRoles();
        return null; //TODO
    }
}
