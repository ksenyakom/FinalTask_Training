package by.ksu.training.controller.commands.admin_and_trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.validator.ComplexValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Prepares data  to show on page "edit complex".
 *
 * @Author Kseniya Oznobishina
 * @Date 27.01.2021
 */
public class ComplexEditShowPageCommand extends AdminAndTrainerCommand {
    private static Logger logger = LogManager.getLogger(ComplexEditShowPageCommand.class);

    /**
     * Prepares data  to show on page "edit complex":
     * data of complex with id came in parameter complexId in request.
     * Performs check if edit allowed for this user.
     */

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        EntityValidator<Complex> complexValidator = new ComplexValidator();
        ComplexService complexService = factory.getService(ComplexService.class);
        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);

        Integer id = complexValidator.validateId(request);
        Complex complex = complexService.findById(id);
        boolean allowed = complexService.checkEditAllowed(user, complex);
        if (!allowed) {
            throw new PersistentException(String.format("You are not allowed to edit this record: %s",
                    complex == null ? "no such record" : complex.getTitle()));
        }
        request.setAttribute(AttrName.COMPLEX, complex);
        return new ForwardState("complex/edit.jsp");
    }
}
