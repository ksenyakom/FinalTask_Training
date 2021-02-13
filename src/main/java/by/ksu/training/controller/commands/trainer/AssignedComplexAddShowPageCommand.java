package by.ksu.training.controller.commands.trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.UserService;
import by.ksu.training.service.validator.UserValidator;
import by.ksu.training.service.validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Prepares data to show on page "assignedComplex add".
 *
 * @Author Kseniya Oznobishina
 * @Date 22.01.2021
 */
public class AssignedComplexAddShowPageCommand extends TrainerCommand {
    private static Logger logger = LogManager.getLogger(AssignedComplexAddShowPageCommand.class);

    /**
     * Prepares data to show on page "assignedComplex add":
     * all complexes which visitor with id "vistitorId" allowed to execute.
     */

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        EntityValidator<User> validator = new UserValidator();
        UserService userService = factory.getService(UserService.class);
        ComplexService complexService = factory.getService(ComplexService.class);

        Integer visitorId = validator.validateId(request);
        User visitor = new User(visitorId);
        userService.findLogin(List.of(visitor));
        List<Complex> complexes = complexService.findComplexesMetaDataByUser(visitor);

        request.setAttribute(AttrName.VISITOR, visitor);
        request.setAttribute("lst", complexes);

        return new ForwardState("assigned_complex/add.jsp");
    }
}
