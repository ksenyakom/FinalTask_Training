package by.ksu.training.controller.commands.authorized_user;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
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
import java.util.List;

/**
 * A class deletes all users which id came in request parameter "remove".
 *
 * @Author Kseniya Oznobishina
 * @Date 05.01.2021
 */
public class DeleteUserCommand extends AuthorizedUserCommand {
    private static Logger logger = LogManager.getLogger(DeleteUserCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<User> validator = new UserValidator();
            List<Integer> listId = validator.validateRemoveId(request);
            UserService userService = factory.getService(UserService.class);
            for (Integer id : listId) {
                userService.delete(id);
            }
            request.getSession().setAttribute(AttrName.SUCCESS_MESSAGE, "message.success.delete");

            String sRole = request.getParameter(AttrName.ROLE);
            String parameter = sRole == null ? "" : "?" + AttrName.ROLE + "=" + sRole;

            return new RedirectState("user/list.html" + parameter);
        } catch (IncorrectFormDataException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.WARNING_MESSAGE, e.getMessage());
            return new ForwardState("user/list.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }
}
