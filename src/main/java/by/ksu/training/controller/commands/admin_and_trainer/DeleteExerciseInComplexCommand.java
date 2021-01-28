package by.ksu.training.controller.commands.admin_and_trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.Exercise;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedTrainerService;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.validator.ComplexValidator;
import by.ksu.training.service.validator.ExerciseValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;

/**
 * @Author Kseniya Oznobishina
 * @Date 27.01.2021
 */
public class DeleteExerciseInComplexCommand extends AdminAndTrainerCommand {
    private static Logger logger = LogManager.getLogger(DeleteExerciseInComplexCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<Exercise> validator = new ExerciseValidator();
            List<Integer> removeIndex = validator.validateRemoveId(request);
            Validator<Complex> complexVilidator = new ComplexValidator();
            Integer complexId = complexVilidator.validateId(request);

            // check if authorized user can work with complex
            User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
            ComplexService complexService = factory.getService(ComplexService.class);
            Complex complex = complexService.findById(complexId);
            // for admin
            boolean allowed = false;
            if (user.getRole() == Role.ADMINISTRATOR && complex != null && complex.getVisitorFor() == null) {
                allowed = true;
            }
            // for trainer
            if (user.getRole() == Role.TRAINER && complex != null && complex.getVisitorFor() != null) {
                AssignedTrainerService assignedTrainerService = factory.getService(AssignedTrainerService.class);
                User trainerOfVisitor = assignedTrainerService.findTrainerByVisitor(complex.getVisitorFor());
                if (trainerOfVisitor != null && user.getId().equals(trainerOfVisitor.getId())) {
                    allowed = true;
                }
            }
            if (!allowed) {
                request.setAttribute(AttrName.ERROR_MESSAGE, String.format("You are not allowed to delete this record: %s",
                        complex == null ? "no such record" : complex.getTitle()));
                return new ErrorState();
            }
            removeIndex.sort(Comparator.reverseOrder());
            for (Integer index : removeIndex) {
                complex.deleteComplexUnit(index-1);
            }
            complexService.save(complex);

            String parameter = "?" + AttrName.COMPLEX_ID + "=" + complexId;
            return new RedirectState("complex/edit.html"+ parameter);
        } catch (IncorrectFormDataException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.WARNING_MESSAGE, e.getMessage());
            return new ForwardState("complex/edit.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }
}
