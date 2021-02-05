package by.ksu.training.controller.commands.admin_and_trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ExerciseService;
import by.ksu.training.service.validator.ComplexValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Prepares data (list of exercises) to show on page "add exercise in complex".
 *
 * @throws PersistentException - if complexId is not valid or any exception
 *                               occure while receiving data from base.
 * @Author Kseniya Oznobishina
 * @Date 27.01.2021
 */
public class ShowAddExerciseInComplex extends AdminAndTrainerCommand {
    private static Logger logger = LogManager.getLogger(ShowAddExerciseInComplex.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Validator<Complex> complexValidator = new ComplexValidator();
        ExerciseService exerciseService = factory.getService(ExerciseService.class);

        Integer complexId = complexValidator.validateId(request);
        List<Exercise> exercises = exerciseService.findAll();
        request.setAttribute("lst", exercises);
        request.setAttribute(AttrName.COMPLEX_ID, complexId);

        return new ForwardState("complex/add_exercise_in_complex.jsp");
    }
}
