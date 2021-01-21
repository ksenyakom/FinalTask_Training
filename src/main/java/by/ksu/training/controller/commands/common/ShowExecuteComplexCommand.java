package by.ksu.training.controller.commands.common;

import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.ExerciseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.stream.Collectors;

/**
 * @Author Kseniya Oznobishina
 * @Date 20.01.2021
 */
public class ShowExecuteComplexCommand extends AuthorizedUserCommand {
    private static Logger logger = LogManager.getLogger(ShowExecuteComplexCommand.class);
    private static final String ASSIGNED_COMPLEX_ID = "assigned_complexId";
//TODO навести порядок в проге !!!
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("authorizedUser");
            String assignedComplexId = request.getParameter(ASSIGNED_COMPLEX_ID);
            int acId = Integer.parseInt(assignedComplexId);
            AssignedComplexService acService = factory.getService(AssignedComplexService.class);
            AssignedComplex assignedComplex = acService.findById(acId);
            Complex complex = null;
            if (assignedComplex != null && user != null && user.getId().equals(assignedComplex.getVisitor().getId())) {
                ComplexService complexService = factory.getService(ComplexService.class);
                complex = complexService.findById(assignedComplex.getComplex().getId());
            } else {
                throw new PersistentException("Wrong parameter request");
            }
            ExerciseService exerciseService = factory.getService(ExerciseService.class);
            exerciseService.find(complex.getListOfUnits().stream()
                    .map(unit -> unit.getExercise())
                    .distinct()
                    .collect(Collectors.toList()));

            request.setAttribute("assigned_complex", assignedComplex);
            request.setAttribute("complex", complex);

        } catch (
                PersistentException e) {
            logger.error("Exception in command!!!!", e);
            request.setAttribute("err_message", e.getMessage());
            return null;
        }
        return new ResponseState("complex/execute.jsp");
    }
}