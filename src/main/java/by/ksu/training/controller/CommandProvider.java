package by.ksu.training.controller;

import by.ksu.training.controller.commands.*;

import java.util.HashMap;
import java.util.Map;

/**
 * contain map of commands
 */
public final class CommandProvider {
    private final Map<CommandType, Command> repository = new HashMap<>();

    CommandProvider() {
//        repository.put(CommandTypeNamesMain.LANGUAGE, new LanguageChoose());
//        repository.put(CommandTypeNamesMain.WRONG_REQUEST, new WrongRequest());
    }

    /**
     * returns object of command class which correspond to command name
     */
    Command getCommand(CommandType name) {
       Command command = null;
        try {
            command = repository.get(name);
            return command;
        } catch (IllegalArgumentException e) {
            return repository.get(CommandTypeNamesMain.WRONG_REQUEST);
        }
    }

}
