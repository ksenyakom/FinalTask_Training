package by.ksu.training.controller.commands;


import by.ksu.training.controller.commands.admin.*;
import by.ksu.training.controller.commands.common.*;
import by.ksu.training.controller.commands.visitor.ShowSubscriptionBuyCommand;
import by.ksu.training.controller.commands.visitor.ShowVisitorAssignedComplexesCommand;
import by.ksu.training.controller.commands.visitor.ShowVisitorSubscriptionCommand;
import by.ksu.training.controller.commands.visitor.SubscriptionSaveNewCommand;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * contain map of commands
 */
public final class CommandProvider {
    private final Map<Class<? extends Command>, Command> repository = new ConcurrentHashMap<>();

    public CommandProvider() {
        repository.put(StartCommand.class, new StartCommand());
        repository.put(ChangeLanguageCommand.class, new ChangeLanguageCommand());

        repository.put(LoginCommand.class, new LoginCommand());
        repository.put(ShowLoginCommand.class, new ShowLoginCommand());
        repository.put(LogoutCommand.class, new LogoutCommand());
        repository.put(ShowUserEditLoginCommand.class, new ShowUserEditLoginCommand());
        repository.put(SaveChangedLoginCommand.class, new SaveChangedLoginCommand());

        repository.put(RegistrationCommand.class, new RegistrationCommand());
        repository.put(ShowRegistrationCommand.class, new ShowRegistrationCommand());

        repository.put(ShowUsersByRoleCommand.class, new ShowUsersByRoleCommand());
        repository.put(DeleteUserCommand.class, new DeleteUserCommand());

        repository.put(ShowJournalCommand.class, new ShowJournalCommand());

        repository.put(ShowAllCommonComplexCommand.class, new ShowAllCommonComplexCommand());
        repository.put(ShowVisitorAssignedComplexesCommand.class, new ShowVisitorAssignedComplexesCommand());
        repository.put(ShowExecuteComplexCommand.class, new ShowExecuteComplexCommand());

        repository.put(SubscriptionDeleteCommand.class, new SubscriptionDeleteCommand());
        repository.put(ShowSubscriptionEditCommand.class, new ShowSubscriptionEditCommand());
        repository.put(SubscriptionUpdateCommand.class, new SubscriptionUpdateCommand());
        repository.put(ShowAllSubscriptionsCommand.class, new ShowAllSubscriptionsCommand());
        repository.put(ShowSubscriptionBuyCommand.class, new ShowSubscriptionBuyCommand());
        repository.put(ShowVisitorSubscriptionCommand.class, new ShowVisitorSubscriptionCommand());
        repository.put(SubscriptionSaveNewCommand.class, new SubscriptionSaveNewCommand());

        repository.put(SavePersonChangeCommand.class, new SavePersonChangeCommand());
        repository.put(ShowPersonEditCommand.class, new ShowPersonEditCommand());

        repository.put(ShowAssignedTrainerListCommand.class, new ShowAssignedTrainerListCommand());
        repository.put(ShowAssignedTrainerSetPageCommand.class, new ShowAssignedTrainerSetPageCommand());
        repository.put(SaveAssignedTrainerCommand.class, new SaveAssignedTrainerCommand());
        repository.put(DeleteAssignedTrainerCommand.class, new DeleteAssignedTrainerCommand());

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
