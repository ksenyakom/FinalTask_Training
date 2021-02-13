package by.ksu.training.controller.commands.authorized_user;

import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Forward user to "edit login" page.
 *
 * @Author Kseniya Oznobishina
 * @Date 17.01.2021
 */
public class UserLoginEditShowPageCommand extends AuthorizedUserCommand {
    private static Logger logger = LogManager.getLogger(UserLoginEditShowPageCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        return new ForwardState("user/edit_login.jsp");
    }
}
