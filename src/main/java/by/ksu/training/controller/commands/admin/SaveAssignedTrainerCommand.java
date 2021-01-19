package by.ksu.training.controller.commands.admin;

import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.IncorrectFormDataException;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedTrainerService;
import by.ksu.training.service.UserService;
import by.ksu.training.service.validator.AssignedTrainerValidator;
import by.ksu.training.service.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Kseniya Oznobishina
 * @Date 19.01.2021
 */
public class SaveAssignedTrainerCommand extends AdminCommand {
    private static Logger logger = LogManager.getLogger(ShowAssignedTrainerListCommand.class);

    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            Validator<AssignedTrainer> validator = new AssignedTrainerValidator();
            AssignedTrainer assignedTrainer = validator.validate(request);

            AssignedTrainerService atService = factory.getService(AssignedTrainerService.class);
            atService.save(assignedTrainer);

            request.getSession().setAttribute("success_message", "Trainer assigned successfully.");


        } catch (IncorrectFormDataException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute("warning_message", e.getMessage());
            new Forward("assigned_trainer/set.jsp"); //return back
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute("err_message", e.getMessage());
            return null;
        }
        return new Forward("/assigned_trainer/list.html", true);
    }
}
