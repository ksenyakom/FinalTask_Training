package by.ksu.training.dao.database;

import by.ksu.training.dao.Transaction;
import by.ksu.training.dao.UserDao;
import by.ksu.training.entity.Person;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.FilePath;
import by.ksu.training.service.GetDBProperties;
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
        GetDBProperties getDBProperties = new GetDBProperties();
        Properties properties = getDBProperties.fromFile(FilePath.dataBasePropertiesPath);
        String driverName = (String) properties.get("driver");
        Class.forName(driverName);

        String databaseUrl = (String) properties.get("db.url");
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
        user.setRole(Role.TRAINER);

        return new Object[]{
                user
        };
    }

    @Test(priority = 4, dataProvider = "user")
    public void testReadPersonByRole(User user) throws PersistentException {
        int id = userDao.create(user);
        user.setId(id);
        List<Person> list = userDao.readPersonByRole(Role.TRAINER);
        userDao.delete(id);
        transaction.commit();
        Person expectedPerson = new Person(user.getId());
        assertTrue(list.contains(expectedPerson));
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
        user.setRole(Role.VISITOR);
        user.setLogin("Guest2");

        userDao.update(user);
        User actual = userDao.read(id);
        userDao.delete(id);
        transaction.commit();

        assertEquals(actual, user);
    }

    @Test(priority = 2)
    public void testUpdateException() throws PersistentException {
        User user = new User();
        user.setId(id*100);
        user.setPassword("98765");
        user.setRole(Role.VISITOR);
        user.setLogin("Guest2");

        userDao.update(user);
    }

    @Test(priority = 3, dataProvider = "user")
    public void testDelete(User user) throws PersistentException {
        int id = userDao.create(user);
        userDao.delete(id);
        user = userDao.read(id);
        transaction.commit();
        assertNull(user);
    }
}