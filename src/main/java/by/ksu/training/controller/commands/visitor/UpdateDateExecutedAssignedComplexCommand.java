package by.ksu.training.controller.commands.visitor;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.User;
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
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Validator<AssignedComplex> validator = new AssignedComplexValidator();
        AssignedComplexService service = factory.getService(AssignedComplexService.class);
        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);

        int id = validator.validateId(request);
        AssignedComplex assignedComplex = service.findById(id);

        if (user.getId().equals(assignedComplex.getVisitor().getId())) {
            assignedComplex.setDateExecuted(LocalDate.now());
            service.save(assignedComplex);
            logger.debug("User {} executed complex id = {} assignment id = {}",
                    user.getLogin(), assignedComplex.getComplex().getId(), assignedComplex.getId());
        } else {
            throw new PersistentException("User id do not correspond complex assignment");
        }

        return new RedirectState("visitor/assigned_trainings.html");
    }
}
