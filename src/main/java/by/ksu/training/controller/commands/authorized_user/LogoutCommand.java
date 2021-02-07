package by.ksu.training.controller.commands.authorized_user;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Logout authorized user.
 *
 * @Author Kseniya Oznobishina
 * @Date 05.01.2021
 */
public class LogoutCommand extends AuthorizedUserCommand {
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(AttrName.AUTHORIZED_USER);
        return new RedirectState("index.jsp");
    }
}
