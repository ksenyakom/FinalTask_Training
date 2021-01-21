package by.ksu.training.controller.commands.common;

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
import java.util.Set;

/**
 * @Author Kseniya Oznobishina
 * @Date 17.01.2021
 */
public class SaveChangedLoginCommand extends AuthorizedUserCommand {
    private static Logger logger = LogManager.getLogger(SaveChangedLoginCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        Validator<User> validator = new UserLoginPasswordValidator();
        User authorizedUser = null;
        try {
            User user = validator.validate(request);
            authorizedUser = (User) request.getSession().getAttribute("authorizedUser");

            if (!user.getLogin().equals(authorizedUser.getLogin())) {
                UserService service = factory.getService(UserService.class);

                if (!service.checkLoginExist(user.getLogin())) {
                    User userFromBase = service.findByLoginAndPassword(authorizedUser.getLogin(), user.getPassword());
                    if (userFromBase != null) {
                        //save in base
                        user.setEmail(authorizedUser.getEmail());
                        user.setId(authorizedUser.getId());
                        user.setRole(authorizedUser.getRole());
                        service.save(user);
                        logger.info("user {} has changed login to {}",
                                authorizedUser.getLogin(), user.getLogin());

                        //save in session
                        authorizedUser.setLogin(user.getLogin());
                        request.getSession().setAttribute("authorizedUser", authorizedUser);
                        return new ResponseState("/index.jsp", true); //redirect to index
                    } else {
                        request.setAttribute("warning_message", "Wrong password.");
                        return new ResponseState("user/edit_login.jsp"); // returnBack
                    }
                } else {
                    request.setAttribute("warning_message", "Enter other login. Login already exist!!!");
                    return new ResponseState("user/edit_login.jsp"); // returnBack
                }
            } else {
                request.setAttribute("warning_message", "No login changed.");
                return new ResponseState("user/edit_login.jsp"); // returnBack
            }
        } catch (IncorrectFormDataException e) {
            request.setAttribute("warning_message", "You entered incorrect data: " + e.getMessage());
            return new ResponseState("user/edit_login.jsp"); // returnBack
        } catch (PersistentException e) {
            logger.error("Exception while changing Login of user {}", authorizedUser.getLogin(), e);
            request.setAttribute("error_message", "Exception in command!! " + e.getMessage());
            return null; //error state
        }
    }

    @Override
    public Set<Role> getAllowedRoles() {
        return null;
    }
}

