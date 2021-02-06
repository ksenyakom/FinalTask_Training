package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedTrainerService;
import by.ksu.training.service.UserService;
import by.ksu.training.service.validator.AssignedTrainerValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Prepares data to show on page for trainer assignment .
 *
 * @Author Kseniya Oznobishina
 * @Date 19.01.2021
 */
public class ShowAssignedTrainerSetPageCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(ShowAssignedTrainerSetPageCommand.class);

    /**
     * Prepares data to show on list of trainer assignment for user:
     * list of available trainers.
     *
     * @throws PersistentException if any exception occur in service layout.
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        UserService userService = factory.getService(UserService.class);
        AssignedTrainerService atService = factory.getService(AssignedTrainerService.class);
        Validator<AssignedTrainer> validator = new AssignedTrainerValidator();

        int id = validator.validateIntAttr(AttrName.VISITOR_ID, request);
        User visitor = new User(id);
        User trainer = atService.findTrainerByVisitor(visitor);
        List<User> trainers = userService.findUserByRole(Role.TRAINER);
        userService.findLogin(List.of(visitor));

        String action = request.getParameter(AttrName.ACTION);
        request.setAttribute(AttrName.ACTION, action);
        request.setAttribute(AttrName.TRAINER, trainer);
        request.setAttribute(AttrName.VISITOR, visitor);
        request.setAttribute("lst", trainers);

        return new ForwardState("assigned_trainer/set.jsp");
    }
}
