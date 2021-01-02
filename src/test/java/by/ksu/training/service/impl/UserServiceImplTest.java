package by.ksu.training.service.impl;

import by.ksu.training.dao.Transaction;
import by.ksu.training.dao.UserDao;
import by.ksu.training.dao.database.TransactionImpl;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.testng.Assert.*;

public class UserServiceImplTest {
    private Connection connection;
    private Transaction transaction;
    private UserDao userDao;
    UserService userService;
    private int id;

    @BeforeClass
    public void init() throws PersistentException, ClassNotFoundException, SQLException {
        GetDBProperties getDBProperties = new GetDBProperties();
        Properties properties = getDBProperties.fromFile(FilePath.dataBasePropertiesPath);
        String driverName = (String) properties.get("driver");
        Class.forName(driverName);

        String databaseUrl = (String) properties.get("db.url");
        connection = DriverManager.getConnection(databaseUrl, properties);
        connection.setAutoCommit(false);

        transaction = new TransactionImpl(connection);
        userDao = transaction.createDao(UserDao.class);
        userService = new UserServiceImpl();
     //   ServiceFactory serviceFactory = new ServiceFactoryImpl()
        //TODO
    }

    @AfterClass
    public void destroy() throws PersistentException, SQLException {
        userDao.delete(id);
        transaction.commit();
        connection.close();
    }


    @Test
    public void testFindPersonIdByRole() {
    }

    @Test
    public void testFindByIdentity() {
    }

    @Test
    public void testFindByLoginAndPassword() {

    }

    @Test
    public void testSave() {
    }

    @Test
    public void testDelete() {
    }
}