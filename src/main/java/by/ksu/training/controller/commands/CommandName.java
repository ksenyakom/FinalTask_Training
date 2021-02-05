package by.ksu.training.controller.commands;


/**
 * A class with names of commands.
 * @Author Kseniya Oznobishina
 * @Date 05.02.2021
 */
public enum CommandName {
    //  commandsGet

    START,
    SHOW_LOGIN,
    LOGOUT,
    SHOW_USERS_BY_ROLE,
    SHOW_USER_EDIT_LOGIN,
    SHOW_PERSON_EDIT,
    SHOW_VISITOR_ASSIGNED_COMPLEXES,
    SHOW_VISITOR_SUBSCRIPTION,
    SHOW_ALL_SUBSCRIPTIONS,
    SHOW_SUBSCRIPTION_EDIT,
    SHOW_SUBSCRIPTION_BUY,
    SHOW_ALL_COMMON_COMPLEX,
    SHOW_JOURNAL,
    SHOW_REGISTRATION,
    CHANGE_LANGUAGE,
    SHOW_ASSIGNED_TRAINER_LIST,
    SHOW_ASSIGNED_TRAINER_SET_PAGE,
    SHOW_EXECUTE_COMPLEX,
    SHOW_VISITORS_BY_TRAINER,
    SHOW_ASSIGNED_COMPLEXES,
    SHOW_ASSIGNED_COMPLEX_ADD_PAGE,
    SHOW_ASSIGNED_COMPLEX_EDIT_PAGE,
    SHOW_EXERCISE_LIST,
    SHOW_EXERCISE_ADD_PAGE,
    SHOW_EXERCISE_EDIT,
    SHOW_MY_COMPLEXES,
    SHOW_EDIT_COMPLEX_PAGE,
    SHOW_ADD_EXERCISE_IN,
    SHOW_ADD_COMPLEX_PAGE,
    SHOW_MY_ACCOUNT_PAGE,

    //  commandsPost

    DELETE_USER,
    SAVE_CHANGED_LOGIN,
    UPDATE_PERSON,
    DELETE_SUBSCRIPTION,
    UPDATE_SUBSCRIPTION,
    SAVE_NEW_SUBSCRIPTION,
    REGISTRATION,
    LOGIN,
    SAVE_ASSIGNED_TRAINER,
    DELETE_ASSIGNED_TRAINER,
    UPDATE_DATE_EXECUTED_ASSIGNED_COMPLEX,
    ADD_ASSIGNED_COMPLEX,
    DELETE_ASSIGNED_COMPLEX,
    UPDATE_ASSIGNED_COMPLEX,
    DELETE_EXERCISE,
    ADD_EXERCISE,
    UPDATE_EXERCISE,
    DELETE_COMPLEX,
    ADD_EXERCISE_IN_COMPLEX,
    DELETE_EXERCISE_IN_COMPLEX,
    UPDATE_COMPLEX,
    SAVE_NEW_COMPLEX,
}
