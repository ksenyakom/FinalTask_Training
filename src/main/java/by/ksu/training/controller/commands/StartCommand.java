package by.ksu.training.controller.commands;

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
//TODO убрать эту команду наверное и index.html

public class StartCommand extends Command{
    private static Logger logger = LogManager.getLogger(StartCommand.class);

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        return new Forward("/index.jsp",true);

    }

    @Override
    public Set<Role> getAllowedRoles() {
        return null;
    }
}
