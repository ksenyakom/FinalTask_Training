package by.ksu.training.controller.commands.authorized_user;

import by.ksu.training.controller.AttrName;
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
 * Save changed login.
 *
 * @Author Kseniya Oznobishina
 * @Date 17.01.2021
 */
public class LoginUpdateCommand extends AuthorizedUserCommand {
    private static Logger logger = LogManager.getLogger(LoginUpdateCommand.class);

    /**
     * Save changed login. Performs check of password and duplicate login in base.
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        EntityValidator<User> validator = new UserLoginPasswordValidator();
        User authorizedUser = null;
        try {
            User newLoginUser = validator.validate(request);
            authorizedUser = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);

            if (!newLoginUser.getLogin().equals(authorizedUser.getLogin())) {
                UserService service = factory.getService(UserService.class);

                if (!service.checkLoginExist(newLoginUser.getLogin())) {
                    User userFromBase = service.findByLoginAndPassword(authorizedUser.getLogin(), newLoginUser.getPassword());
                    if (userFromBase != null) {
                        //save in base
                        newLoginUser.setEmail(authorizedUser.getEmail());
                        newLoginUser.setId(authorizedUser.getId());
                        newLoginUser.setRole(authorizedUser.getRole());
                        service.save(newLoginUser);
                        logger.info("user {} has changed login to {}",  authorizedUser.getLogin(), newLoginUser.getLogin());

                        //save in session
                        authorizedUser.setLogin(newLoginUser.getLogin());
                        request.getSession().setAttribute("authorizedUser", authorizedUser);
                        return new RedirectState("my_account.html");
                    } else {
                        request.setAttribute(AttrName.WARNING_MESSAGE, "message.warning.wrong_password");
                        return new ForwardState("user/edit_login.jsp");
                    }
                } else {
                    request.setAttribute(AttrName.WARNING_MESSAGE, "message.warning.login_already_exist");
                    return new ForwardState("user/edit_login.jsp");
                }
            } else {
                request.setAttribute(AttrName.WARNING_MESSAGE, "message.warning.login_did_not_changed");
                return new ForwardState("user/edit_login.jsp");
            }
        } catch (IncorrectFormDataException e) {
            request.setAttribute(AttrName.WARNING_MAP, validator.getWarningMap());
            return new ForwardState("user/edit_login.jsp");
        }
    }
}

