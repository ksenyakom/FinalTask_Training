package by.ksu.training.controller.commands;

import by.ksu.training.entity.Role;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.SubscriptionService;
import by.ksu.training.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Array;
import java.util.Arrays;
import java.util.Set;

/**
 * @Author Kseniya Oznobishina
 * @Date 05.01.2021
 */
public class UserDeleteCommand extends AuthorizedUserCommand{
    private static Logger logger = LogManager.getLogger(UserDeleteCommand.class);

    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        String[] usersId = request.getParameterValues("remove");

        try {
            UserService userService = factory.getService(UserService.class);

            for (String stringId: usersId) {
                int id = Integer.parseInt(stringId);
                //TODO Проверкку какую может?
                userService.delete(id);
            }

        } catch (PersistentException e) {
            logger.error("Exception while delete user(s) {}",Arrays.toString(usersId),e);
        }

        return new Forward("user/list.jsp");
    }

    @Override
    public Set<Role> getAllowedRoles() {
       // return super.getAllowedRoles();
        return null; //TODO
    }
}
