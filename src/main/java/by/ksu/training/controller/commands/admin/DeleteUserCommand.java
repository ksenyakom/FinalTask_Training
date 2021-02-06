package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.User;
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
 * A class for delete user(s) by admin.
 *
 * @Author Kseniya Oznobishina
 * @Date 05.01.2021
 */
public class DeleteUserCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(DeleteUserCommand.class);

    /**
     * Deletes records of Users with id which came in parameter "remove".
     *
     * @return ResponseState
     * @throws PersistentException if any exception occur in service layout.
     * @see by.ksu.training.controller.state.ResponseState
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Validator<User> validator = new UserValidator();
        UserService userService = factory.getService(UserService.class);
        String sRole = request.getParameter(AttrName.ROLE);
        String parameter = sRole == null ? "" : "?" + AttrName.ROLE + "=" + sRole;
        ResponseState state = new RedirectState("user/list.html" + parameter);

        List<Integer> listId = validator.validateListId(AttrName.REMOVE, request);
        if (!listId.isEmpty()) {
            for (Integer id : listId) {
                userService.delete(id);
                logger.debug("Admin deleted user id={}", id);
            }
            state.getAttributes().put(AttrName.SUCCESS_MESSAGE, "message.success.delete");
        } else {
            state.getAttributes().put(AttrName.WARNING_MAP, validator.getWarningMap());
        }
        return state;
    }
}
