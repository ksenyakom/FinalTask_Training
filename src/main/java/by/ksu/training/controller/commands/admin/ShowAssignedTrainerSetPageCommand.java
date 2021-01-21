package by.ksu.training.controller.commands.admin;

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
    private static final String VISITOR_ID = "visitorId";

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            String stringId = request.getParameter(VISITOR_ID);
            if (stringId != null) {
                int id = Integer.parseInt(stringId);
                User visitor = new User(id);
                UserService userService = factory.getService(UserService.class);
                List<User> trainers = userService.findUserByRole(Role.TRAINER);
                AssignedTrainerService atService = factory.getService(AssignedTrainerService.class);
                User trainer = atService.findTrainerByVisitor(visitor);
                if (trainer != null) {
                    userService.findLogin(List.of(visitor, trainer));
                    request.setAttribute("trainer", trainer);
                } else {
                    userService.findLogin(List.of(visitor));
                }
                request.setAttribute("visitor", visitor);
                request.setAttribute("lst", trainers);
            } else {
                request.setAttribute("err_message","No user chosen.");
            }
            //показать страницу назначения тренера
        } catch (PersistentException | NumberFormatException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute("err_message",e.getMessage());
            return null;
        }
        return new ResponseState("assigned_trainer/set.jsp");
    }
}
