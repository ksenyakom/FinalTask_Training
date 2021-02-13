package by.ksu.training.controller.commands.admin_and_trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.Exercise;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.validator.ExerciseValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;

/**
 * Deletes exercises from complex.
 *
 * @Author Kseniya Oznobishina
 * @Date 27.01.2021
 */
public class ExerciseInComplexDeleteCommand extends AdminAndTrainerCommand {
    private static Logger logger = LogManager.getLogger(ExerciseInComplexDeleteCommand.class);

    /**
     * Deletes exercises (with numbers from parameter "removeId")
     * from complex (with id from parameter "complexId").
     *
     * @throws PersistentException if any exception occur in service layout.
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        EntityValidator<Exercise> validator = new ExerciseValidator();
        ComplexService complexService = factory.getService(ComplexService.class);
        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);

        Integer complexId = validator.validateIntAttr(AttrName.COMPLEX_ID, request);
        String parameter = "?" + AttrName.COMPLEX_ID + "=" + complexId;
        ResponseState state = new RedirectState("complex/edit.html" + parameter);

        List<Integer> removeIndex = validator.validateListId(AttrName.REMOVE, request);
        if (!removeIndex.isEmpty()) {
            Complex complex = complexService.findById(complexId);
            boolean allowed = complexService.checkEditAllowed(user, complex);
            if (!allowed) {
                throw new PersistentException(String.format("You are not allowed to edit this record: %s",
                        complex == null ? "no such record" : complex.getTitle()));
            }

            removeIndex.sort(Comparator.reverseOrder());
            for (Integer index : removeIndex) {
                complex.deleteComplexUnit(index - 1);
            }
            complexService.save(complex);
            logger.debug("User {} removed units {} from complex id = {}", user.getLogin(), removeIndex, complex.getId());

            state.getAttributes().put(AttrName.SUCCESS_MESSAGE, "message.success.delete");
        } else {
            state.getAttributes().put(AttrName.WARNING_MAP, validator.getWarningMap());
        }
        return state;
    }
}
