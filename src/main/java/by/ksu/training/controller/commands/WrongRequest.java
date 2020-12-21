package by.ksu.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** command wrong request*/
public class WrongRequest implements Command {

    private static final Logger LOGGER = LogManager.getLogger(WrongRequest.class);
    @Override
    public void execute() {
        LOGGER.info("Wrong request");

    }
}
