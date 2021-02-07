package by.ksu.training.controller.commands.visitor;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Prepares data to show on page of assigned complexes for visitor.
 *
 * @Author Kseniya Oznobishina
 * @Date 17.01.2021
 */
public class ShowVisitorAssignedComplexesCommand extends VisitorCommand {
    private static Logger logger = LogManager.getLogger(ShowVisitorAssignedComplexesCommand.class);

    /**
     * For this period of days assigned complexes records are collected for page.
     */
    private static final int DAYS = 60;

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
        AssignedComplexService acService = factory.getService(AssignedComplexService.class);

        List<AssignedComplex> assignedComplexes = acService.findUnexecutedByUser(user);
        if (assignedComplexes.isEmpty()) {
            request.setAttribute(AttrName.WARNING_MESSAGE, "message.warning.no_assigned_trainings");
        }
        List<AssignedComplex> executedComplexes = acService.findExecutedByUserForPeriod(user, DAYS);
        assignedComplexes.addAll(executedComplexes);

        request.setAttribute("lst", assignedComplexes);
        return new ForwardState("visitor/assigned_trainings.jsp");
    }
}
