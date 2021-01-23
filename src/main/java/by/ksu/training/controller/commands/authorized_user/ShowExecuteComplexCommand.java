package by.ksu.training.controller.commands.authorized_user;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.ExerciseService;
import by.ksu.training.service.validator.AssignedComplexValidator;
import by.ksu.training.service.validator.Validator;
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
//TODO привести в порядок, убрать лишнее в сервис
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(AttrName.AUTHORIZED_USER);

            Validator<AssignedComplex> validator = new AssignedComplexValidator();

            int assignedComplexId = validator.validateId(request);
            AssignedComplexService acService = factory.getService(AssignedComplexService.class);
            AssignedComplex assignedComplex = acService.findById(assignedComplexId);

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

            request.setAttribute(AttrName.ASSIGNED_COMPLEX, assignedComplex);
            request.setAttribute(AttrName.COMPLEX, complex);

            return new ForwardState("complex/execute.jsp");
        } catch (
                PersistentException e) {
            logger.error("Exception in command!!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        } catch (IncorrectFormDataException e) {
            request.setAttribute(AttrName.WARNING_MESSAGE, "You have entered incorrect data: " + e.getMessage());
            return new ForwardState("/complex/execute.jsp");
        }

    }
}