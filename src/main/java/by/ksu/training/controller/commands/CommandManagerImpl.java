package by.ksu.training.controller.commands;

import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommandManagerImpl implements CommandManager {
    private ServiceFactory factory;

    public CommandManagerImpl(ServiceFactory factory) {
        this.factory = factory;
    }

    @Override
    public ResponseState execute(Command command, HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        command.setFactory(factory);
        return command.exec(request,response);
    }

    @Override
    public void close() {
        factory.close();
    }
}
