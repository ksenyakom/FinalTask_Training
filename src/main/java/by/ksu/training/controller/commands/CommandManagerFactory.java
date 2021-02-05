package by.ksu.training.controller.commands;

import by.ksu.training.dao.TransactionFactoryImpl;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ServiceFactory;
import by.ksu.training.service.ServiceFactoryImpl;

public class CommandManagerFactory {
    public static CommandManager getManager() throws PersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());

        return new CommandManagerImpl(serviceFactory);
    }

}
