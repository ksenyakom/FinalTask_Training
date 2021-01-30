package by.ksu.training.controller.commands.visitor;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.validator.AssignedComplexValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 * Updates assigned complex dateExecuted with today date.
 *
 * @Author Kseniya Oznobishina
 * @Date 22.01.2021
 * @see by.ksu.training.entity.AssignedComplex
 */
public class UpdateDateExecutedAssignedComplexCommand extends VisitorCommand {
    private static Logger logger = LogManager.getLogger(UpdateDateExecutedAssignedComplexCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<AssignedComplex> validator = new AssignedComplexValidator();
            int id = validator.validateId(request);
            AssignedComplexService service = factory.getService(AssignedComplexService.class);
            AssignedComplex assignedComplex = service.findById(id);
            User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
            if (user.getId().equals(assignedComplex.getVisitor().getId())) {
                assignedComplex.setDateExecuted(LocalDate.now());
                service.save(assignedComplex);
            } else {
                throw new PersistentException("User id do not correspond");
            }

            return new RedirectState("visitor/assigned_trainings.html");
//        }
//        catch (IncorrectFormDataException e) {
//            request.setAttribute(AttrName.WARNING_MESSAGE, "You have entered incorrect data: " + e.getMessage());
//            return new ForwardState("complex/execute.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.WARNING_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }
}
