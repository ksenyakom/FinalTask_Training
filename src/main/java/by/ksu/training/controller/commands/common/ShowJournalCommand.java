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
 * Prepares data to show on page "journal".
 *
 * @Author Kseniya Oznobishina
 * @Date 02.01.2021
 */
public class ShowJournalCommand extends Command {
    private static Logger logger = LogManager.getLogger(ShowJournalCommand.class);
    /**
     * Default period for journal.
     */
    private static final int periodDefault = 90;

    /**
     * Prepares data to show on page "journal".
     * list of executed complexes by users for last periodDefault.
     */
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        AssignedComplexService assignedComplexService = factory.getService(AssignedComplexService.class);
        List<AssignedComplex> list = assignedComplexService.findExecutedForPeriod(periodDefault);
        request.setAttribute("lst", list);

        return new ForwardState("journal.jsp");
    }

    @Override
    public Set<Role> getAllowedRoles() {
        return null;
    }
}
