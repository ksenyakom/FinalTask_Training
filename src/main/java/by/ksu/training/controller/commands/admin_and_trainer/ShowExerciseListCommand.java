package by.ksu.training.controller.commands.admin_and_trainer;

import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ExerciseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Prepares data to show of page "exercise list".
 *
 * @Author Kseniya Oznobishina
 * @Date 23.01.2021
 */
public class ShowExerciseListCommand extends AdminAndTrainerCommand {
    private static Logger logger = LogManager.getLogger(ShowExerciseListCommand.class);

    /**
     * Prepares data  to show on page "exercise list":
     * list of existing exercises.
     * Pagination used to show list exercises.
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        final int recordsPerPage = 5;
        ExerciseService exerciseService = factory.getService(ExerciseService.class);

        //count number of pages
        String numberOfPages = request.getParameter("noOfPages");
        if (numberOfPages == null) {
            int count = exerciseService.findTotalCount();
            int noOfPages = count / recordsPerPage + (count % recordsPerPage > 0 ? 1 : 0);
            numberOfPages = "" + noOfPages;
        }

        // receive currentPage number
        String page = request.getParameter("currentPage");
        int currentPage;
        if (page == null) {
            currentPage = 1;
        } else {
            try {
                currentPage = Integer.parseInt(page);
            } catch (NumberFormatException e) {
                throw new PersistentException("Current page number can not be read, found:" + page);
            }
        }
        request.setAttribute("noOfPages", numberOfPages);                                   //1
        request.setAttribute("currentPage", currentPage);                                   //2
        request.setAttribute("recordsPerPage", recordsPerPage);                             //3
        List<Exercise> exerciseList = exerciseService.find(currentPage, recordsPerPage);
        request.setAttribute("lst", exerciseList);
        logger.debug("User looks page exercises/list");

        return new ForwardState("exercise/list.jsp");
    }
}
