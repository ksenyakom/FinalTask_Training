package by.ksu.training.controller.commands.visitor;

import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

/**
 * @Author Kseniya Oznobishina
 * @Date 17.01.2021
 */
public class ShowVisitorAssignedComplexesCommand extends VisitorCommand {
    private static Logger logger = LogManager.getLogger(ShowVisitorAssignedComplexesCommand.class);
    private static final int DAYS = 60;

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("authorizedUser");

            AssignedComplexService acService = factory.getService(AssignedComplexService.class);
            List<AssignedComplex> assignedComplexes = acService.findUnexecutedByUser(user);
            List<AssignedComplex> executedComplexes = acService.findExecutedByUserForPeriod(user, DAYS);
            assignedComplexes.addAll(executedComplexes);

            request.setAttribute("lst", assignedComplexes);
            return new ForwardState("visitor/assigned_trainings.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!!", e);
            request.setAttribute("err_message", e.getMessage());
            return new ErrorState();
        }
    }
}
