package by.ksu.training.controller;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.controller.commands.CommandManager;
import by.ksu.training.controller.commands.CommandManagerFactory;
import by.ksu.training.controller.state.*;
import by.ksu.training.dao.database.TransactionFactoryImpl;
import by.ksu.training.dao.pool.ConnectionPool;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.FilePath;
import by.ksu.training.service.GetDBProperties;
import by.ksu.training.service.ServiceFactory;
import by.ksu.training.service.ServiceFactoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Properties;

public class DispatcherServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(DispatcherServlet.class);

    public static final int DB_POOL_START_SIZE = 10;
    public static final int DB_POOL_MAX_SIZE = 100;
    public static final int DB_POOL_CHECK_CONNECTION_TIMEOUT = 0;

    public void init() {
        try {
            GetDBProperties getDBProperties = new GetDBProperties();
            Properties properties = getDBProperties.fromFile(FilePath.dataBasePropertiesPath);
            ConnectionPool.getInstance().init(properties,
                    DB_POOL_START_SIZE, DB_POOL_MAX_SIZE, DB_POOL_CHECK_CONNECTION_TIMEOUT);
        } catch (PersistentException e) {
            logger.error("It is impossible to initialize application", e);
            destroy();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    private void doProcessold(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//            Command command = (Command) req.getAttribute("command");
//            ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
//
//            if (command != null) {
//                CommandManager commandManager = CommandManagerFactory.getManager(serviceFactory);
//                Command.Forward forward = commandManager.execute(command, req, resp);
//                commandManager.close();
//                String message = (String) req.getAttribute("err_message");
//                if (message != null) {
//                    //  resp.sendError(404, message);
//                    req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
//
//                } else if (forward != null) {
//                    if (forward.isRedirect()) {
//                        String uri = req.getContextPath() + forward.getForward();
//                        resp.sendRedirect(uri);
//                    } else {
//                        String successMessage = (String) req.getSession().getAttribute("success_message");
//                        if (successMessage != null) {
//                            req.setAttribute("success_message", successMessage);
//                            req.getSession().removeAttribute("success_message");
//                        }
//                        String uri = "/WEB-INF/jsp/" + forward.getForward();
//                        req.getRequestDispatcher(uri).forward(req, resp);
//                    }
//                } else {
//                    String uri = "/WEB-INF/jsp" + command.getName() + ".jsp";
//                    req.getRequestDispatcher(uri).forward(req, resp);
//                }
//            } else {
//                req.getRequestDispatcher("/index.jsp").forward(req, resp);
//                //TODO
//            }
//        } catch (PersistentException e) {
//            e.printStackTrace();
//            //TODo
//        }
    }

    private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Command command = (Command) req.getAttribute("command");
            ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());

            if (command != null) {
                CommandManager commandManager = CommandManagerFactory.getManager(serviceFactory);
                ResponseState state = commandManager.execute(command, req, resp);
                commandManager.close();

                if (state.getClass() == ErrorState.class) {
                    req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
                } else if (state.getClass() == RedirectState.class) {
                    String uri = req.getContextPath() + state.getUrl();
                    resp.sendRedirect(uri);
                } else if (state.getClass() == ForwardState.class) {
                    String successMessage = (String) req.getSession().getAttribute("success_message");
                    if (successMessage != null) {
                        req.setAttribute("success_message", successMessage);
                        req.getSession().removeAttribute("success_message");
                    }
                    String uri = "/WEB-INF/jsp/" + state.getUrl();
                    req.getRequestDispatcher(uri).forward(req, resp);
                } else if (state.getClass() == ReturnBackState.class) {
                    String uri = "/WEB-INF/jsp" + command.getName() + ".jsp";
                    req.getRequestDispatcher(uri).forward(req, resp);
                }

            } else {
                req.setAttribute("warning_message", "No such command!!");
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
                //TODO
            }
        } catch (PersistentException e) {
            req.setAttribute("error_message", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
        }
    }
}
