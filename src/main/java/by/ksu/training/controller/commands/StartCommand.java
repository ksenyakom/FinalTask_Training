package by.ksu.training.controller.commands;

import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//TODO убрать эту команду

public class StartCommand extends Command{
    private static Logger logger = LogManager.getLogger(StartCommand.class);

    @Override
    public ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        return new ForwardState("index.jsp");
    }
}
