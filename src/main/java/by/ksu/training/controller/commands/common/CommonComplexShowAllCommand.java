package by.ksu.training.controller.commands.common;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.Role;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ComplexService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * Prepares data of common complexes to show on page.
 *
 * @Author Kseniya Oznobishina
 * @Date 13.01.2021
 */
public class CommonComplexShowAllCommand extends Command {
    private static Logger logger = LogManager.getLogger(CommonComplexShowAllCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        ComplexService complexService = factory.getService(ComplexService.class);
        List<Complex> complexes = complexService.findAllCommonComplexMetaData();

        request.setAttribute("lst", complexes);

        return new ForwardState("complex/list.jsp");
    }

    @Override
    public Set<Role> getAllowedRoles() {
        return null;
    }
}
