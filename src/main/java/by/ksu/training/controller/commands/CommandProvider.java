package by.ksu.training.controller.commands;


import by.ksu.training.controller.commands.admin.ShowUsersByRoleCommand;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * contain map of commands
 */
public final class CommandProvider {
    private final Map<Class<? extends Command>, Command> repository = new ConcurrentHashMap<>();

    public CommandProvider() {
        repository.put(StartCommand.class, new StartCommand());
        repository.put(LoginCommand.class, new LoginCommand());
        repository.put(ShowUsersByRoleCommand.class, new ShowUsersByRoleCommand());
        repository.put(RegistrationCommand.class, new RegistrationCommand());
        repository.put(ShowJournal.class, new ShowJournal());
        repository.put(ShowLoginCommand.class, new ShowLoginCommand());
        repository.put(ShowRegistrationCommand.class, new ShowRegistrationCommand());
        repository.put(LogoutCommand.class, new LogoutCommand());
        repository.put(UserDeleteCommand.class, new UserDeleteCommand());
    }

    /**
     * returns object of command class which correspond to command name
     */
    public Command getCommand(Class<? extends Command> clazz) {
        Command command = null;
        try {
            command = repository.get(clazz);
            return command;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
