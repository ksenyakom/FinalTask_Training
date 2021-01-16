package by.ksu.training.controller.commands;


import by.ksu.training.controller.commands.admin.*;
import by.ksu.training.controller.commands.common.*;

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
        repository.put(ShowJournalCommand.class, new ShowJournalCommand());
        repository.put(ShowLoginCommand.class, new ShowLoginCommand());
        repository.put(ShowRegistrationCommand.class, new ShowRegistrationCommand());
        repository.put(LogoutCommand.class, new LogoutCommand());
        repository.put(UserDeleteCommand.class, new UserDeleteCommand());
        repository.put(ChangeLanguageCommand.class, new ChangeLanguageCommand());
        repository.put(ShowAllCommonComplexCommand.class, new ShowAllCommonComplexCommand());
        repository.put(ShowAllSubscriptionsCommand.class, new ShowAllSubscriptionsCommand());
        repository.put(SubscriptionDeleteCommand.class, new SubscriptionDeleteCommand());
        repository.put(ShowSubscriptionEditCommand.class, new ShowSubscriptionEditCommand());
        repository.put(SubscriptionUpdateCommand.class, new SubscriptionUpdateCommand());

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
