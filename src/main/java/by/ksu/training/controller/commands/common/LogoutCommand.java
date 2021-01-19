package by.ksu.training.controller.commands.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author Kseniya Oznobishina
 * @Date 05.01.2021
 */
public class LogoutCommand extends AuthorizedUserCommand {
    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute("authorizedUser", null);
        request.setAttribute("command", null);
        return new Forward("/index.html", true);
    }


}
