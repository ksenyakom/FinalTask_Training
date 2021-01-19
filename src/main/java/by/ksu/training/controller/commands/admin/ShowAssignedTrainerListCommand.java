package by.ksu.training.controller.commands.admin;

import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedTrainerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Kseniya Oznobishina
 * @Date 19.01.2021
 */
public class ShowAssignedTrainerListCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(ShowAssignedTrainerListCommand.class);
    private static final String ALL = "all";
    private static final String ACTIVE = "active";
    private static final String VISITORS_WITHOUT_TRAINER = "visitors_without_trainer";
    private static final String ACTION="action";


    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            AssignedTrainerService atService = factory.getService(AssignedTrainerService.class);
            String action = request.getParameter(ACTION);
            if (action != null) {
                List<AssignedTrainer> list = action.equals(ALL) ?
                        atService.findAll() :
                        action.equals(ACTIVE) ?
                        atService.findAllActive() :
                        atService.findAllWithoutTrainer();

                request.setAttribute("lst", list);
            }
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute("err_message", e.getMessage());
            return null;
        }
        return new Forward("/assigned_trainer/list.jsp");
    }
}
