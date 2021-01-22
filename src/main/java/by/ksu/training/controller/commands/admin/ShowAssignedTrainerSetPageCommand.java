package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedTrainerService;
import by.ksu.training.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Kseniya Oznobishina
 * @Date 19.01.2021
 */
public class ShowAssignedTrainerSetPageCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(ShowAssignedTrainerSetPageCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            String stringId = request.getParameter(AttrName.VISITOR_ID);
            if (stringId != null) {
                int id = Integer.parseInt(stringId);

                User visitor = new User(id);

                UserService userService = factory.getService(UserService.class);
                List<User> trainers = userService.findUserByRole(Role.TRAINER);
                AssignedTrainerService atService = factory.getService(AssignedTrainerService.class);
                User trainer = atService.findTrainerByVisitor(visitor);
                if (trainer != null) {
                    userService.findLogin(List.of(visitor, trainer));
                    request.setAttribute(AttrName.TRAINER, trainer);
                } else {
                    userService.findLogin(List.of(visitor));
                }

                request.setAttribute(AttrName.VISITOR, visitor);
                request.setAttribute("lst", trainers);
            } else {
                request.setAttribute(AttrName.ERROR_MESSAGE,"message.warning.no_user");
            }

            return new ForwardState("assigned_trainer/set.jsp");
        } catch ( NumberFormatException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.WARNING_MESSAGE, "message.warning.parameter_not_correct");
            return new ForwardState("assigned_trainer/list.jsp");
        }catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        }

    }
}
