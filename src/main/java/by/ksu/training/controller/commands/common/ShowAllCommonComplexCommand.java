package by.ksu.training.controller.commands.common;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.commands.Command;
import by.ksu.training.controller.state.ErrorState;
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
 * @Author Kseniya Oznobishina
 * @Date 13.01.2021
 */
public class ShowAllCommonComplexCommand extends Command {
    private static Logger logger = LogManager.getLogger(ShowAllCommonComplexCommand.class);

    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            ComplexService complexService = factory.getService(ComplexService.class);
            List<Complex> complexes = complexService.findAllCommonComplexMetaData();

            request.setAttribute("lst", complexes);

            return new ForwardState("complex/list.jsp");
        } catch (PersistentException e) {
            logger.error("Exception in command!!!", e);
            request.setAttribute(AttrName.ERROR_MESSAGE,e.getMessage());
            return new ErrorState();
        }

    }
}
