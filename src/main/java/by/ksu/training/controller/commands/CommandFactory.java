package by.ksu.training.controller.commands;


import by.ksu.training.controller.commands.admin.*;
import by.ksu.training.controller.commands.admin_and_trainer.*;
import by.ksu.training.controller.commands.authorized_user.*;
import by.ksu.training.controller.commands.common.*;
import by.ksu.training.controller.commands.trainer.*;
import by.ksu.training.controller.commands.visitor.*;

/**
 *
 */
public final class CommandFactory {

    private Command getCommandByName(CommandName commandName) {
        switch (commandName) {
            case START: return new StartCommand();

            case CHANGE_LANGUAGE: return  new ChangeLanguageCommand();
            case LOGIN: return  new LoginCommand();
            case SHOW_LOGIN: return  new ShowLoginCommand();
            case LOGOUT: return  new LogoutCommand();
            case SHOW_USER_EDIT_LOGIN: return  new ShowUserEditLoginCommand();
            case SAVE_CHANGED_LOGIN: return  new SaveChangedLoginCommand();
            case REGISTRATION: return  new RegistrationCommand();
            case SHOW_REGISTRATION: return  new ShowRegistrationCommand();
            case SHOW_USERS_BY_ROLE: return  new ShowUsersByRoleCommand();
            case DELETE_USER: return  new DeleteUserCommand();

            case SHOW_JOURNAL: return  new ShowJournalCommand();

            case SHOW_ALL_COMMON_COMPLEX: return  new ShowAllCommonComplexCommand();
            case SHOW_VISITOR_ASSIGNED_COMPLEXES: return  new ShowVisitorAssignedComplexesCommand();
            case SHOW_EXECUTE_COMPLEX: return  new ShowExecuteComplexCommand();
            case UPDATE_DATE_EXECUTED_ASSIGNED_COMPLEX: return  new UpdateDateExecutedAssignedComplexCommand();

            case DELETE_SUBSCRIPTION: return  new DeleteSubscriptionCommand();
            case SHOW_SUBSCRIPTION_EDIT: return  new ShowSubscriptionEditCommand();
            case UPDATE_SUBSCRIPTION: return  new UpdateSubscriptionCommand();
            case SHOW_ALL_SUBSCRIPTIONS: return  new ShowAllSubscriptionsCommand();
            case SHOW_SUBSCRIPTION_BUY: return  new ShowSubscriptionBuyCommand();
            case SHOW_VISITOR_SUBSCRIPTION: return  new ShowVisitorSubscriptionCommand();
            case SAVE_NEW_SUBSCRIPTION: return  new SaveNewSubscriptionCommand();
            case SHOW_VISITORS_BY_TRAINER: return  new ShowVisitorsByTrainerCommand();


            case UPDATE_PERSON: return  new UpdatePersonCommand();
            case SHOW_PERSON_EDIT: return  new ShowPersonEditCommand();

            case SHOW_ASSIGNED_TRAINER_LIST: return  new ShowAssignedTrainerListCommand();
            case SHOW_ASSIGNED_TRAINER_SET_PAGE: return  new ShowAssignedTrainerSetPageCommand();
            case SAVE_ASSIGNED_TRAINER: return  new SaveAssignedTrainerCommand();
            case DELETE_ASSIGNED_TRAINER: return  new DeleteAssignedTrainerCommand();

            case SHOW_ASSIGNED_COMPLEXES: return  new ShowAssignedComplexesCommand();
            case SHOW_ASSIGNED_COMPLEX_ADD_PAGE: return  new ShowAssignedComplexAddPageCommand();
            case ADD_ASSIGNED_COMPLEX: return  new AddAssignedComplexCommand();
            case DELETE_ASSIGNED_COMPLEX: return  new DeleteAssignedComplexCommand();
            case SHOW_ASSIGNED_COMPLEX_EDIT_PAGE: return  new ShowAssignedComplexEditPageCommand();
            case UPDATE_ASSIGNED_COMPLEX: return  new UpdateAssignedComplexCommand();

            case SHOW_EXERCISE_LIST: return  new ShowExerciseListCommand();
            case DELETE_EXERCISE: return  new DeleteExerciseCommand();
            case SHOW_EXERCISE_ADD_PAGE: return  new ShowExerciseAddPageCommand();
            case ADD_EXERCISE: return  new AddExerciseCommand();
            case SHOW_EXERCISE_EDIT: return  new ShowExerciseEditCommand();
            case UPDATE_EXERCISE: return  new UpdateExerciseCommand();

            case SHOW_MY_COMPLEXES: return  new ShowMyComplexesCommand();
            case DELETE_COMPLEX: return  new DeleteComplexCommand();
            case SHOW_EDIT_COMPLEX_PAGE: return  new ShowEditComplexPageCommand();
            case SHOW_ADD_EXERCISE_IN: return  new ShowAddExerciseInComplex();
            case ADD_EXERCISE_IN_COMPLEX: return  new AddExerciseInComplexCommand();
            case DELETE_EXERCISE_IN_COMPLEX: return  new DeleteExerciseInComplexCommand();
            case UPDATE_COMPLEX: return  new UpdateComplexCommand();
            case SHOW_ADD_COMPLEX_PAGE: return  new ShowAddComplexPageCommand();
            case SAVE_NEW_COMPLEX: return  new SaveNewComplexCommand();
            case SHOW_MY_ACCOUNT_PAGE: return  new ShowMyAccountPageCommand();
            default: throw new IllegalArgumentException();
        }

    }

