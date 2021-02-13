package by.ksu.training.controller.filter;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.commands.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CommandFromUriFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(CommandFromUriFilter.class);

    private static final Map<String, CommandName> commandsGet = new ConcurrentHashMap<>();
    private static final Map<String, CommandName> commandsPost = new ConcurrentHashMap<>();
    private CommandFactory commandFactory;

    static {
        commandsGet.put("/",  CommandName.START);
        commandsGet.put("/index",  CommandName.START);
        commandsGet.put("/login",  CommandName.SHOW_LOGIN);
        commandsGet.put("/logout",  CommandName.LOGOUT);
        commandsGet.put("/user/list",  CommandName.SHOW_USERS_BY_ROLE);
        commandsGet.put("/user/edit_login",  CommandName.SHOW_USER_EDIT_LOGIN);
        commandsGet.put("/person/edit",  CommandName.SHOW_PERSON_EDIT);
        commandsGet.put("/visitor/assigned_trainings",  CommandName.SHOW_VISITOR_ASSIGNED_COMPLEXES);
        commandsGet.put("/visitor/subscription",  CommandName.SHOW_VISITOR_SUBSCRIPTION);
        commandsGet.put("/subscription/list",  CommandName.SHOW_ALL_SUBSCRIPTIONS);
        commandsGet.put("/subscription/edit",  CommandName.SHOW_SUBSCRIPTION_EDIT);
        commandsGet.put("/subscription/buy",  CommandName.SHOW_SUBSCRIPTION_BUY);
        commandsGet.put("/complex/list",  CommandName.SHOW_ALL_COMMON_COMPLEX);
        commandsGet.put("/journal",  CommandName.SHOW_JOURNAL);
        commandsGet.put("/registration",  CommandName.SHOW_REGISTRATION);
        commandsGet.put("/change_language",  CommandName.CHANGE_LANGUAGE);
        commandsGet.put("/assigned_trainer/list",  CommandName.SHOW_ASSIGNED_TRAINER_LIST);
        commandsGet.put("/assigned_trainer/set",  CommandName.SHOW_ASSIGNED_TRAINER_SET_PAGE);
        commandsGet.put("/complex/execute",  CommandName.SHOW_EXECUTE_COMPLEX);
        commandsGet.put("/visitor/list",  CommandName.SHOW_VISITORS_BY_TRAINER);
        commandsGet.put("/assigned_complex/list",  CommandName.SHOW_ASSIGNED_COMPLEXES);
        commandsGet.put("/assigned_complex/add",  CommandName.SHOW_ASSIGNED_COMPLEX_ADD_PAGE);
        commandsGet.put("/assigned_complex/edit",  CommandName.SHOW_ASSIGNED_COMPLEX_EDIT_PAGE);
        commandsGet.put("/exercise/list",  CommandName.SHOW_EXERCISE_LIST);
        commandsGet.put("/exercise/add",  CommandName.SHOW_EXERCISE_ADD_PAGE);
        commandsGet.put("/exercise/edit",  CommandName.SHOW_EXERCISE_EDIT);
        commandsGet.put("/complex/my_complexes",  CommandName.SHOW_MY_COMPLEXES);
        commandsGet.put("/complex/edit",  CommandName.SHOW_EDIT_COMPLEX_PAGE);
        commandsGet.put("/complex/add_exercise_in_complex",  CommandName.SHOW_ADD_EXERCISE_IN);
        commandsGet.put("/complex/add",  CommandName.SHOW_ADD_COMPLEX_PAGE);
        commandsGet.put("/my_account",  CommandName.SHOW_MY_ACCOUNT_PAGE);
        commandsGet.put("/admin/register_trainer",  CommandName.SHOW_REGISTER_TRAINER_PAGE);
        commandsGet.put("/contacts",  CommandName.SHOW_CONTACTS);
        commandsGet.put("/report/subscription",  CommandName.SHOW_REPORT_SUBSCRIPTION);

        commandsPost.put("/user/deleteUser",  CommandName.DELETE_USER);
        commandsPost.put("/user/save_changes_login",  CommandName.SAVE_CHANGED_LOGIN);
        commandsPost.put("/person/save_changes",  CommandName.UPDATE_PERSON);
        commandsPost.put("/subscription/delete",  CommandName.DELETE_SUBSCRIPTION);
        commandsPost.put("/subscription/update",  CommandName.UPDATE_SUBSCRIPTION);
        commandsPost.put("/subscription/save_new",  CommandName.SAVE_NEW_SUBSCRIPTION);
        commandsPost.put("/registration",  CommandName.REGISTRATION);
        commandsPost.put("/login",  CommandName.LOGIN);
        commandsPost.put("/assigned_trainer/save",  CommandName.SAVE_ASSIGNED_TRAINER);
        commandsPost.put("/assigned_trainer/delete",  CommandName.DELETE_ASSIGNED_TRAINER);
        commandsPost.put("/assigned_complex/update_date_executed",  CommandName.UPDATE_DATE_EXECUTED_ASSIGNED_COMPLEX);
        commandsPost.put("/assigned_complex/add",  CommandName.ADD_ASSIGNED_COMPLEX);
        commandsPost.put("/assigned_complex/delete",  CommandName.DELETE_ASSIGNED_COMPLEX);
        commandsPost.put("/assigned_complex/update",  CommandName.UPDATE_ASSIGNED_COMPLEX);
        commandsPost.put("/exercise/delete",  CommandName.DELETE_EXERCISE);
        commandsPost.put("/exercise/add",  CommandName.ADD_EXERCISE);
        commandsPost.put("/exercise/edit",  CommandName.UPDATE_EXERCISE);
        commandsPost.put("/complex/delete",  CommandName.DELETE_COMPLEX);
        commandsPost.put("/complex/add_exercise_in_complex",  CommandName.ADD_EXERCISE_IN_COMPLEX);
        commandsPost.put("/complex/delete_exercise_in_complex",  CommandName.DELETE_EXERCISE_IN_COMPLEX);
        commandsPost.put("/complex/edit",  CommandName.UPDATE_COMPLEX);
        commandsPost.put("/complex/save",  CommandName.SAVE_NEW_COMPLEX);
        commandsPost.put("/admin/register_trainer",  CommandName.REGISTER_TRAINER);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commandFactory = new CommandFactory();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String commandNameFromPath = commandNameFromRequestPath(httpRequest);
            String method = httpRequest.getMethod();
            Optional<CommandName> commandName = commandNameByMethodAndNameFromPath(method, commandNameFromPath);

            if (commandName.isPresent()) {
                Command command = commandFactory.getCommand(commandName.get());

                command.setName(commandNameFromPath);
                httpRequest.setAttribute("command", command);
                chain.doFilter(request, response);

            } else {
                logger.error("It is impossible to create command object:{}", commandNameFromPath);
                httpRequest.setAttribute(AttrName.ERROR_MESSAGE,
                        String.format("Requested uri cannot be processed by server %s", httpRequest.getRequestURI()));
                httpRequest.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            }
        } else {
            logger.error("It is impossible to use HTTP filter");
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    private String commandNameFromRequestPath(HttpServletRequest httpRequest) {
        String contextPath = httpRequest.getContextPath();
        String uri = httpRequest.getRequestURI();
        logger.debug("Starting of processing of request for URI {}", uri);
        int beginCommand = contextPath.length();
        int endCommand = uri.lastIndexOf('.');
        String commandName;

        if (endCommand >= 0) {
            commandName = uri.substring(beginCommand, endCommand);
        } else {
            commandName = uri.substring(beginCommand);
        }
        return commandName;
    }

    private Optional<CommandName> commandNameByMethodAndNameFromPath(String method, String commandName) {
        switch (method.toLowerCase()) {
            case "get":
                return Optional.ofNullable(commandsGet.get(commandName));
            case "post":
                return Optional.ofNullable(commandsPost.get(commandName));
            default:
                return Optional.empty();
        }
    }
}


