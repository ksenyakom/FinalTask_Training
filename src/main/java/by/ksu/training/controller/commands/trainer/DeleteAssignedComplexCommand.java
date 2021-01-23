package by.ksu.training.controller.commands.trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.commands.admin.DeleteSubscriptionCommand;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.RedirectState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.SubscriptionService;
import by.ksu.training.service.validator.AssignedComplexValidator;
import by.ksu.training.service.validator.SubscriptionValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Kseniya Oznobishina
 * @Date 23.01.2021
 */
public class DeleteAssignedComplexCommand extends TrainerCommand{
    private static Logger logger = LogManager.getLogger(DeleteAssignedComplexCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
       try{
        Validator<AssignedComplex> validator = new AssignedComplexValidator();
        List<Integer> listId = validator.validateRemoveId(request);
        AssignedComplexService service = factory.getService(AssignedComplexService.class);
        for (Integer id : listId) {
            service.delete(id);
        }

        request.getSession().setAttribute(AttrName.SUCCESS_MESSAGE, "message.success.delete");
        String userId = request.getParameter(AttrName.USER_ID);
        String parameter = userId == null ? "" : "?" + AttrName.USER_ID + "=" + userId;
        return new RedirectState("assigned_complex/list.html" + parameter);

    } catch (
    IncorrectFormDataException e) {
        logger.error("Exception in command!!!", e);
        request.setAttribute(AttrName.WARNING_MESSAGE, e.getMessage());
        return new ForwardState("assigned_complex/list.jsp");
    } catch (
    PersistentException e) {
        logger.error("Exception in command!!!", e);
        request.setAttribute(AttrName.ERROR_MESSAGE, e.getMessage());
        return new ErrorState();
    }
    }
}
