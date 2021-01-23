package by.ksu.training.controller.commands.trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.commands.visitor.UpdateDateExecutedAssignedComplexCommand;
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
 * @Author Kseniya Oznobishina
 * @Date 23.01.2021
 */
public class UpdateAssignedComplexCommand extends TrainerCommand {
    private static Logger logger = LogManager.getLogger(UpdateAssignedComplexCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<AssignedComplex> validator = new AssignedComplexValidator();
            AssignedComplex assignedComplex = validator.validate(request);

            AssignedComplexService service = factory.getService(AssignedComplexService.class);
            service.save(assignedComplex);

            String parameter = "?" + AttrName.USER_ID + "=" + assignedComplex.getVisitor().getId();
            return new RedirectState("assigned_complex/list.html" + parameter);
        } catch (IncorrectFormDataException e) {
            request.setAttribute(AttrName.WARNING_MESSAGE, "You have entered incorrect data: " + e.getMessage());
            return new ForwardState("assigned_complex/edit.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.WARNING_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }
}
