package by.ksu.training.controller.commands.admin_and_trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedTrainerService;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.validator.ComplexValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Kseniya Oznobishina
 * @Date 27.01.2021
 */
public class UpdateEditedComplexCommand extends AdminAndTrainerCommand {
    private static Logger logger = LogManager.getLogger(UpdateEditedComplexCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Validator<Complex> validator = new ComplexValidator();
        ComplexService complexService = factory.getService(ComplexService.class);
        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
        Integer id = null;
        try {
            //check if operation allowed for user
            id = validator.validateId(request);
            Complex newComplex = validator.validate(request); // only visitor_id, title, trainer
            Complex oldComplex = complexService.findById(id);

            boolean allowed = false;
            // for admin
            if (user.getRole() == Role.ADMINISTRATOR && oldComplex != null && oldComplex.getVisitorFor() == null) {
                allowed = true;
            }
            // for trainer
            if (user.getRole() == Role.TRAINER && oldComplex != null && oldComplex.getVisitorFor() != null) {
                AssignedTrainerService assignedTrainerService = factory.getService(AssignedTrainerService.class);
                User trainerOfVisitor = assignedTrainerService.findTrainerByVisitor(oldComplex.getVisitorFor());

                if (trainerOfVisitor != null && user.getId().equals(trainerOfVisitor.getId())) {
                    allowed = true;
                    oldComplex.setTrainerDeveloped(user);
                }
            }

            if (!allowed) {
                ResponseState state = new ErrorState();
                state.getAttributes().put(AttrName.ERROR_MESSAGE, String.format("You are not allowed to edit this record: %s",
                        oldComplex == null ? "no such record" : oldComplex.getTitle()));
                return state;
            }

            oldComplex.setTitle(newComplex.getTitle());
            complexService.save(oldComplex);
            logger.debug("User {} updated complex {} with new title and trainer", user.getLogin(), oldComplex.getId());

            return new RedirectState("complex/my_complexes.html");
        } catch (IncorrectFormDataException e) {
            logger.debug("User entered invalid data.", e);
            ResponseState state = new ForwardState("complex/edit.jsp");
            Complex complex = validator.getInvalid();
            complex.setId(id);
            request.setAttribute(AttrName.WARNING_MAP, validator.getWarningMap());
            request.setAttribute(AttrName.COMPLEX, complex);
            return state;
        }
    }
}
