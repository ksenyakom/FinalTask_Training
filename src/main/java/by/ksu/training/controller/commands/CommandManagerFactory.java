package by.ksu.training.controller.commands;

import by.ksu.training.service.ServiceFactory;

public class CommandManagerFactory {
    public static CommandManager getManager(ServiceFactory factory) {
        return new CommandManagerImpl(factory);
    }

}
