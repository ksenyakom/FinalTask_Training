package by.ksu.training.controller;

import by.ksu.training.dao.ExerciseDao;
import by.ksu.training.dao.TransactionFactory;
import by.ksu.training.dao.database.TransactionFactoryImpl;
import by.ksu.training.dao.pool.ConnectionPool;
import by.ksu.training.entity.Trainer;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.FilePath;
import by.ksu.training.service.GetDBProperties;
import by.ksu.training.service.ServiceFactory;
import by.ksu.training.service.ServiceFactoryImpl;
import by.ksu.training.service.impl.TrainerService;
import by.ksu.training.service.impl.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.List;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            TransactionFactory factory = new TransactionFactoryImpl();
            ServiceFactory serviceFactory = new ServiceFactoryImpl(factory);
            UserService userService = serviceFactory.getService(UserService.class);
            TrainerService trainerService = serviceFactory.getService(TrainerService.class);

            User user = userService.findByIdentity(1);
            List<Trainer> trainerList = trainerService.findAll();
            req.setAttribute("tList", trainerList);

            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
//            HttpSession session = req.getSession();
//            StringBuilder sb = new StringBuilder();
//            session.setAttribute("mySessionAttr", "some value" );
//            Enumeration<String> sAttr = session.getAttributeNames();
//            while (sAttr.hasMoreElements()) {
//                String name = sAttr.nextElement();
//                sb.append(name + " = " +  session.getAttribute(name)).append("  ");
//            }
//            sb.append("Время жизни сессии").append(session.getMaxInactiveInterval());


//            Cookie[] cookies = req.getCookies();
//            String s = "" + cookies.length;
//            for (Cookie cookie : cookies) {
//                s += cookie.getName();
//                s += cookie.getValue();
//                s += cookie.getMaxAge();
//            Cookie cookie = new Cookie("testCookie", "abc");
//            resp.addCookie(cookie);





        } catch (PersistentException e) {
            e.printStackTrace();
        }


    }
}
