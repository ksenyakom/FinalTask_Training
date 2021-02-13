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
 * Adds assigned complex to visitor.
 *
 * @Author Kseniya Oznobishina
 * @Date 22.01.2021
 */
public class AssignedComplexSaveCommand extends TrainerCommand {
    private static Logger logger = LogManager.getLogger(AssignedComplexSaveCommand.class);

    /**
     * Adds assigned complex to visitor.
     * Performs check if current authorized user is trainer of visitor,
     * which visitorId came with request.
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        EntityValidator<AssignedComplex> validator = new AssignedComplexValidator();
        AssignedComplexService service = factory.getService(AssignedComplexService.class);
        AssignedTrainerService assignedTrainerService = factory.getService(AssignedTrainerService.class);
        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);

        int visitorId = 0;
        try {
            visitorId = validator.validateIntAttr(AttrName.VISITOR_ID, request);
            User visitor = new User(visitorId);
            int complexId = validator.validateIntAttr(AttrName.COMPLEX_ID, request);

            boolean check = assignedTrainerService.checkTrainerByVisitor(user, visitor);
            if (check) {
                AssignedComplex assignedComplex = validator.validate(request);
                assignedComplex.setVisitor(visitor);
                assignedComplex.setComplex(new Complex(complexId));

                service.save(assignedComplex);
                logger.debug("User {} added assigned complex id={} to visitor id={}", user.getLogin(), complexId, visitorId);
                String parameter = "?userId=" + assignedComplex.getVisitor().getId();

                return new RedirectState("assigned_complex/list.html" + parameter);
            } else {
                throw new PersistentException("You are not allowed to add complex to this visitor");
            }
        } catch (IncorrectFormDataException e) {
            logger.warn("User entered invalid data", e);
            String parameter = "?" + AttrName.USER_ID + "=" + visitorId;
            ResponseState state = new RedirectState("assigned_complex/add.html" + parameter);
            state.getAttributes().put(AttrName.WARNING_MAP, validator.getWarningMap());
            return state;
        }
    }
}
