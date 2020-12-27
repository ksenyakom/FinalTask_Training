package by.ksu.training.controller.commands;

import by.ksu.training.entity.Complex;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ComplexService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class StartCommand extends Command{
    private static Logger logger = LogManager.getLogger(StartCommand.class);

    @Override
    Forward exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            ComplexService complexService = factory.getService(ComplexService.class);
            List<Complex>  complexList = complexService.findAll(); //TODO find all not individual
            request.setAttribute("lst", complexList);
        } catch (PersistentException e) {
            logger.error("Exception in StartCommand",e);
        }
        return new Forward("index.jsp");

    }
}
