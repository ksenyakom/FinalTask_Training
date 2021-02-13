package by.ksu.training.controller.commands.common;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.commands.Command;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.UserService;
import by.ksu.training.service.validator.UserLoginPasswordValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A class to authorize user.
 *
 * @Author Kseniya Oznobishina
 * @Date 07.01.2021
 */

public class LogInCommand extends Command {
    private static Logger logger = LogManager.getLogger(LogInCommand.class);

    /**
     * Authorizes user and saves him in session.
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        EntityValidator<User> validator = new UserLoginPasswordValidator();
        UserService service = factory.getService(UserService.class);
        try {
            User testUser = validator.validate(request);
            User user = service.findByLoginAndPassword(testUser.getLogin(), testUser.getPassword());
            if (user != null) {
                user.setPassword(null);
                request.getSession().setAttribute(AttrName.AUTHORIZED_USER, user);
                logger.info("user {} is logged in from {} ({}:{})", user.getLogin(), request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort());

                return new RedirectState("my_account.html");
            } else {
                request.setAttribute(AttrName.WARNING_MESSAGE, "message.warning.wrong_login_or_password");
                logger.info("user {} unsuccessfully tried to log in from {} ({}:{})", testUser.getLogin(), request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort());

                return new ForwardState("login.jsp");
            }
        } catch (IncorrectFormDataException e) {
            request.setAttribute(AttrName.WARNING_MAP, validator.getWarningMap());
            return new ForwardState("login.jsp");
        }
    }
}
