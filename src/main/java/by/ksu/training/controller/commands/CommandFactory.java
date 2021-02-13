package by.ksu.training.controller.commands;


import by.ksu.training.controller.commands.admin.*;
import by.ksu.training.controller.commands.admin_and_trainer.*;
import by.ksu.training.controller.commands.authorized_user.*;
import by.ksu.training.controller.commands.common.*;
import by.ksu.training.controller.commands.trainer.*;
import by.ksu.training.controller.commands.visitor.*;


public final class CommandFactory {

    private Command getCommandByName(CommandName commandName) {
        switch (commandName) {
            case START: return new StartCommand();

            case CHANGE_LANGUAGE: return new LanguageChangeCommand();
            case LOGIN: return new LogInCommand();
            case SHOW_LOGIN: return new LogInShowPageCommand();
            case LOGOUT: return new LogOutCommand();
            case SHOW_USER_EDIT_LOGIN: return new UserLoginEditShowPageCommand();
            case SAVE_CHANGED_LOGIN: return new LoginUpdateCommand();
            case REGISTRATION: return new UserSaveCommand();
            case SHOW_REGISTRATION: return new RegistrationShowCommand();
            case SHOW_USERS_BY_ROLE: return new UsersByRoleShowCommand();
            case DELETE_USER: return new UserDeleteCommand();

            case SHOW_JOURNAL: return new JournalShowCommand();
            case SHOW_CONTACTS: return new ContactsShowCommand();

            case SHOW_ALL_COMMON_COMPLEX: return new CommonComplexShowAllCommand();
            case SHOW_VISITOR_ASSIGNED_COMPLEXES: return new AssignedComplexVisitorShowCommand();
            case SHOW_EXECUTE_COMPLEX: return new ComplexExecuteShowPageCommand();
            case UPDATE_DATE_EXECUTED_ASSIGNED_COMPLEX: return new AssignedComplexUpdateDateExecutedCommand();

            case DELETE_SUBSCRIPTION: return new SubscriptionDeleteCommand();
            case SHOW_SUBSCRIPTION_EDIT: return new SubscriptionShowEditCommand();
            case UPDATE_SUBSCRIPTION: return new SubscriptionUpdateCommand();
            case SHOW_ALL_SUBSCRIPTIONS: return new SubscriptionsShowAllCommand();
            case SHOW_SUBSCRIPTION_BUY: return new SubscriptionBuyShowPageCommand();
            case SHOW_VISITOR_SUBSCRIPTION: return new SubscriptionVisitorShowCommand();
            case SAVE_NEW_SUBSCRIPTION: return new SubscriptionSaveCommand();
            case SHOW_VISITORS_BY_TRAINER: return new VisitorsByTrainerShowPageCommand();


            case UPDATE_PERSON: return new PersonUpdateCommand();
            case SHOW_PERSON_EDIT: return new PersonEditShowPageCommand();

            case SHOW_ASSIGNED_TRAINER_LIST: return new AssignedTrainerShowListCommand();
            case SHOW_ASSIGNED_TRAINER_SET_PAGE: return new AssignedTrainerShowSetPageCommand();
            case SAVE_ASSIGNED_TRAINER: return new AssignedTrainerSaveCommand();
            case DELETE_ASSIGNED_TRAINER: return new AssignedTrainerDeleteCommand();

            case SHOW_ASSIGNED_COMPLEXES: return new AssignedComplexShowAllCommand();
            case SHOW_ASSIGNED_COMPLEX_ADD_PAGE: return new AssignedComplexAddShowPageCommand();
            case ADD_ASSIGNED_COMPLEX: return new AssignedComplexSaveCommand();
            case DELETE_ASSIGNED_COMPLEX: return new AssignedComplexDeleteCommand();
            case SHOW_ASSIGNED_COMPLEX_EDIT_PAGE: return new AssignedComplexShowEditPageCommand();
            case UPDATE_ASSIGNED_COMPLEX: return new AssignedComplexUpdateCommand();

            case SHOW_EXERCISE_LIST: return new ExerciseListShowCommand();
            case DELETE_EXERCISE: return new ExerciseDeleteCommand();
            case SHOW_EXERCISE_ADD_PAGE: return new ExerciseShowAddPageCommand();
            case ADD_EXERCISE: return new ExerciseSaveCommand();
            case SHOW_EXERCISE_EDIT: return new ExerciseShowEditPageCommand();
            case UPDATE_EXERCISE: return new ExerciseUpdateCommand();

            case SHOW_MY_COMPLEXES: return new MyComplexesShowCommand();
            case DELETE_COMPLEX: return new ComplexDeleteCommand();
            case SHOW_EDIT_COMPLEX_PAGE: return new ComplexEditShowPageCommand();
            case SHOW_ADD_EXERCISE_IN: return new ExerciseInComplexShowAddPageCommand();
            case ADD_EXERCISE_IN_COMPLEX: return new ExerciseInComplexAddCommand();
            case DELETE_EXERCISE_IN_COMPLEX: return new ExerciseInComplexDeleteCommand();
            case UPDATE_COMPLEX: return new ComplexUpdateCommand();
            case SHOW_ADD_COMPLEX_PAGE: return new ComplexShowAddPageCommand();
            case SAVE_NEW_COMPLEX: return new ComplexSaveCommand();
            case SHOW_MY_ACCOUNT_PAGE: return new MyAccountShowPageCommand();

            case SHOW_REGISTER_TRAINER_PAGE: return new TrainerRegisterShowPageCommand();
            case REGISTER_TRAINER: return new TrainerSaveCommand();
            case SHOW_REPORT_SUBSCRIPTION: return new ReportSubscriptionShowPageCommand();
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