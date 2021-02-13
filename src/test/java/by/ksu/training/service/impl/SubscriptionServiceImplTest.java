package by.ksu.training.service.impl;

import by.ksu.training.dao.GetDbProperties;
import by.ksu.training.dao.GetProperties;
import by.ksu.training.dao.Transaction;
import by.ksu.training.dao.TransactionImpl;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.*;
import by.ksu.training.service.SubscriptionService;
import by.ksu.training.service.UserService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import static org.testng.Assert.*;

public class SubscriptionServiceImplTest {
    private Connection connection;
    private Transaction transaction;
    SubscriptionService subscriptionService;
    UserService userService;


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
        subscriptionService = new SubscriptionServiceImpl();
        ((ServiceImpl) subscriptionService).setTransaction(transaction);
        userService = new UserServiceImpl();
        ((ServiceImpl) userService).setTransaction(transaction);
    }

    @AfterClass
    public void destroy() throws PersistentException, SQLException {
        transaction.commit();
        connection.close();
    }

    @DataProvider (name="sub")
    public Object[] getData() {
        User user = new User(2);
        Subscription subscription = new Subscription();
        subscription.setVisitor(user);
        subscription.setBeginDate(LocalDate.now().minusMonths(1));
        subscription.setEndDate(LocalDate.now().plusMonths(1));
        subscription.setPrice(BigDecimal.TEN);
        return new Object[]{subscription};
    }

    @Test(dataProvider = "sub") // not working compareTo for BigDecimal for some reason
    public void testSave(Subscription subscription) throws PersistentException {
        subscriptionService.save(subscription);

        Subscription expectedSub = subscriptionService.findById(subscription.getId());
        subscriptionService.delete(subscription.getId());

        assertEquals(subscription, expectedSub);
    }

    @Test(dataProvider = "sub")
    public void testDelete(Subscription subscription) throws PersistentException {
        subscriptionService.save(subscription);
        subscriptionService.delete(subscription.getId());
        Subscription expected = subscriptionService.findById(subscription.getId());

        assertNull(expected);
    }

    @Test
    public void testFindAll() throws PersistentException {
        List<Subscription> subscriptionList = subscriptionService.findAll();
        int expectedSubNumber = 2;

        assertEquals(subscriptionList.size(), expectedSubNumber);
    }

    @Test
    public void testFindById() throws PersistentException {
        Integer id = 1;
        Subscription subscription = subscriptionService.findById(id);

        assertEquals(subscription.getId(), id);
    }

    @Test
    public void testFindByUser() throws PersistentException {
        User user = new User(2);
        List<Subscription> subscriptionList = subscriptionService.findByUser(user);
        Integer expectedId = 1;

        assertEquals(subscriptionList.get(0).getId(), expectedId);
    }

    @Test
    public void testFindActiveByUser() throws PersistentException {
        User user = new User();
        user.setLogin("SubGuest");
        user.setPassword("12345");
        user.setEmail("mail@mail.ru");
        user.setRole(Role.VISITOR);

        Subscription subscription = new Subscription();
        subscription.setVisitor(user);
        subscription.setBeginDate(LocalDate.now().minusMonths(1));
        subscription.setEndDate(LocalDate.now().plusMonths(1));
        subscription.setPrice(BigDecimal.TEN);
        userService.save(user);
        subscriptionService.save(subscription);

        Subscription expectedActiveSub = subscriptionService.findActiveByUser(user);
        subscriptionService.delete(subscription.getId());
        userService.delete(user.getId());

        assertEquals(subscription, expectedActiveSub);
    }
    @Test
    public void testFindFrom() throws PersistentException {
        LocalDate from = LocalDate.parse("2020-10-02");
        List<Subscription> subscriptionList = subscriptionService.findFrom(from);
        int expected = 2;

        assertEquals(subscriptionList.size(),expected);
    }

    @Test
    public void testFindTo() throws PersistentException{
        LocalDate to = LocalDate.parse("2020-10-02");
        List<Subscription> subscriptionList = subscriptionService.findTo(to);
        int expected = 1;

        assertEquals(subscriptionList.size(),expected);
    }

    @Test
    public void testFindFromTo() throws PersistentException {
        LocalDate from = LocalDate.parse("2020-10-02");
        LocalDate to = LocalDate.parse("2020-11-02");
        List<Subscription> subscriptionList = subscriptionService.findFromTo(from,to);
        int expected = 2;

        assertEquals(subscriptionList.size(),expected);
    }

    @Test
    public void testFindByUserLogin() throws PersistentException {
        String login = "visitor";
        List<Subscription> subscriptionList = subscriptionService.findByUserLogin(login);
        int expected = 2;

        assertEquals(subscriptionList.size(),expected);
    }

    @Test
    public void testFindFromLogin() throws PersistentException {
        LocalDate from = LocalDate.parse("2020-10-02");
        String login = "visitor";
        List<Subscription> subscriptionList = subscriptionService.findFromLogin(from,login);
        int expected = 2;

        assertEquals(subscriptionList.size(),expected);
    }

    @Test
    public void testFindToLogin() throws PersistentException {
        LocalDate from = LocalDate.parse("2021-10-02");
        String login = "visitor";
        List<Subscription> subscriptionList = subscriptionService.findToLogin(from,login);
        int expected = 2;

        assertEquals(subscriptionList.size(),expected);
    }

    @Test
    public void testFindFromToLogin() throws PersistentException {
        LocalDate from = LocalDate.parse("2020-10-02");
        LocalDate to = LocalDate.parse("2020-11-02");
        String login = "visitor";
        List<Subscription> subscriptionList = subscriptionService.findFromToLogin(from,to,login);
        int expected = 2;

        assertEquals(subscriptionList.size(),expected);
    }

}