package by.ksu.training.controller.filter;

import by.ksu.training.controller.commands.*;
import by.ksu.training.controller.commands.admin.ShowUsersByRoleCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandFromUriFilter implements Filter {
    private static Logger logger = LogManager.getLogger(CommandFromUriFilter.class);

    private static Map<String, Class<? extends Command>> commandsGet = new ConcurrentHashMap<>();
    private static Map<String, Class<? extends Command>> commandsPost = new ConcurrentHashMap<>();
    private static CommandProvider commandProvider;

    static { // не забывать ложить класс в CommandProvider!!!!
        commandsGet.put("/", StartCommand.class);
        commandsGet.put("/index", StartCommand.class);
        commandsGet.put("/login", ShowLoginCommand.class);
        commandsGet.put("/logout", LogoutCommand.class);
        commandsGet.put("/user/list", ShowUsersByRoleCommand.class);
        commandsGet.put("/journal", ShowJournalCommand.class);
        commandsGet.put("/registration", ShowRegistrationCommand.class);
        commandsGet.put("/change_language", ChangeLanguageCommand.class);


        commandsPost.put("/user/list", UserDeleteCommand.class);
        commandsPost.put("/registration", RegistrationCommand.class);
        commandsPost.put("/login", LoginCommand.class);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commandProvider = new CommandProvider();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.debug("i am in uri filter");
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            // определение uri commandName из contextPath для обращения за коммандой
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
            Class<? extends Command> commandClass;
            String method = httpRequest.getMethod();
            switch (method.toLowerCase()) {
                case "get":
                    commandClass = commandsGet.get(commandName);
                    break;
                case "post":
                    commandClass = commandsPost.get(commandName);
                    break;
                default:
                    commandClass = null;
            }

            try {
                Command command = commandProvider.getCommand(commandClass);
                command.setName(commandName);
                httpRequest.setAttribute("command", command);
                chain.doFilter(request, response);
            } catch (NullPointerException e) {// TODO че это за null
                logger.error("It is impossible to create action handler object", e);
                httpRequest.setAttribute("error", String.format("Запрошенный адрес %s не может быть обработан сервером", uri));
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
}
