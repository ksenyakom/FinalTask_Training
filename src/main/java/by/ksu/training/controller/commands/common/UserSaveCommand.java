package by.ksu.training.controller.commands.common;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.commands.Command;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.UserService;
import by.ksu.training.service.validator.UserValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * A command for registration.
 *
 * @Author Kseniya Oznobishina
 * @Date 29.12.2020
 */
public class UserSaveCommand extends Command {
    private static Logger logger = LogManager.getLogger(UserSaveCommand.class);

    /**
     * A command for registration.
     * Provides check of duplicate login.
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        EntityValidator<User> validator = new UserValidator();
        UserService service = factory.getService(UserService.class);
        User user = null;

        try {
            user = validator.validate(request);
            if (!service.checkLoginExist(user.getLogin())) {
                user.setRole(Role.VISITOR);
                service.save(user);

                HttpSession session = request.getSession();
                session.setAttribute("authorizedUser", user);
                logger.info("user {} is created logged in from {} ({}:{})", user.getLogin(), request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort());
                return new RedirectState("my_account.html");

            } else {
                request.setAttribute(AttrName.WARNING_MESSAGE, "message.warning.login_already_exist");
                request.setAttribute(AttrName.USER, user);
                logger.info("user {} already exist", user.getLogin());
                return new ForwardState("registration.jsp");
            }
        } catch (IncorrectFormDataException e) {
            request.setAttribute(AttrName.WARNING_MAP, validator.getWarningMap());
            user = validator.getInvalid();
            request.setAttribute(AttrName.USER,user);
            logger.error("user {} unsuccessfully tried to register from {} ({}:{})",
                    user != null ? user.getLogin() : "unknown", request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort());
            return new ForwardState("registration.jsp");
        }
    }

}
