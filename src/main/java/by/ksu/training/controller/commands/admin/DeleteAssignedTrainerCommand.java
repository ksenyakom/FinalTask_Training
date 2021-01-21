package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.commands.common.DeleteUserCommand;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedTrainerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Kseniya Oznobishina
 * @Date 20.01.2021
 */
public class DeleteAssignedTrainerCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(DeleteUserCommand.class);
    private static final String REMOVE = "remove";
    private static final String ACTION = "action";

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        String[] atId = request.getParameterValues(REMOVE);
        try {

            AssignedTrainerService atService  = factory.getService(AssignedTrainerService.class);
            for (String stringId : atId) {
                int id = Integer.parseInt(stringId);
                //TODO Проверкку какую может?
                atService.delete(id);
                request.setAttribute("success_message", "Запись успешно удалена");

            }
            String action = request.getParameter(ACTION);
            String parameter = action == null ? "" : "?action="+action;
            return new ResponseState("/assigned_trainer/list.html" + parameter,true);
        } catch (NumberFormatException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute("warning_message", e.getMessage());
            return new ResponseState("assigned_trainer/list.jsp"); //return back
        }
        catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute("err_message", e.getMessage());
            return null;
        }


    }
}
