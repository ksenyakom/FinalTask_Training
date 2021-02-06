package by.ksu.training.controller.commands.admin_and_trainer;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.*;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedTrainerService;
import by.ksu.training.service.ComplexService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * A command prepares data for "my complexes" page.
 *
 * @Author Kseniya Oznobishina
 * @Date 25.01.2021
 */
public class ShowMyComplexesCommand extends AdminAndTrainerCommand {
    private static Logger logger = LogManager.getLogger(ShowMyComplexesCommand.class);

    /**
     * A command prepares data for my/complexes page:
     * for admin - all metadata of all complexes existing,
     * for trainer - all common complexes and individual complexes for his visitors.
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        ComplexService complexService = factory.getService(ComplexService.class);
        User user = (User) request.getSession().getAttribute(AttrName.AUTHORIZED_USER);
        List<Complex> complexes = null;

        if (user.getRole() == Role.ADMINISTRATOR) {
            complexes = complexService.findAllMetaData();
        } else if (user.getRole() == Role.TRAINER) {
            AssignedTrainerService trainerService = factory.getService(AssignedTrainerService.class);

            List<User> visitorsOfTrainer = trainerService.findVisitorsByTrainer(user);
            complexes = complexService.findAllCommonComplexMetaData();
            List<Complex> complexOfTrainer = complexService.findIndividualComplexMetaDataByUsers(visitorsOfTrainer);
            complexes.addAll(complexOfTrainer);
        }
        if (complexes != null) {
            request.setAttribute("lst", complexes);
        }
        return new ForwardState("complex/my_complexes.jsp");
    }
}
