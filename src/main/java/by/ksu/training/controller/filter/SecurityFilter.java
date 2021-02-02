package by.ksu.training.controller.filter;

import by.ksu.training.controller.AttrName;
import by.ksu.training.controller.commands.Command;
import by.ksu.training.controller.commands.StartCommand;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

public class SecurityFilter implements Filter {

    private static Logger logger = LogManager.getLogger(SecurityFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            // получаем request response command
            HttpServletRequest httpRequest = (HttpServletRequest)request;
            HttpServletResponse httpResponse = (HttpServletResponse)response;
            Command command = (Command)httpRequest.getAttribute("command");
            // получаем allowRoles
            Set<Role> allowRoles = command.getAllowedRoles();

            //получаем session, берем user оттуда
            HttpSession session = httpRequest.getSession(false);
            User user = null;
            if(session != null) {
                user = (User)session.getAttribute(AttrName.AUTHORIZED_USER);
                String errorMessage = (String)session.getAttribute("SecurityFilterMessage");
                if(errorMessage != null) {
                    httpRequest.setAttribute(AttrName.ERROR_MESSAGE, errorMessage);
                    session.removeAttribute("SecurityFilterMessage");
                }
            }

            //проверяем может ли этот User выполнить выбранную команду
            String userName = "unauthorized user";
            boolean canExecute = allowRoles == null;
            if(user != null) {
                userName = "\"" + user.getLogin() + "\" user";
                if (allowRoles!= null && allowRoles.isEmpty()) {
                    logger.info("Trying of {} access to forbidden resource {}", userName, command.getName());
                    request.setAttribute(AttrName.ERROR_MESSAGE, "Access forbidden for authorized user. Logout first.");
                    request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
                }
                canExecute = canExecute || allowRoles.contains(user.getRole());
            } else {
                //user не авторизован и имеет доступ к странице для авторизованных юзеров (login and registration)
                if (allowRoles!= null && allowRoles.isEmpty()) {
                    canExecute = true;
                }
            }
            // если может то следующий фильтр
            if(canExecute) {
                chain.doFilter(request, response);
            } else {
                // если нет, то redirect на login.html и сообщение на SecurityFilterMessage
                logger.info("Trying of {} access to forbidden resource {}", userName, command.getName());
                if(session != null && command.getClass() != StartCommand.class) {
                    session.setAttribute("SecurityFilterMessage", "\"Access forbidden. ");
                }
                if (user != null) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/error.jsp");
                } else {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html");}
            }
        } else {
            //ошибка
            logger.error("It is impossible to use HTTP filter");
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {}
}
