package by.ksu.training.dao.database;

import by.ksu.training.dao.*;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class SubscriptionDaoImplTest {
    private Transaction transaction;
    private Connection connection;
    private UserDao userDao;
    private SubscriptionDao sDao;
    private int visitorId;


    @BeforeClass
    public void init() throws PersistentException, ClassNotFoundException, SQLException {
        GetProperties getDBProperties = new GetDbProperties();
        Properties properties = getDBProperties.fromFile("properties/database.properties");
        String driverName = (String) properties.get("driver");
        String databaseUrl = (String) properties.get("db.url");
        Class.forName(driverName);
        connection = DriverManager.getConnection(databaseUrl, properties);
        connection.setAutoCommit(false);

        transaction = new TransactionImpl(connection);
        sDao = transaction.createDao(SubscriptionDao.class);
        userDao = transaction.createDao(UserDao.class);

        User user = new User();
        user.setLogin("ComplexGuest");
        user.setPassword("12345");
        user.setEmail("mail@mail.ru");
        user.setRole(Role.VISITOR);
        visitorId = userDao.create(user);

        transaction.commit();
    }

    @AfterClass
    public void destroy() throws PersistentException, SQLException {
        userDao.delete(visitorId);
        transaction.commit();
        connection.close();
    }
    @DataProvider(name = "sub")
    public Object[] createData() {
        Subscription subscription = new Subscription();
        User visitor = new User(visitorId);
        subscription.setVisitor(visitor);
        subscription.setBeginDate(LocalDate.of(2020, 1, 1));
        subscription.setEndDate(LocalDate.of(2020, 5, 1));
        subscription.setPrice(new BigDecimal(100));
        return new Object[]{
                subscription
        };
    }

    @Test(priority = 1, dataProvider = "sub")
    public void testCreateRead(Subscription subscription) throws PersistentException {
        int id = sDao.create(subscription);
        subscription.setId(id);
        Subscription actual = sDao.read(id);
        sDao.delete(id);
        transaction.commit();

        assertEquals(actual, subscription);
    }

    @Test(priority = 2, dataProvider = "sub")
    public void testUpdate(Subscription subscription) throws PersistentException {
        int id = sDao.create(subscription);
        subscription.setId(id);

        subscription.setPrice(BigDecimal.TEN);
        subscription.setBeginDate(LocalDate.of(2021, 10, 12));
        subscription.setEndDate(LocalDate.of(2022, 12, 30));

        sDao.update(subscription);
        Subscription actual = sDao.read(id);
        sDao.delete(id);
        transaction.commit();

        assertEquals(actual, subscription);
    }

    @Test(priority = 3, dataProvider = "sub")
    public void testDelete(Subscription subscription) throws PersistentException {
        int id = sDao.create(subscription);
        sDao.delete(id);
        subscription = sDao.read(id);
        transaction.commit();

        assertNull(subscription);
    }
}