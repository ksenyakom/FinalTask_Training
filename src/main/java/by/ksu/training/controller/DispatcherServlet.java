package by.ksu.training.controller;

import by.ksu.training.dao.ExerciseDao;
import by.ksu.training.dao.TransactionFactory;
import by.ksu.training.dao.database.TransactionFactoryImpl;
import by.ksu.training.dao.pool.ConnectionPool;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.FilePath;
import by.ksu.training.service.GetDBProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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



        } catch(PersistentException e) {
            logger.error("It is impossible to initialize application", e);
            destroy();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            TransactionFactory factory = new TransactionFactoryImpl();
            
            req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);


        } catch (PersistentException e) {
            e.printStackTrace();
        }


    }
}
