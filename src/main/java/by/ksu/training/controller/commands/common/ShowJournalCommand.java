package by.ksu.training.controller.commands.common;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.commands.Command;
import by.ksu.training.controller.state.ErrorState;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.*;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.UserService;
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
    private static Logger logger = LogManager.getLogger(ShowJournalCommand.class);
    private static final int periodDefault = 90;

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            AssignedComplexService assignedComplexService = factory.getService(AssignedComplexService.class);
            List<AssignedComplex> list = assignedComplexService.findExecutedForPeriod(periodDefault);
//           remove to service
            List<User> users = list.stream()
                    .map(AssignedComplex::getVisitor)
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
//
            request.setAttribute("lst", list);

            return new ForwardState("journal.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE,e.getMessage());
            return new ErrorState();
        }

    }

    @Override
    public Set<Role> getAllowedRoles() {
        return null;
    }
}
