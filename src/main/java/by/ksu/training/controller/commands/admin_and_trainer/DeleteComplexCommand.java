package by.ksu.training.controller.commands.admin_and_trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.commands.authorized_user.DeleteUserCommand;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedTrainerService;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.UserService;
import by.ksu.training.service.validator.ComplexValidator;
import by.ksu.training.service.validator.UserValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Deletes complex chosen by user, 'id' of complexes to delete come with parameter 'removeId' in request.
 * Checks if authorized user can remove complex (trainer can remove complex of his visitor;
 * admin can remove common complexes, which developed not for certain visitor).
 *
 * @Author Kseniya Oznobishina
 * @Date 26.01.2021
 */
public class DeleteComplexCommand extends AdminAndTrainerCommand {
    private static Logger logger = LogManager.getLogger(DeleteComplexCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<Complex> validator = new ComplexValidator();
            List<Integer> listId = validator.validateRemoveId(request);

            // check if authorized user can remove complex
            User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
            ComplexService complexService = factory.getService(ComplexService.class);

            if (user.getRole() == Role.ADMINISTRATOR) {
                // for admin
                for (Integer id : listId) {
                    Complex complex = complexService.findById(id);
                    if (complex != null && complex.getVisitorFor() == null) {
                        complexService.delete(id);
                    } else {
                        request.setAttribute(AttrName.ERROR_MESSAGE,
                                String.format("You are not allowed to delete this record: %s",
                                        complex == null ? "no such record" : complex.getTitle()));
                        return new ErrorState();
                    }
                }
            } else {
                // for trainer
                AssignedTrainerService assignedTrainerService = factory.getService(AssignedTrainerService.class);
                for (Integer id : listId) {
                    Complex complex = complexService.findById(id);
                    if (complex != null && complex.getVisitorFor() != null) {
                        User trainerOfVisitor = assignedTrainerService.findTrainerByVisitor(complex.getVisitorFor());
                        if (trainerOfVisitor!= null && user.getId().equals(trainerOfVisitor.getId())) {
                            complexService.delete(id);
                        }else {
                            request.setAttribute(AttrName.ERROR_MESSAGE,
                                    String.format("You are not allowed to delete this record: %s", complex.getTitle()));
                            return new ErrorState();
                        }
                    } else {
                        request.setAttribute(AttrName.ERROR_MESSAGE,
                                String.format("You are not allowed to delete this record: %s",
                                        complex == null ? "no such record" : complex.getTitle()));
                        return new ErrorState();
                    }
                }
            }
            request.getSession().setAttribute(AttrName.SUCCESS_MESSAGE, "message.success.delete");
            return new RedirectState("complex/my_complexes.html");
        } catch (IncorrectFormDataException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.WARNING_MESSAGE, e.getMessage());
            return new ForwardState("complex/my_complexes.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }
}
