package by.ksu.training.controller.commands.admin_and_trainer;

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
 * @Date 27.01.2021
 */
public class ShowAddComplexPageCommand extends AdminAndTrainerCommand {
    private static Logger logger = LogManager.getLogger(ShowAddComplexPageCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
        AssignedTrainerService assignedTrainerService = factory.getService(AssignedTrainerService.class);

        if (user.getRole() == Role.TRAINER) {
            List<User> visitors = assignedTrainerService.findVisitorsByTrainer(user);
            request.setAttribute("lst", visitors);
        }
        return new ForwardState("complex/add.jsp");
    }
}
