package by.ksu.training.controller.commands.common;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.UserService;
import by.ksu.training.service.validator.UserValidator;
import by.ksu.training.service.validator.Validator;
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
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        Validator<User> validator = new UserValidator();
        User user = null;

        try {
            user = validator.validate(request);
            UserService service = factory.getService(UserService.class);

            if (!service.checkLoginExist(user.getLogin())) {
                user.setRole(Role.VISITOR);
                service.save(user);

                HttpSession session = request.getSession();
                session.setAttribute("authorizedUser", user);
                logger.info("user {} is created logged in from {} ({}:{})", user.getLogin(), request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort());
                request.setAttribute("command", null);
                return new ResponseState("/index.html", true);

            } else {
                request.setAttribute("message", "Пользователь с таким именем уже существует");
                logger.info("user {} already exist", user.getLogin());
                return null;
            }
        } catch (PersistentException | IncorrectFormDataException e) {
            request.setAttribute("message", "Невозможно зарегестрировать нового пользователя, обратитесь к администратору");
            logger.error("user {} unsuccessfully tried to register from {} ({}:{})", user.getLogin(), request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort());
            return null;
        }
    }


    @Override
    public Set<Role> getAllowedRoles() {
        return null;
    }
}
