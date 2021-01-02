package by.ksu.training.controller.commands;

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

/**
 * @Author Kseniya Oznobishina
 * @Date 29.12.2020
 */
public class RegistrationCommand extends Command {
    private static Logger logger = LogManager.getLogger(RegistrationCommand.class);

    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        //TODO сделать проверку повторного пароля на форме
        //А если user уже в  зарегистррован?
        try {
            if (login != null && password != null && email != null) {
                UserService service = factory.getService(UserService.class);

                if (!service.checkLoginExist(login)) {
                    User user = new User();

                    user.setLogin(login);
                    user.setPassword(password);
                    user.setEmail(email);
                    user.setRole(Role.VISITOR);
                    service.save(user);

                    HttpSession session = request.getSession();
                    session.setAttribute("authorizedUser", user);
                    logger.info("user {} is created logged in from {} ({}:{})", login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort());
                    return new Forward("/index.html");

                } else {
                    request.setAttribute("message", "Пользователь с таким именем уже существует");
                    logger.info("user {} already exist", login);
                    return null;
                }
            }
        } catch (PersistentException e) {
            request.setAttribute("message", "Невозможно зарегестрировать нового пользователя, обратитесь к администратору");
            logger.error("user {} unsuccessfully tried to register from {} ({}:{})", login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort());
        }
        return null;
        //  return new Forward("WEB-INF/jsp/index.jsp");
    }


    @Override
    public Set<Role> getAllowedRoles() {
        return null;
    }
}
