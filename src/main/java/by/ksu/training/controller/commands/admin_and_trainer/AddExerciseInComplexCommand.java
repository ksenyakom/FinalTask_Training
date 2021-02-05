package by.ksu.training.controller.commands.admin_and_trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.Exercise;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.validator.ComplexValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Adds exercise(es) with id which came with parameter "addId" to complex.
 *
 * @Author Kseniya Oznobishina
 * @Date 27.01.2021
 */
public class AddExerciseInComplexCommand extends AdminAndTrainerCommand {
    private static Logger logger = LogManager.getLogger(AddExerciseInComplexCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Validator<Complex> complexValidator = new ComplexValidator();
        ComplexService complexService = factory.getService(ComplexService.class);
        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);

        List<Integer> exerciseId = complexValidator.validateListId(AttrName.ADD_ID, request);

        Integer complexId = complexValidator.validateId(request);
        Complex complex = complexService.findById(complexId);
        if (!exerciseId.isEmpty()) {
            boolean allowed = complexService.checkEditAllowed(user, complex);
            if (!allowed) {
                throw new PersistentException(String.format("You are not allowed to edit this record: %s", complex.getTitle()));
            }

            for (Integer id : exerciseId) {
                Complex.ComplexUnit unit = new Complex.ComplexUnit();
                unit.setExercise(new Exercise(id));
                complex.addComplexUnit(unit);
            }
            complexService.save(complex);
            logger.debug("User {} added exercise in complex {}", user.getLogin(), complex.getId());
            String parameter = "?" + AttrName.COMPLEX_ID + "=" + complexId;
            return new RedirectState("complex/edit.html" + parameter);
        } else {
            String parameter = String.format("?%s=%s",AttrName.COMPLEX_ID, complexId);
            ResponseState state = new RedirectState("complex/add_exercise_in_complex.html"+parameter);
            state.getAttributes().put(AttrName.WARNING_MESSAGE, "message.warning.no_any_record_chosen");

            return state;
        }
    }
}
