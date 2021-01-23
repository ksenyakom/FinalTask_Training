package by.ksu.training.controller.commands.trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.commands.admin.ShowSubscriptionEditCommand;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.SubscriptionService;
import by.ksu.training.service.UserService;
import by.ksu.training.service.validator.AssignedComplexValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Kseniya Oznobishina
 * @Date 23.01.2021
 */
public class ShowAssignedComplexEditPageCommand extends TrainerCommand {
    private static Logger logger = LogManager.getLogger(ShowAssignedComplexEditPageCommand.class);
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<AssignedComplex> validator = new AssignedComplexValidator();
            int id = validator.validateId(request);
            AssignedComplexService service = factory.getService(AssignedComplexService.class);
            AssignedComplex assignedComplex = service.findById(id);
            UserService userService = factory.getService(UserService.class);
            userService.findLogin(List.of(assignedComplex.getVisitor()));
            ComplexService complexService = factory.getService(ComplexService.class);
            List<Complex> complexes = complexService.findComplexesMetaDataByUser(assignedComplex.getVisitor());

            request.setAttribute("lst",complexes);
            request.setAttribute(AttrName.ASSIGNED_COMPLEX, assignedComplex);

            return new ForwardState("assigned_complex/edit.jsp");
        } catch (PersistentException | IncorrectFormDataException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }
}
