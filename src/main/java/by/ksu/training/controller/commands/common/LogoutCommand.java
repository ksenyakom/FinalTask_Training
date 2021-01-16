package by.ksu.training.controller.commands.common;

import by.ksu.training.controller.commands.AuthorizedUserCommand;
import by.ksu.training.entity.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

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
