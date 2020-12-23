package by.ksu.training.controller.commands;


import java.util.HashMap;
import java.util.Map;

/**
 * contain map of commands
 */
public final class CommandProvider {
    private final Map<Class<? extends Command>, Command> repository = new HashMap<>();

    public CommandProvider() {
        repository.put(MainCommand.class, new MainCommand());
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
