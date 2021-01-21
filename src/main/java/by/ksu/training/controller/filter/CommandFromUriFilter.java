package by.ksu.training.controller.filter;

import by.ksu.training.controller.commands.*;
import by.ksu.training.controller.commands.admin.*;
import by.ksu.training.controller.commands.common.*;
import by.ksu.training.controller.commands.visitor.ShowSubscriptionBuyCommand;
import by.ksu.training.controller.commands.visitor.ShowVisitorAssignedComplexesCommand;
import by.ksu.training.controller.commands.visitor.ShowVisitorSubscriptionCommand;
import by.ksu.training.controller.commands.visitor.SubscriptionSaveNewCommand;
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

    private static final Map<String, Class<? extends Command>> commandsGet = new ConcurrentHashMap<>();
    private static final Map<String, Class<? extends Command>> commandsPost = new ConcurrentHashMap<>();
    private CommandProvider commandProvider;

    static { // не забывать ложить класс в CommandProvider!!!!
        //TODO можно это перенести в init???

        commandsGet.put("/", StartCommand.class);
        commandsGet.put("/index", StartCommand.class);
        commandsGet.put("/login", ShowLoginCommand.class);
        commandsGet.put("/logout", LogoutCommand.class);
        commandsGet.put("/user/list", ShowUsersByRoleCommand.class);
        commandsGet.put("/user/edit_login", ShowUserEditLoginCommand.class);
        commandsGet.put("/person/edit", ShowPersonEditCommand.class);
        commandsGet.put("/visitor/assigned_trainings", ShowVisitorAssignedComplexesCommand.class);
        commandsGet.put("/visitor/subscription", ShowVisitorSubscriptionCommand.class);
        commandsGet.put("/subscription/list", ShowAllSubscriptionsCommand.class);
        commandsGet.put("/subscription/edit", ShowSubscriptionEditCommand.class);
        commandsGet.put("/subscription/buy", ShowSubscriptionBuyCommand.class);
        commandsGet.put("/complex/list", ShowAllCommonComplexCommand.class);
        commandsGet.put("/journal", ShowJournalCommand.class);
        commandsGet.put("/registration", ShowRegistrationCommand.class);
        commandsGet.put("/change_language", ChangeLanguageCommand.class);
        commandsGet.put("/assigned_trainer/list", ShowAssignedTrainerListCommand.class);
        commandsGet.put("/assigned_trainer/set", ShowAssignedTrainerSetPageCommand.class);
        commandsGet.put("/complex/execute", ShowExecuteComplexCommand.class);


        commandsPost.put("/user/deleteUser", DeleteUserCommand.class);
        commandsPost.put("/user/save_changes_login", SaveChangedLoginCommand.class);
        commandsPost.put("/person/save_changes", SavePersonChangeCommand.class);
        commandsPost.put("/subscription/deleteSubscription", SubscriptionDeleteCommand.class);
        commandsPost.put("/subscription/update", SubscriptionUpdateCommand.class);
        commandsPost.put("/subscription/save_new", SubscriptionSaveNewCommand.class);
        commandsPost.put("/registration", RegistrationCommand.class);
        commandsPost.put("/login", LoginCommand.class);
        commandsPost.put("/assigned_trainer/save", SaveAssignedTrainerCommand.class);
        commandsPost.put("/assigned_trainer/delete", DeleteAssignedTrainerCommand.class);
      //  commandsPost.put("/assigned_complex/update_date_execute", .class);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commandProvider = new CommandProvider();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String commandName = commandNameFromRequestPath(httpRequest);
            String method = httpRequest.getMethod();
            Optional<Class<? extends Command>> commandClass = commandClassByMethodAndName(method, commandName);

            if (commandClass.isPresent()) {
                Command command = commandProvider.getCommand(commandClass.get());
                if (command == null) { //TODO remove after finish project
                    request.setAttribute("err_message","Добавь команду "+ commandName +" в CommandProvider!!!!!!");
                } else {
                    command.setName(commandName);
                    httpRequest.setAttribute("command", command);
                }
                chain.doFilter(request, response);

            } else {
                logger.error("It is impossible to create command object:{}", commandName);
                httpRequest.setAttribute("err_message",
                        String.format("Requested uri cannot be processed by server %s", httpRequest.getRequestURI()));
                  httpRequest.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
               // httpRequest.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
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

    private Optional<Class<? extends Command>> commandClassByMethodAndName(String method, String commandName) {
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
