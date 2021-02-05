package by.ksu.training.controller.commands.admin_and_trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Complex;
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
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Validator<Complex> validator = new ComplexValidator();
        ComplexService complexService = factory.getService(ComplexService.class);

        List<Integer> listId = validator.validateListId(AttrName.REMOVE, request);
        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
        ResponseState state = new RedirectState("complex/my_complexes.html");

        if (!listId.isEmpty()) {
            for (Integer id : listId) {
                Complex complex = complexService.findById(id);
                boolean allowed = complexService.checkEditAllowed(user, complex);
                if (!allowed) {
                    throw new PersistentException(String.format("You are not allowed to edit this record: %s",
                            complex == null ? "no such record" : complex.getTitle()));
                }

                complexService.delete(id);
                logger.debug("User {} deleted complex(s) id = {}", user.getLogin(), id);
            }
            state.getAttributes().put(AttrName.SUCCESS_MESSAGE, "message.success.delete");
        } else {
            state.getAttributes().put(AttrName.WARNING_MAP, validator.getWarningMap());
        }
        return state;
    }
}
