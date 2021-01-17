package by.ksu.training.controller.commands.visitor;

import by.ksu.training.controller.commands.common.ShowAllCommonComplexCommand;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.ComplexService;
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

    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {

        try {
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("authorizedUser");
            if (user != null) {
                AssignedComplexService acService = factory.getService(AssignedComplexService.class);
                List<AssignedComplex> assignedComplexes = acService.findUnexecutedByUser(user);
                List<AssignedComplex> executedComplexes = acService.findExecutedByUserForPeriod(user,60);
                assignedComplexes.addAll(executedComplexes);

                request.setAttribute("lst", assignedComplexes);
            } else {
                request.setAttribute("err_message", "No authorized user");

            }
        } catch (PersistentException e) {
            logger.error("Exception in command!!!!", e);
            request.setAttribute("err_message",e.getMessage());
            return null;
        }
        return new Forward("visitor/assigned_trainings.jsp");

    }

    @Override
    public Set<Role> getAllowedRoles() {
        return null;
    }
}