    /**
     * returns object of command class which correspond to command name
     */
    public Command getCommand(CommandName commandName) {
        Command command = null;
        try {
            command = getCommandByName(commandName);
            return command;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}

//    public Command getCommand(Class<? extends Command> clazz) {
//        Command command = null;
//        try {
//            command = repository.get(clazz);
//            return command;
//        } catch (IllegalArgumentException e) {
//            return null;
//        }
//    }



//    private final Map<Class<? extends Command>, Command> repository = new ConcurrentHashMap<>();
//
//    public CommandProvider() {
//        repository.put(StartCommand.class, new StartCommand());
//        repository.put(ChangeLanguageCommand.class, new ChangeLanguageCommand());
//
//        repository.put(LoginCommand.class, new LoginCommand());
//        repository.put(ShowLoginCommand.class, new ShowLoginCommand());
//        repository.put(LogoutCommand.class, new LogoutCommand());
//        repository.put(ShowUserEditLoginCommand.class, new ShowUserEditLoginCommand());
//        repository.put(SaveChangedLoginCommand.class, new SaveChangedLoginCommand());
//
//        repository.put(RegistrationCommand.class, new RegistrationCommand());
//        repository.put(ShowRegistrationCommand.class, new ShowRegistrationCommand());
//
//        repository.put(ShowUsersByRoleCommand.class, new ShowUsersByRoleCommand());
//        repository.put(DeleteUserCommand.class, new DeleteUserCommand());
//
//        repository.put(ShowJournalCommand.class, new ShowJournalCommand());
//
//        repository.put(ShowAllCommonComplexCommand.class, new ShowAllCommonComplexCommand());
//        repository.put(ShowVisitorAssignedComplexesCommand.class, new ShowVisitorAssignedComplexesCommand());
//        repository.put(ShowExecuteComplexCommand.class, new ShowExecuteComplexCommand());
//        repository.put(UpdateDateExecutedAssignedComplexCommand.class, new UpdateDateExecutedAssignedComplexCommand());
//
//        repository.put(DeleteSubscriptionCommand.class, new DeleteSubscriptionCommand());
//        repository.put(ShowSubscriptionEditCommand.class, new ShowSubscriptionEditCommand());
//        repository.put(UpdateSubscriptionCommand.class, new UpdateSubscriptionCommand());
//        repository.put(ShowAllSubscriptionsCommand.class, new ShowAllSubscriptionsCommand());
//        repository.put(ShowSubscriptionBuyCommand.class, new ShowSubscriptionBuyCommand());
//        repository.put(ShowVisitorSubscriptionCommand.class, new ShowVisitorSubscriptionCommand());
//        repository.put(SaveNewSubscriptionCommand.class, new SaveNewSubscriptionCommand());
//        repository.put(ShowVisitorsByTrainerCommand.class, new ShowVisitorsByTrainerCommand());
//
//
//        repository.put(UpdatePersonCommand.class, new UpdatePersonCommand());
//        repository.put(ShowPersonEditCommand.class, new ShowPersonEditCommand());
//
//        repository.put(ShowAssignedTrainerListCommand.class, new ShowAssignedTrainerListCommand());
//        repository.put(ShowAssignedTrainerSetPageCommand.class, new ShowAssignedTrainerSetPageCommand());
//        repository.put(SaveAssignedTrainerCommand.class, new SaveAssignedTrainerCommand());
//        repository.put(DeleteAssignedTrainerCommand.class, new DeleteAssignedTrainerCommand());
//
//        repository.put(ShowAssignedComplexesCommand.class, new ShowAssignedComplexesCommand());
//        repository.put(ShowAssignedComplexAddPageCommand.class, new ShowAssignedComplexAddPageCommand());
//        repository.put(AddAssignedComplexCommand.class, new AddAssignedComplexCommand());
//        repository.put(DeleteAssignedComplexCommand.class, new DeleteAssignedComplexCommand());
//        repository.put(ShowAssignedComplexEditPageCommand.class, new ShowAssignedComplexEditPageCommand());
//        repository.put(UpdateAssignedComplexCommand.class, new UpdateAssignedComplexCommand());
//
//        repository.put(ShowExerciseListCommand.class, new ShowExerciseListCommand());
//        repository.put(DeleteExerciseCommand.class, new DeleteExerciseCommand());
//        repository.put(ShowExerciseAddPageCommand.class, new ShowExerciseAddPageCommand());
//        repository.put(AddExerciseCommand.class, new AddExerciseCommand());
//        repository.put(ShowExerciseEditCommand.class, new ShowExerciseEditCommand());
//        repository.put(UpdateExerciseCommand.class, new UpdateExerciseCommand());
//
//        repository.put(ShowMyComplexesCommand.class, new ShowMyComplexesCommand());
//        repository.put(DeleteComplexCommand.class, new DeleteComplexCommand());
//        repository.put(ShowEditComplexPageCommand.class, new ShowEditComplexPageCommand());
//        repository.put(ShowAddExerciseInComplex.class, new ShowAddExerciseInComplex());
//        repository.put(AddExerciseInComplexCommand.class, new AddExerciseInComplexCommand());
//        repository.put(DeleteExerciseInComplexCommand.class, new DeleteExerciseInComplexCommand());
//        repository.put(UpdateComplexCommand.class, new UpdateComplexCommand());
//        repository.put(ShowAddComplexPageCommand.class, new ShowAddComplexPageCommand());
//        repository.put(SaveNewComplexCommand.class, new SaveNewComplexCommand());
//        repository.put(ShowMyAccountPageCommand.class, new ShowMyAccountPageCommand());
//
//    }