// commandsGet.put("/", StartCommand.class);
//        commandsGet.put("/index", StartCommand.class);
//        commandsGet.put("/login", ShowLoginCommand.class);
//        commandsGet.put("/logout", LogoutCommand.class);
//        commandsGet.put("/user/list", ShowUsersByRoleCommand.class);
//        commandsGet.put("/user/edit_login", ShowUserEditLoginCommand.class);
//        commandsGet.put("/person/edit", ShowPersonEditCommand.class);
//        commandsGet.put("/visitor/assigned_trainings", ShowVisitorAssignedComplexesCommand.class);
//        commandsGet.put("/visitor/subscription", ShowVisitorSubscriptionCommand.class);
//        commandsGet.put("/subscription/list", ShowAllSubscriptionsCommand.class);
//        commandsGet.put("/subscription/edit", ShowSubscriptionEditCommand.class);
//        commandsGet.put("/subscription/buy", ShowSubscriptionBuyCommand.class);
//        commandsGet.put("/complex/list", ShowAllCommonComplexCommand.class);
//        commandsGet.put("/journal", ShowJournalCommand.class);
//        commandsGet.put("/registration", ShowRegistrationCommand.class);
//        commandsGet.put("/change_language", ChangeLanguageCommand.class);
//        commandsGet.put("/assigned_trainer/list", ShowAssignedTrainerListCommand.class);
//        commandsGet.put("/assigned_trainer/set", ShowAssignedTrainerSetPageCommand.class);
//        commandsGet.put("/complex/execute", ShowExecuteComplexCommand.class);
//        commandsGet.put("/visitor/list", ShowVisitorsByTrainerCommand.class);
//        commandsGet.put("/assigned_complex/list", ShowAssignedComplexesCommand.class);
//        commandsGet.put("/assigned_complex/add", ShowAssignedComplexAddPageCommand.class);
//        commandsGet.put("/assigned_complex/edit", ShowAssignedComplexEditPageCommand.class);
//        commandsGet.put("/exercise/list", ShowExerciseListCommand.class);
//        commandsGet.put("/exercise/add", ShowExerciseAddPageCommand.class);
//        commandsGet.put("/exercise/edit", ShowExerciseEditCommand.class);
//        commandsGet.put("/complex/my_complexes", ShowMyComplexesCommand.class);
//        commandsGet.put("/complex/edit", ShowEditComplexPageCommand.class);
//        commandsGet.put("/complex/add_exercise_in_complex", ShowAddExerciseInComplex.class);
//        commandsGet.put("/complex/add", ShowAddComplexPageCommand.class);
//        commandsGet.put("/my_account", ShowMyAccountPageCommand.class);
//
//        commandsPost.put("/user/deleteUser", DeleteUserCommand.class);
//        commandsPost.put("/user/save_changes_login", SaveChangedLoginCommand.class);
//        commandsPost.put("/person/save_changes", UpdatePersonCommand.class);
//        commandsPost.put("/subscription/delete", DeleteSubscriptionCommand.class);
//        commandsPost.put("/subscription/update", UpdateSubscriptionCommand.class);
//        commandsPost.put("/subscription/save_new", SaveNewSubscriptionCommand.class);
//        commandsPost.put("/registration", RegistrationCommand.class);
//        commandsPost.put("/login", LoginCommand.class);
//        commandsPost.put("/assigned_trainer/save", SaveAssignedTrainerCommand.class);
//        commandsPost.put("/assigned_trainer/delete", DeleteAssignedTrainerCommand.class);
//        commandsPost.put("/assigned_complex/update_date_executed", UpdateDateExecutedAssignedComplexCommand.class);
//        commandsPost.put("/assigned_complex/add", AddAssignedComplexCommand.class);
//        commandsPost.put("/assigned_complex/delete", DeleteAssignedComplexCommand.class);
//        commandsPost.put("/assigned_complex/update", UpdateAssignedComplexCommand.class);
//        commandsPost.put("/exercise/delete", DeleteExerciseCommand.class);
//        commandsPost.put("/exercise/add", AddExerciseCommand.class);
//        commandsPost.put("/exercise/edit", UpdateExerciseCommand.class);
//        commandsPost.put("/complex/delete", DeleteComplexCommand.class);
//        commandsPost.put("/complex/add_exercise_in_complex", AddExerciseInComplexCommand.class);
//        commandsPost.put("/complex/delete_exercise_in_complex", DeleteExerciseInComplexCommand.class);
//        commandsPost.put("/complex/edit", UpdateComplexCommand.class);
//        commandsPost.put("/complex/save", SaveNewComplexCommand.class);