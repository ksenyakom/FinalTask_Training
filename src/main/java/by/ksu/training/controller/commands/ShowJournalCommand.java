package by.ksu.training.controller.commands;

import by.ksu.training.controller.DispatcherServlet;
import by.ksu.training.entity.*;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author Kseniya Oznobishina
 * @Date 02.01.2021
 */
public class ShowJournalCommand extends Command {
    private static Logger logger = LogManager.getLogger(DispatcherServlet.class);

    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            int periodDefault = 90;
            AssignedComplexService assignedComplexService = factory.getService(AssignedComplexService.class);
            List<AssignedComplex> list = assignedComplexService.findExecutedForPeriod(periodDefault);
            List<User> users = list.stream()
                    .map(AssignedComplex::getUser)
                    .distinct()
                    .collect(Collectors.toList());
            UserService userService = factory.getService(UserService.class);
            userService.findLogin(users);

            List<Complex> complexes = list.stream()
                    .map(AssignedComplex::getComplex)
                    .distinct()
                    .collect(Collectors.toList());

            ComplexService complexService = factory.getService(ComplexService.class);
            complexService.findTitle(complexes);

            request.setAttribute("lst", list);

        } catch (PersistentException e) {
            logger.error("Error while show execute log", e);
            return new Forward("index.jsp");
        }
        return new Forward("journal.jsp");
    }

    @Override
    public Set<Role> getAllowedRoles() {
        return null;
    }
}
