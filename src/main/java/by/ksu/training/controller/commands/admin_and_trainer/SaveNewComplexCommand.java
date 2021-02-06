package by.ksu.training.controller.commands.admin_and_trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.*;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.validator.ComplexValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Saves entered complex.
 *
 * @Author Kseniya Oznobishina
 * @Date 27.01.2021
 */
public class SaveNewComplexCommand extends AdminAndTrainerCommand {
    private static Logger logger = LogManager.getLogger(SaveNewComplexCommand.class);

    /**
     * Saves entered complex, provides check if title exist.
     *
     * @throws PersistentException if any exception occur in service layout.
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Validator<Complex> validator = new ComplexValidator();
        ComplexService complexService = factory.getService(ComplexService.class);
        Complex complex = null;

        try {
            complex = validator.validate(request);
            boolean existTitle = complexService.checkTitleExist(complex.getTitle());
            if (!existTitle) {
                User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
                complexService.save(complex);
                ResponseState state = new RedirectState("complex/my_complexes.html");
                state.getAttributes().put(AttrName.SUCCESS_MESSAGE, "message.success.complex_creation");
                logger.info("User {} created new complex id = {}", user.getLogin(), complex.getId());
                return state;
            } else {
                ResponseState state = new RedirectState("complex/add.html");
                state.getAttributes().put(AttrName.WARNING_MESSAGE, "message.warning.training_title_already_exist");
                state.getAttributes().put(AttrName.COMPLEX, complex);
                return state;
            }
        } catch (IncorrectFormDataException e) {
            logger.debug("User entered invalid data.", e);
            ResponseState state = new RedirectState("complex/add.html");
            state.getAttributes().put(AttrName.WARNING_MAP, validator.getWarningMap());
            state.getAttributes().put(AttrName.COMPLEX, validator.getInvalid());
            return state;
        }
    }
}
