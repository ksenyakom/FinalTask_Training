package by.ksu.training.controller;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.controller.commands.CommandManager;
import by.ksu.training.controller.commands.CommandManagerFactory;
import by.ksu.training.controller.state.*;
import by.ksu.training.dao.GetProperties;
import by.ksu.training.dao.TransactionFactoryImpl;
import by.ksu.training.dao.pool.ConnectionPool;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.dao.GetDbProperties;
import by.ksu.training.service.ServiceFactory;
import by.ksu.training.service.ServiceFactoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class DispatcherServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(DispatcherServlet.class);

    public static final int DB_POOL_START_SIZE = 10;
    public static final int DB_POOL_MAX_SIZE = 100;
    public static final int DB_POOL_CHECK_CONNECTION_TIMEOUT = 0;

    @Override
    public void init() {
        try {
            GetProperties getDBProperties = new GetDbProperties();
            Properties properties = getDBProperties.fromFile("properties/database.properties");
            ConnectionPool.getInstance().init(properties,
                    DB_POOL_START_SIZE, DB_POOL_MAX_SIZE, DB_POOL_CHECK_CONNECTION_TIMEOUT);
        } catch (PersistentException e) {
            logger.error("It is impossible to initialize application", e);
            destroy();
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }


    private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("text/html;charset=UTF-8");
            HttpSession session = req.getSession(false);
            if(session != null) {
                @SuppressWarnings("unchecked")
                Map<String, Object> attributes = (Map<String, Object>)session.getAttribute("redirectedData");
                if(attributes != null) {
                    for(String key : attributes.keySet()) {
                        req.setAttribute(key, attributes.get(key));
                    }
                    session.removeAttribute("redirectedData");
                }
            }

            Command command = (Command) req.getAttribute("command");
            if (command != null) {
                CommandManager commandManager = CommandManagerFactory.getManager();
                ResponseState state = commandManager.execute(command, req, resp);
                commandManager.close();

                if(session != null && state != null && !state.getAttributes().isEmpty()) {
                    session.setAttribute("redirectedData", state.getAttributes());
                }

                if (state.getClass() == ErrorState.class) {
                    req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
                } else if (state.getClass() == RedirectState.class) {
                    String uri = req.getContextPath() + "/" + state.getUrl();
                    resp.sendRedirect(uri);
                } else if (state.getClass() == ForwardState.class) {
                    String uri = "/WEB-INF/jsp/" + state.getUrl();
                    req.getRequestDispatcher(uri).forward(req, resp);
                } else if (state.getClass() == ReturnBackState.class) {
                    String uri = "/WEB-INF/jsp" + command.getName() + ".jsp";
                    req.getRequestDispatcher(uri).forward(req, resp);
                }

            } else {
                req.setAttribute(AttrName.WARNING_MESSAGE, "No such command!!");
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
                logger.error("No such command.");
            }
        } catch (PersistentException e) {
            logger.error(e);
            req.setAttribute(AttrName.ERROR_MESSAGE, e);
            req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
        }
    }
}
