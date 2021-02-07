package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.commands.common.RegistrationCommand;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Saves new trainer.
 *
 * @Author Kseniya Oznobishina
 * @Date 07.02.2021
 */
public class RegisterTrainerCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(RegisterTrainerCommand.class);

    /**
     * Saves new trainer. Performs check if login exists.
     *
     * @throws PersistentException - if exception occur while receiving data from database.
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Validator<User> validator = new UserValidator();
        UserService service = factory.getService(UserService.class);
        User user = null;

        try {
            user = validator.validate(request);
            if (!service.checkLoginExist(user.getLogin())) {
                user.setRole(Role.TRAINER);
                service.save(user);
                logger.info("Admin created new trainer, login={} is created logged in from {} ({}:{})", user.getLogin(), request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort());
                return new RedirectState("user/list.html?role=trainer");
            } else {
                request.setAttribute(AttrName.WARNING_MESSAGE, "message.warning.login_already_exist");
                request.setAttribute(AttrName.USER, user);
                logger.info("Admin tried to create new trainer? but user {} already exist", user.getLogin());
                return new ForwardState("admin/register_trainer.jsp");
            }
        } catch (IncorrectFormDataException e) {
            request.setAttribute(AttrName.WARNING_MAP, validator.getWarningMap());
            user = validator.getInvalid();
            request.setAttribute(AttrName.USER, user);
            logger.error("Admin unsuccessfully tried to register new trainer login={} from {} ({}:{})", user != null ? user.getLogin() : "unknown", request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort());
            return new ForwardState("admin/register_trainer.jsp");
        }
    }
}
