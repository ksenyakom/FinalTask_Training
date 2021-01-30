package by.ksu.training.controller.commands.trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.commands.visitor.ShowVisitorAssignedComplexesCommand;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.UserService;
import by.ksu.training.service.validator.UserValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author Kseniya Oznobishina
 * @Date 22.01.2021
 */
public class ShowAssignedComplexesCommand extends TrainerCommand {
    private static Logger logger = LogManager.getLogger(ShowAssignedComplexesCommand.class);
    private static final int DAYS = 60;

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
//        try {
            Validator<User> validator = new UserValidator();
            Integer visitorId = validator.validateId(request);
            User visitor = new User(visitorId);
            AssignedComplexService acService = factory.getService(AssignedComplexService.class);
            List<AssignedComplex> assignedComplexes = acService.findUnexecutedByUser(visitor);
            List<AssignedComplex> executedComplexes = acService.findExecutedByUserForPeriod(visitor, DAYS);
            assignedComplexes.addAll(executedComplexes);
            UserService userService = factory.getService(UserService.class);
            userService.findLogin(List.of(visitor));

            request.setAttribute(AttrName.VISITOR, visitor);
            request.setAttribute("lst", assignedComplexes);
            return new ForwardState("assigned_complex/list.jsp");
//        } catch (PersistentException | IncorrectFormDataException e) {
//            logger.error("Exception in command!!!!", e);
//            request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
//            return new ErrorState();
//        }
    }

}
