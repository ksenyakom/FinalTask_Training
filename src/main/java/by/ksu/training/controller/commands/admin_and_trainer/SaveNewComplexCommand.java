package by.ksu.training.controller.commands.admin_and_trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.*;
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
 * Saves entered complex, provides check if title exist.
 *
 * @Author Kseniya Oznobishina
 * @Date 27.01.2021
 */
public class SaveNewComplexCommand extends AdminAndTrainerCommand {
    private static Logger logger = LogManager.getLogger(SaveNewComplexCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<Complex> validator = new ComplexValidator();
            Complex newComplex = validator.validate(request); // only title for now

            ComplexService complexService = factory.getService(ComplexService.class);

            boolean existTitle = complexService.checkTitleExist(newComplex.getTitle());
            if (existTitle) {
                ResponseState state = new RedirectState("complex/add.html");
                state.getAttributes().put(AttrName.WARNING_MESSAGE, "message.warning.training_title_already_exist");
                state.getAttributes().put(AttrName.TITLE, newComplex.getTitle());
                return state;
            } else {
                User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
                if (user.getRole() == Role.TRAINER) {
                    int visitorId = validator.validateIntAttr(AttrName.VISITOR_ID, request);
                    newComplex.setVisitorFor(new User(visitorId));
                    newComplex.setTrainerDeveloped(user);
                }
                complexService.save(newComplex);
                ResponseState state = new RedirectState("complex/my_complexes.html");
                state.getAttributes().put(AttrName.SUCCESS_MESSAGE, "message.success.complex_creation");
                //TODO change
                logger.info("!!!!!!!!!!!!!!User {} created new complex id = {}", user.getLogin(), newComplex.getId() );
                return state;
            }

        } catch (IncorrectFormDataException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.WARNING_MESSAGE, e.getMessage());
            return new ForwardState("complex/add.html");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }

}
