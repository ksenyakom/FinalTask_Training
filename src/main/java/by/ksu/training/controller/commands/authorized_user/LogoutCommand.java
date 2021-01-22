package by.ksu.training.controller.commands.authorized_user;

import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author Kseniya Oznobishina
 * @Date 05.01.2021
 */
public class LogoutCommand extends AuthorizedUserCommand {
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute("authorizedUser", null);
        request.setAttribute("command", null);
        return new ForwardState("index.jsp", true);
    }


}
