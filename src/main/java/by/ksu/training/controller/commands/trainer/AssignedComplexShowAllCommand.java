package by.ksu.training.controller.commands.trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.UserService;
import by.ksu.training.service.validator.UserValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Prepares data to show on page assigned complexes for trainer.
 *
 * @Author Kseniya Oznobishina
 * @Date 22.01.2021
 */
public class AssignedComplexShowAllCommand extends TrainerCommand {
    private static Logger logger = LogManager.getLogger(AssignedComplexShowAllCommand.class);
    /**
     * For this period of days executed complexes records are collected for page.
     */
    private static final int DAYS = 60;

    /**
     * Prepares data to show on page, includes complexes executed for period DAYS,
     * and all assigned complexes unexecuted.
     *
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        EntityValidator<User> validator = new UserValidator();
        AssignedComplexService acService = factory.getService(AssignedComplexService.class);
        UserService userService = factory.getService(UserService.class);

        Integer visitorId = validator.validateId(request);
        User visitor = new User(visitorId);
        List<AssignedComplex> assignedComplexes = acService.findUnexecutedByUser(visitor);
        List<AssignedComplex> executedComplexes = acService.findExecutedByUserForPeriod(visitor, DAYS);
        assignedComplexes.addAll(executedComplexes);
        userService.findLogin(List.of(visitor));

        request.setAttribute(AttrName.VISITOR, visitor);
        request.setAttribute("lst", assignedComplexes);
        return new ForwardState("assigned_complex/list.jsp");
    }
}
