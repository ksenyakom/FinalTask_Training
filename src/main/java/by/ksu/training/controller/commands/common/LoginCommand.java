package by.ksu.training.controller.commands.common;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class LoginCommand extends Command {
    private static Logger logger = LogManager.getLogger(LoginCommand.class);

    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        String login =  request.getParameter("login");
        String password = request.getParameter("password");
        try {
            if (login != null && password != null) {
                UserService service = factory.getService(UserService.class);
                User user = service.findByLoginAndPassword(login, password);
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("authorizedUser", user);
               //     session.setAttribute("menu", menu.get(user.getRole()));
                    logger.info("user {} is logged in from {} ({}:{})", login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort());
                    request.setAttribute("command",null);
                    return new Forward("/index.jsp",true);
                } else {
                    request.setAttribute("message", "Имя пользователя или пароль не опознанны");
                    logger.info("user {} unsuccessfully tried to log in from {} ({}:{})", login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort());
                }
            }
        } catch (PersistentException e) {
            logger.error("Exception while Login of user {}",login, e);
        }

        return null;

      //  return new Forward("/oldIndex.jsp");
    }

    @Override
    public Set<Role> getAllowedRoles() {
        return null;
    }
}
