package by.ksu.training.controller.commands.common;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.commands.Command;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
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
import org.w3c.dom.Attr;

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
                return new RedirectState("/index.jsp");

            } else {
                request.setAttribute(AttrName.WARNING_MESSAGE, "message.warning.login_already_exist");
                logger.info("user {} already exist", user.getLogin());
                return new ForwardState("registration.jsp");
            }
        } catch (PersistentException | IncorrectFormDataException e) {
            logger.error("user {} unsuccessfully tried to register from {} ({}:{})", user.getLogin(), request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort());
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }

}
