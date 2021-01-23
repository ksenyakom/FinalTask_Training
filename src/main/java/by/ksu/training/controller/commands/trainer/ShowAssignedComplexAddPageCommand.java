package by.ksu.training.controller.commands.trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.UserService;
import by.ksu.training.service.validator.UserValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Kseniya Oznobishina
 * @Date 22.01.2021
 */
public class ShowAssignedComplexAddPageCommand  extends TrainerCommand{
    private static Logger logger = LogManager.getLogger(ShowAssignedComplexAddPageCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<User> validator = new UserValidator();
            Integer visitorId = validator.validateId(request);

            User visitor = new User(visitorId);
            UserService userService = factory.getService(UserService.class);
            userService.findLogin(List.of(visitor));


            ComplexService complexService = factory.getService(ComplexService.class);
            List<Complex> complexes = complexService.findComplexesMetaDataByUser(visitor);

            request.setAttribute(AttrName.VISITOR, visitor);
            request.setAttribute("lst",complexes);

            return new ForwardState("assigned_complex/add.jsp");
        }  catch (IncorrectFormDataException e) {
            request.setAttribute(AttrName.WARNING_MESSAGE, "You have entered incorrect data: " + e.getMessage());
            return new ForwardState("assigned_complex/list.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.WARNING_MESSAGE, e.getMessage());
            return new ErrorState();
        }
    }
}
