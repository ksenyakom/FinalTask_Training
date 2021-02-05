package by.ksu.training.service.impl;

import by.ksu.training.dao.GetDbProperties;
import by.ksu.training.dao.GetProperties;
import by.ksu.training.dao.Transaction;
import by.ksu.training.dao.TransactionImpl;
import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedTrainerService;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.ServiceImpl;
import by.ksu.training.service.UserService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import static org.testng.Assert.*;

public class AssignedTrainerServiceImplTest {
    private Connection connection;
    private Transaction transaction;
    UserService userService;
    AssignedTrainerService assignedTrainerService;
    int userId;


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
        assignedTrainerService = new AssignedTrainerServiceImpl();
        ((ServiceImpl) assignedTrainerService).setTransaction(transaction);
        userService = new UserServiceImpl();
        ((ServiceImpl) userService).setTransaction(transaction);

        User user = new User();
        user.setLogin("InterestingGuest");
        user.setPassword("12345");
        user.setEmail("mail@mail.ru");
        user.setRole(Role.TRAINER);
        userService.save(user);
        userId = user.getId();

    }

    @AfterClass
    public void destroy() throws PersistentException, SQLException {
        userService.delete(userId);
        transaction.commit();
        connection.close();
    }

    @Test
    public void testFindAll() throws PersistentException {
        List<AssignedTrainer> assignedTrainerList = assignedTrainerService.findAll();
        int expectedCount = 2;

        assertEquals(assignedTrainerList.size(), expectedCount);
    }

    @DataProvider(name = "assignedTrainer")
    public Object[] createData() {

        User visitor = new User(userId);
        User trainer = new User(4);

        AssignedTrainer assignedTrainer = new AssignedTrainer();
        assignedTrainer.setVisitor(visitor);
        assignedTrainer.setTrainer(trainer);
        assignedTrainer.setBeginDate(LocalDate.of(2020, 11, 30));
        assignedTrainer.setEndDate(LocalDate.of(2020, 12, 30));

        AssignedTrainer assignedTrainerWithoutEndDate = new AssignedTrainer();
        assignedTrainerWithoutEndDate.setVisitor(visitor);
        assignedTrainerWithoutEndDate.setTrainer(trainer);
        assignedTrainerWithoutEndDate.setBeginDate(LocalDate.of(2020, 12, 30));

        return new Object[]{
                assignedTrainer,
                assignedTrainerWithoutEndDate
        };
    }

    @Test(priority = 1, dataProvider = "assignedTrainer")
    public void testSave(AssignedTrainer assignedTrainer) throws PersistentException {
        assignedTrainerService.save(assignedTrainer);
        AssignedTrainer actual = assignedTrainerService.findById(assignedTrainer.getId());
        assignedTrainerService.delete(assignedTrainer.getId());

        assertEquals(actual, assignedTrainer);
    }

    @Test(priority = 1, expectedExceptions = PersistentException.class)
    public void testSaveException() throws PersistentException {
        AssignedTrainer assignedTrainer = new AssignedTrainer();
        User visitor = new User(userId);
        User trainer = new User(4);
        assignedTrainer.setVisitor(visitor);
        assignedTrainer.setTrainer(trainer);

        assignedTrainerService.save(assignedTrainer);
        AssignedTrainer actual = assignedTrainerService.findById(assignedTrainer.getId());
        assignedTrainerService.delete(assignedTrainer.getId());

        assertEquals(actual, assignedTrainer);
    }

    @Test(priority = 1, dataProvider = "assignedTrainer")
    public void testDelete(AssignedTrainer assignedTrainer) throws PersistentException {
        assignedTrainerService.save(assignedTrainer);
        assignedTrainerService.delete(assignedTrainer.getId());
        AssignedTrainer actual = assignedTrainerService.findById(assignedTrainer.getId());

        assertNull(actual);
    }

    @Test(priority = 2, dataProvider = "assignedTrainer")
    public void testFindById(AssignedTrainer assignedTrainer) throws PersistentException {
        assignedTrainerService.save(assignedTrainer);
        AssignedTrainer actual = assignedTrainerService.findById(assignedTrainer.getId());
        assignedTrainerService.delete(assignedTrainer.getId());
    }

    @Test(priority = 2)
    public void testFindTrainerByVisitor() throws PersistentException {
        User trainer = assignedTrainerService.findTrainerByVisitor(new User(2));
        Integer expectedId = 4;
        assertEquals(trainer.getId(), expectedId);
    }

    @Test(priority = 2)
    public void testFindVisitorsByTrainer() throws PersistentException {
        List<User> visitors = assignedTrainerService.findVisitorsByTrainer(new User(4));
        int expected = 1;
        assertEquals(visitors.size(), expected);
    }

    @Test(priority = 2)
    public void testFindAllActive() throws PersistentException {
        List<AssignedTrainer> assignedTrainerList = assignedTrainerService.findAllActive();
        int expected = 2;
        assertEquals(assignedTrainerList.size(), expected);
    }

    @Test(priority = 2, expectedExceptions = UnsupportedOperationException.class)
    public void testFindAllWithoutTrainer() throws PersistentException {
        assignedTrainerService.findAllWithoutTrainer();
    }


}