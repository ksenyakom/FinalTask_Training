package by.ksu.training.controller.commands.admin_and_trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.ExerciseService;
import by.ksu.training.service.validator.ComplexValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Kseniya Oznobishina
 * @Date 27.01.2021
 */
public class ShowAddExerciseInComplex  extends AdminAndTrainerCommand{
    private static Logger logger = LogManager.getLogger(ShowAddExerciseInComplex.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<Complex> complexValidator = new ComplexValidator();
            Integer complexId = complexValidator.validateId(request);

            ExerciseService exerciseService = factory.getService(ExerciseService.class);
            List<Exercise> exercises = exerciseService.findAll();
            request.setAttribute("lst", exercises);
            request.setAttribute(AttrName.COMPLEX_ID, complexId);

            return new ForwardState("complex/add_exercise_in_complex.jsp");
        } catch (IncorrectFormDataException e) {
            request.setAttribute(AttrName.WARNING_MESSAGE, "You have entered incorrect data: " + e.getMessage());
            return new ForwardState("complex/edit.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage()); //TODO проверить чтобы везде был error
            return new ErrorState();
        }
    }
}
