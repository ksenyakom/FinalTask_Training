package by.ksu.training.controller.commands.authorized_user;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A command to redirect user to his account page by role.
 *
 * @Author Kseniya Oznobishina
 * @Date 31.01.2021
 */
public class ShowMyAccountPageCommand extends AuthorizedUserCommand {
    private static Logger logger = LogManager.getLogger(ShowMyAccountPageCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
        ResponseState state;
        if (user.getRole() == Role.ADMINISTRATOR) {
            state = new RedirectState("user/list.html");
        } else if (user.getRole() == Role.TRAINER) {
            state = new RedirectState("visitor/list.html");
        } else if (user.getRole() == Role.VISITOR) {
            state = new RedirectState("visitor/assigned_trainings.html");
        } else {
            throw new PersistentException("no authorized user");
        }
        return state;
    }
}
