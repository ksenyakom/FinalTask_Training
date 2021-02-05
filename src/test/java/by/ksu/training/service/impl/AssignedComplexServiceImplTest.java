package by.ksu.training.service.impl;

import by.ksu.training.dao.GetDbProperties;
import by.ksu.training.dao.GetProperties;
import by.ksu.training.dao.Transaction;
import by.ksu.training.dao.TransactionImpl;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;

import static org.testng.Assert.*;

public class AssignedComplexServiceImplTest {
    private Connection connection;
    private Transaction transaction;
    ComplexService complexService;
    UserService userService;
    AssignedComplexService assignedComplexService;



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
        complexService = new ComplexServiceImpl();
        ((ServiceImpl) complexService).setTransaction(transaction);
        userService = new UserServiceImpl();
        ((ServiceImpl) userService).setTransaction(transaction);
        assignedComplexService = new AssignedComplexServiceImpl();
        ((ServiceImpl) assignedComplexService).setTransaction(transaction);

    }

    @AfterClass
    public void destroy() throws PersistentException, SQLException {
        transaction.commit();
        connection.close();
    }

    @DataProvider(name = "assignedComplex")
    public Object[] createData() {
        Complex complex = new Complex(1);
        User user = new User(2);

        AssignedComplex assignedComplex = new AssignedComplex();
        assignedComplex.setVisitor(user);
        assignedComplex.setComplex(complex);
        assignedComplex.setDateExpected(LocalDate.of(2020, 12, 10));
        assignedComplex.setDateExecuted(LocalDate.of(2020, 12, 10));

        AssignedComplex assignedComplexWithoutExecutedDate = new AssignedComplex();
        assignedComplexWithoutExecutedDate.setVisitor(user);
        assignedComplexWithoutExecutedDate.setComplex(complex);
        assignedComplexWithoutExecutedDate.setDateExpected(LocalDate.of(2020, 12, 30));
        // dateExecuted = null;
        return new Object[]{
                assignedComplex,
                assignedComplexWithoutExecutedDate
        };
    }

    @Test(priority = 1, dataProvider = "assignedComplex")
    public void testSaveFind(AssignedComplex assignedComplex) throws PersistentException {
        assignedComplexService.save(assignedComplex);
        AssignedComplex actual = assignedComplexService.findById(assignedComplex.getId());
        assignedComplexService.delete(assignedComplex.getId());

        assertEquals(actual, assignedComplex);
    }

    @Test(priority = 1, expectedExceptions = PersistentException.class)
    public void testSaveFindException() throws PersistentException {
        Complex complex = new Complex(1);
        User user = new User(2);

        AssignedComplex assignedComplex = new AssignedComplex();
        assignedComplex.setVisitor(user);
        assignedComplex.setComplex(complex);
        assignedComplexService.save(assignedComplex);

    }

    @Test (priority = 1, dataProvider = "assignedComplex")
    public void testDelete(AssignedComplex assignedComplex) throws PersistentException {
        assignedComplexService.save(assignedComplex);
        assignedComplexService.delete(assignedComplex.getId());
        AssignedComplex actual = assignedComplexService.findById(assignedComplex.getId());

        assertNull(actual);
    }

    @Test
    public void testFindUnexecutedByUser() throws PersistentException {
        List<AssignedComplex> list = assignedComplexService.findUnexecutedByUser(new User(2));
        int expected = 2;
        assertEquals(list.size(), expected);
    }

    @Test
    public void testFindExecutedByUserForPeriod() throws PersistentException {
        LocalDate today = LocalDate.now();
        LocalDate firstAssignmentDate = LocalDate.parse("2020-01-12");
        int periodDays = (int)firstAssignmentDate.until(today, ChronoUnit.DAYS);
        List<AssignedComplex> list = assignedComplexService.findExecutedByUserForPeriod(new User(3), periodDays);
        int expected = 11;
        assertEquals(list.size(), expected);
    }

    @Test
    public void testFindExecutedForPeriod() throws PersistentException {
        LocalDate today = LocalDate.now();
        LocalDate firstAssignmentDate = LocalDate.parse("2020-01-12");
        int periodDays = (int)firstAssignmentDate.until(today, ChronoUnit.DAYS);
        List<AssignedComplex> list = assignedComplexService.findExecutedForPeriod(periodDays);
        int expected = 14;
        assertEquals(list.size(), expected);
    }
}