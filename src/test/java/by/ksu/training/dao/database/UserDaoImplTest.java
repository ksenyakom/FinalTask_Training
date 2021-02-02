package by.ksu.training.dao.database;

import by.ksu.training.dao.GetProperties;
import by.ksu.training.dao.Transaction;
import by.ksu.training.dao.UserDao;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.dao.GetDBProperties;
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

public class UserDaoImplTest {
    private Connection connection;
    private Transaction transaction;
    private UserDao userDao;
    private int id;

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
        userDao = transaction.createDao(UserDao.class);
    }

    @AfterClass
    public void destroy() throws PersistentException, SQLException {
        userDao.delete(id);
        transaction.commit();
        connection.close();
    }

    @DataProvider(name = "user")
    public Object[] createData() {
        User user = new User();
        user.setLogin("RareGuest");
        user.setPassword("12345");
        user.setEmail("mail@mail.ru");
        user.setRole(Role.TRAINER);

        return new Object[]{
                user
        };
    }

    @Test(priority = 1, dataProvider = "user")
    public void testCreateRead(User user) throws PersistentException {
        int id = userDao.create(user);
        user.setId(id);
        User actual = userDao.read(id);
        userDao.delete(id);
        transaction.commit();

        assertEquals(actual, user);
    }

    @Test(priority = 1, expectedExceptions = PersistentException.class)
    public void testCreateException() throws PersistentException {
        User user = new User();
        user.setLogin("SuperGuest");
        user.setPassword("save password");
        user.setRole(Role.TRAINER);

        id = userDao.create(user);
        userDao.create(user);
    }

    @Test(priority = 2, dataProvider = "user")
    public void testUpdate(User user) throws PersistentException {
        int id = userDao.create(user);
        user.setId(id);

        user.setPassword("98765");
        user.setEmail("newMail@mail.ru");
        user.setRole(Role.VISITOR);
        user.setLogin("Guest2");

        userDao.update(user);
        User actual = userDao.read(id);
        userDao.delete(id);
        transaction.commit();

        assertEquals(actual, user);
    }

    @Test(priority = 3, dataProvider = "user")
    public void testDelete(User user) throws PersistentException {
        int id = userDao.create(user);
        userDao.delete(id);
        user = userDao.read(id);
        transaction.commit();

        assertNull(user);
    }

    @Test(priority = 3, dataProvider = "user")
    public void testCheckLogin(User user) throws PersistentException {
        int id = userDao.create(user);
        boolean expected = userDao.checkIfLoginExist(user.getLogin());
        userDao.delete(id);
        transaction.commit();

        assertTrue(expected);
    }

    @Test
    public void testReadUserByRole() throws PersistentException {
        List<User> users = userDao.readUserByRole(Role.VISITOR);
        int expectedUserNumber = 2;

        assertEquals(users.size(), expectedUserNumber);
    }

    @Test
    public void testReadByLogin() throws PersistentException {
        User user = userDao.readByLogin("visitor1");
        Integer idExpected = 2;

        assertEquals(user.getId(),idExpected);
    }

    @Test
    public void testCheckIfLoginExist() throws PersistentException {
        boolean expectedTrue = userDao.checkIfLoginExist("visitor1");
        boolean expectedFalse = userDao.checkIfLoginExist("login_which_not_exist");

        assertTrue(expectedTrue);
        assertFalse(expectedFalse);
    }

    @Test
    public void testReadLogin() throws PersistentException {
        List <User> users = List.of(new User(2), new User(3));
        userDao.readLogin(users);

        assertEquals(users.get(0).getLogin(), "visitor1");
        assertEquals(users.get(1).getLogin(), "visitor2");
    }
}