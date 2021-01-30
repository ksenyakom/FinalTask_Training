package by.ksu.training.controller.commands;


import by.ksu.training.controller.commands.admin.*;
import by.ksu.training.controller.commands.admin_and_trainer.*;
import by.ksu.training.controller.commands.authorized_user.*;
import by.ksu.training.controller.commands.common.*;
import by.ksu.training.controller.commands.trainer.*;
import by.ksu.training.controller.commands.visitor.*;

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
        repository.put(UpdateDateExecutedAssignedComplexCommand.class, new UpdateDateExecutedAssignedComplexCommand());

        repository.put(DeleteSubscriptionCommand.class, new DeleteSubscriptionCommand());
        repository.put(ShowSubscriptionEditCommand.class, new ShowSubscriptionEditCommand());
        repository.put(UpdateSubscriptionCommand.class, new UpdateSubscriptionCommand());
        repository.put(ShowAllSubscriptionsCommand.class, new ShowAllSubscriptionsCommand());
        repository.put(ShowSubscriptionBuyCommand.class, new ShowSubscriptionBuyCommand());
        repository.put(ShowVisitorSubscriptionCommand.class, new ShowVisitorSubscriptionCommand());
        repository.put(SaveNewSubscriptionCommand.class, new SaveNewSubscriptionCommand());
        repository.put(ShowVisitorsByTrainerCommand.class, new ShowVisitorsByTrainerCommand());


        repository.put(SavePersonChangeCommand.class, new SavePersonChangeCommand());
        repository.put(ShowPersonEditCommand.class, new ShowPersonEditCommand());

        repository.put(ShowAssignedTrainerListCommand.class, new ShowAssignedTrainerListCommand());
        repository.put(ShowAssignedTrainerSetPageCommand.class, new ShowAssignedTrainerSetPageCommand());
        repository.put(SaveAssignedTrainerCommand.class, new SaveAssignedTrainerCommand());
        repository.put(DeleteAssignedTrainerCommand.class, new DeleteAssignedTrainerCommand());

        repository.put(ShowAssignedComplexesCommand.class, new ShowAssignedComplexesCommand());
        repository.put(ShowAssignedComplexAddPageCommand.class, new ShowAssignedComplexAddPageCommand());
        repository.put(AddAssignedComplexCommand.class, new AddAssignedComplexCommand());
        repository.put(DeleteAssignedComplexCommand.class, new DeleteAssignedComplexCommand());
        repository.put(ShowAssignedComplexEditPageCommand.class, new ShowAssignedComplexEditPageCommand());
        repository.put(UpdateAssignedComplexCommand.class, new UpdateAssignedComplexCommand());

        repository.put(ShowExerciseListCommand.class, new ShowExerciseListCommand());
        repository.put(DeleteExerciseCommand.class, new DeleteExerciseCommand());
        repository.put(ShowExerciseAddPageCommand.class, new ShowExerciseAddPageCommand());
        repository.put(AddExerciseCommand.class, new AddExerciseCommand());
        repository.put(ShowExerciseEditCommand.class, new ShowExerciseEditCommand());
        repository.put(UpdateExerciseCommand.class, new UpdateExerciseCommand());

        repository.put(ShowMyComplexesCommand.class, new ShowMyComplexesCommand());
        repository.put(DeleteComplexCommand.class, new DeleteComplexCommand());
        repository.put(ShowEditComplexPageCommand.class, new ShowEditComplexPageCommand());
        repository.put(ShowAddExerciseInComplex.class, new ShowAddExerciseInComplex());
        repository.put(AddExerciseInComplexCommand.class, new AddExerciseInComplexCommand());
        repository.put(DeleteExerciseInComplexCommand.class, new DeleteExerciseInComplexCommand());
        repository.put(UpdateEditedComplexCommand.class, new UpdateEditedComplexCommand());;
        repository.put(ShowAddComplexPageCommand.class, new ShowAddComplexPageCommand());;
        repository.put(SaveNewComplexCommand.class, new SaveNewComplexCommand());;




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
