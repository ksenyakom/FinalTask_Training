package by.ksu.training.controller.commands.trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.AssignedTrainerService;
import by.ksu.training.service.validator.AssignedComplexValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Updates assignedComplex.
 *
 * @Author Kseniya Oznobishina
 * @Date 23.01.2021
 */
public class AssignedComplexUpdateCommand extends TrainerCommand {
    private static Logger logger = LogManager.getLogger(AssignedComplexUpdateCommand.class);

    /**
     * Updates assignedComplex with data which came in request.
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        EntityValidator<AssignedComplex> validator = new AssignedComplexValidator();
        AssignedTrainerService assignedTrainerService = factory.getService(AssignedTrainerService.class);
        AssignedComplexService assignedComplexService = factory.getService(AssignedComplexService.class);
        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);

        int visitorId = 0;
        int assignedComplexId = 0;
        try {
            assignedComplexId = validator.validateId(request);
            visitorId = validator.validateIntAttr(AttrName.VISITOR_ID, request);
            User visitor = new User(visitorId);
            int complexId = validator.validateIntAttr(AttrName.COMPLEX_ID, request);

            boolean check = assignedTrainerService.checkTrainerByVisitor(user, visitor);
            if (check) {
                AssignedComplex assignedComplex = validator.validate(request);
                assignedComplex.setId(assignedComplexId);
                assignedComplex.setVisitor(visitor);
                assignedComplex.setComplex(new Complex(complexId));
                assignedComplexService.save(assignedComplex);
                logger.debug("User {} updated assignedComplex id={} for visitor id={} ", user.getLogin(), complexId, visitorId);

                String parameter = "?" + AttrName.USER_ID + "=" + assignedComplex.getVisitor().getId();
                return new RedirectState("assigned_complex/list.html" + parameter);
            } else {
                throw new PersistentException("You are not allowed to add complex to this visitor");
            }
        } catch (IncorrectFormDataException e) {
            logger.warn("User entered invalid data", e);
            String parameter = "?" + AttrName.ASSIGNED_COMPLEX_ID + "=" + assignedComplexId;
            ResponseState state = new RedirectState("assigned_complex/edit.html" + parameter);
            state.getAttributes().put(AttrName.WARNING_MAP, validator.getWarningMap());
            return state;
        }
    }
}
