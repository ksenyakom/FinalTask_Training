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
import by.ksu.training.service.validator.UserLoginPasswordValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class LoginCommand extends Command {
    private static Logger logger = LogManager.getLogger(LoginCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<User> validator = new UserLoginPasswordValidator();
            User testUser = validator.validate(request);
            UserService service = factory.getService(UserService.class);
            User user = service.findByLoginAndPassword(testUser.getLogin(), testUser.getPassword());
            if (user != null) {
                HttpSession session = request.getSession();
                user.setPassword(null);
                session.setAttribute("authorizedUser", user);
                logger.info("user {} is logged in from {} ({}:{})", user.getLogin(), request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort());

                return new RedirectState("/index.jsp");
            } else {
                request.setAttribute(AttrName.WARNING_MESSAGE, "message.warning.wrong_login_or_password");
                logger.info("user {} unsuccessfully tried to log in from {} ({}:{})", testUser.getLogin(), request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort());

                return new ForwardState("login.jsp");
            }
        } catch (IncorrectFormDataException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.WARNING_MESSAGE, e.getMessage());
            return new ForwardState("login.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }
}
