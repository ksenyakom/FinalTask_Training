package by.ksu.training.controller;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.controller.commands.CommandProvider;
import by.ksu.training.controller.commands.MainCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandFromUriFilter implements Filter {
    private static Logger logger = LogManager.getLogger(CommandFromUriFilter.class);

    private static Map<String, Class<? extends Command>> commands = new ConcurrentHashMap<>();
    // TODO зачем тут потокобезопасная коллекция
    private static CommandProvider commandProvider;

    static {
        commands.put("/", MainCommand.class);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commandProvider = new CommandProvider();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest)request;
            String contextPath = httpRequest.getContextPath();
            String uri = httpRequest.getRequestURI();
            logger.debug(String.format("Starting of processing of request for URI \"%s\"", uri));
            int beginCommand = contextPath.length();
            int endCommand = uri.lastIndexOf('.');
            String commandName;
            if(endCommand >= 0) {
                commandName = uri.substring(beginCommand, endCommand);
            } else {
                commandName = uri.substring(beginCommand);
            }
            Class<? extends Command> commandClass = commands.get(commandName);
            try {
               // Command command = commandClass.newInstance();
                Command command = commandProvider.getCommand(commandClass);
                command.setName(commandName);
                httpRequest.setAttribute("command", command);
                chain.doFilter(request, response);
            } catch (NullPointerException e) {
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
    public void destroy() {}
}
