package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedTrainerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Prepares data to show on list of trainer assignments for users.
 *
 * @Author Kseniya Oznobishina
 * @Date 19.01.2021
 */
public class ShowAssignedTrainerListCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(ShowAssignedTrainerListCommand.class);

    /**
     * Prepares data to show on list of trainer assignments for users,
     * all assignments or only active(means for users
     * with active subscription).
     *
     * @throws PersistentException if any exception occur in service layout.
     * @see by.ksu.training.entity.AssignedTrainer
     */

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        AssignedTrainerService atService = factory.getService(AssignedTrainerService.class);

        String action = request.getParameter(AttrName.ACTION);
        if (action != null) {
            List<AssignedTrainer> list = action.equals(AttrName.ALL)
                    ? atService.findAll()
                    : atService.findAllActive();

            request.setAttribute("lst", list);
            request.setAttribute(AttrName.ACTION, action);
        }
        return new ForwardState("assigned_trainer/list.jsp");
    }
}
