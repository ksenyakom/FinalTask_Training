package by.ksu.training.controller.commands;

import by.ksu.training.entity.Trainer;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.TrainerService;
import by.ksu.training.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MainCommand extends Command {
    private static Logger logger = LogManager.getLogger(MainCommand.class);

    @Override
    Forward exec(HttpServletRequest request, HttpServletResponse response) {
        Forward forward = null;
        try {
            UserService userService = factory.getService(UserService.class);

            TrainerService trainerService = factory.getService(TrainerService.class);

            User user = userService.findByIdentity(1);
            List<Trainer> trainerList = trainerService.findAll();
            request.setAttribute("tList", trainerList);

            request.setAttribute("user", user);

             forward = new Forward("/WEB-INF/jsp/mainTest.jsp");



        } catch (PersistentException e) {
            logger.error("Exception in mainCommand",e);
        }
        return forward;
    }
}
