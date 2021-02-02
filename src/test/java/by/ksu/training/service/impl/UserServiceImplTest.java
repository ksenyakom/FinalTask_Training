package by.ksu.training.service.impl;

import by.ksu.training.dao.GetDBProperties;
import by.ksu.training.dao.GetProperties;
import by.ksu.training.dao.Transaction;
import by.ksu.training.dao.database.TransactionImpl;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.testng.Assert.*;

public class UserServiceImplTest {
    private Connection connection;
    private Transaction transaction;
    UserService userService;
    int id;

    @BeforeClass
    public void init() throws PersistentException, ClassNotFoundException, SQLException {
        GetProperties getDBProperties = new GetDBProperties();
        Properties properties = getDBProperties.fromFile("properties/database.properties");
        String driverName = (String) properties.get("driver");
        String databaseUrl = (String) properties.get("db.url");
        Class.forName(driverName);
        connection = DriverManager.getConnection(databaseUrl, properties);
        connection.setAutoCommit(false);

        transaction = new TransactionImpl(connection);
        userService = new UserServiceImpl();
        ((ServiceImpl) userService).setTransaction(transaction);
    }

    @AfterClass
    public void destroy() throws PersistentException, SQLException {
        userService.delete(id);
        transaction.commit();
        connection.close();
    }

    @DataProvider(name = "user")
    public Object[] createData() {
        User user = new User();
        user.setLogin("InterestingGuest");
        user.setPassword("12345");
        user.setEmail("mail@mail.ru");
        user.setRole(Role.TRAINER);

        return new Object[]{
                user
        };
    }

    @Test
    public void testFindById() throws PersistentException {
        User user = userService.findById(2);
        String expectedLogin = "visitor1";

        assertEquals(user.getLogin(), expectedLogin);
    }

    @Test
    public void testFindByLoginAndPassword() throws PersistentException {
        String login = "Visitor1";
        String password = "visitor1";

        User foundUser = userService.findByLoginAndPassword(login, password);

        assertEquals(foundUser.getLogin(), login);
    }

    @Test(dataProvider = "user")
    public void testSave(User user) throws PersistentException {
        userService.save(user);
        User userFromBase = userService.findById(user.getId());
        userService.delete(user.getId());

        assertEquals(user.getLogin(), userFromBase.getLogin());
    }

    @Test(expectedExceptions = PersistentException.class)
    public void testSaveException() throws PersistentException {
        User user = new User();
        user.setLogin("DuplicateUser");
        user.setPassword("12345");
        user.setEmail("mail@mail.ru");
        user.setRole(Role.TRAINER);

        userService.save(user);
        id = user.getId();
        user.setId(null);
        userService.save(user);
    }

    @Test(dataProvider = "user")
    public void testDelete(User user) throws PersistentException {
        userService.save(user);
        userService.delete(user.getId());
        User expectedUser = userService.findById(user.getId());

        assertNull(expectedUser);
    }

    @Test
    public void testFindUserByRole() throws PersistentException {
        List<User> users = userService.findUserByRole(Role.VISITOR);
        int expectedNumber = 2;

        assertEquals(users.size(), expectedNumber);
    }

    @Test
    public void testFindLogin() throws PersistentException {
        List<User> users = List.of(new User(2), new User(3));
        userService.findLogin(users);

        assertEquals(users.get(0).getLogin(), "visitor1");
        assertEquals(users.get(1).getLogin(), "visitor2");
    }

    @Test
    public void testCheckLoginExist() throws PersistentException {
        boolean expectedTrue = userService.checkLoginExist("visitor1");
        boolean expectedFalse = userService.checkLoginExist("login_which_not_exist");

        assertTrue(expectedTrue);
        assertFalse(expectedFalse);
    }
}