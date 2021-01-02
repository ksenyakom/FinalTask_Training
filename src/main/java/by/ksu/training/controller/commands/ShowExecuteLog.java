package by.ksu.training.controller.commands;

import by.ksu.training.controller.DispatcherServlet;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Role;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * @Author Kseniya Oznobishina
 * @Date 02.01.2021
 */
public class ShowExecuteLog extends Command{
    private static Logger logger = LogManager.getLogger(DispatcherServlet.class);

    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            int periodDefault = 90;
            AssignedComplexService assignedComplexService = factory.getService(AssignedComplexService.class);
            List<AssignedComplex> list = assignedComplexService.findExecutedForPeriod(periodDefault);
            request.setAttribute("lst", list);

        } catch (PersistentException e) {
            logger.error("Error while show execute log",e);
            return new Forward("index.jsp");
        }
        return new Forward("executed.jsp");
    }

    @Override
    public Set<Role> getAllowedRoles() {
        return null;
    }
}
