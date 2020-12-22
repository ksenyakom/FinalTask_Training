package by.ksu.training.dao.database;

import by.ksu.training.dao.AssignedComplexDao;
import by.ksu.training.dao.ComplexDao;
import by.ksu.training.dao.Transaction;
import by.ksu.training.dao.UserDao;
import by.ksu.training.dao.database.TransactionImpl;
import by.ksu.training.entity.*;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import static org.testng.Assert.*;

public class AssignedComplexServiceDaoImplTest {
    private Transaction transaction;
    private Connection connection;
    private AssignedComplexDao assignedComplexDao;
    private UserDao userDao;
    private ComplexDao complexDao;
    private int visitorId;
    private int trainerId;
    private int complexId1;
    private int complexId2;

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
        assignedComplexDao = transaction.createDao(AssignedComplexDao.class);
        complexDao = transaction.createDao(ComplexDao.class);

        User user = new User();
        user.setLogin("ComplexGuest");
        user.setPassword("12345");
        user.setRole(Role.VISITOR);
        visitorId = userDao.create(user);
        user = new User();
        user.setLogin("ComplexTrainer");
        user.setPassword("112233");
        user.setRole(Role.TRAINER);
        trainerId = userDao.create(user);

        Complex complex1 = new Complex();
        complex1.setTitle("Силовая тренировка");
        Trainer trainer = new Trainer();
        trainer.setId(trainerId);
        complex1.setTrainerDeveloped(trainer);
        Complex complex2 = new Complex();
        complex2.setTitle("Базовая тренировка");
        complex2.setTrainerDeveloped(trainer);
        complexId1 = complexDao.create(complex1);
        complexId2 = complexDao.create(complex2);

        transaction.commit();
    }

    @AfterClass
    public void destroy() throws PersistentException, SQLException {
        complexDao.delete(complexId1);
        complexDao.delete(complexId2);
        userDao.delete(trainerId);
        userDao.delete(visitorId);
        transaction.commit();
        connection.close();
    }

    @DataProvider(name = "assignedComplex")
    public Object[] createData() {
        Visitor visitor = new Visitor();
        visitor.setId(visitorId);
        Complex complex = new Complex();
        complex.setId(complexId1);

        AssignedComplex assignedComplex = new AssignedComplex();
        assignedComplex.setVisitor(visitor);
        assignedComplex.setComplex(complex);
        assignedComplex.setDateExpected(LocalDate.of(2020, 12, 10));
        assignedComplex.setDateExecuted(LocalDate.of(2020, 12, 10));

        AssignedComplex assignedComplex2 = new AssignedComplex();
        assignedComplex2.setVisitor(visitor);
        assignedComplex2.setComplex(complex);
        assignedComplex2.setDateExpected(LocalDate.of(2020, 12, 30));
        // dateExecuted = null;
        return new Object[]{
                assignedComplex,
                assignedComplex2
        };
    }

    @Test(priority = 1, dataProvider = "assignedComplex")
    public void testCreateRead(AssignedComplex assignedComplex) throws PersistentException {
        int id = assignedComplexDao.create(assignedComplex);
        assignedComplex.setId(id);
        AssignedComplex actual = assignedComplexDao.read(id);
        assignedComplexDao.delete(id);
        transaction.commit();

        assertEquals(actual, assignedComplex);
    }


    @Test(priority = 2, dataProvider = "assignedComplex")
    public void testUpdate(AssignedComplex assignedComplex) throws PersistentException {
        int id = assignedComplexDao.create(assignedComplex);
        assignedComplex.setId(id);

        Complex complex = new Complex();
        complex.setId(complexId2); //TODO
        assignedComplex.setComplex(complex);
        assignedComplex.setDateExpected(LocalDate.of(2020, 12, 31));
        assignedComplex.setDateExecuted(LocalDate.of(2020, 12, 31));

        assignedComplexDao.update(assignedComplex);
        AssignedComplex actual = assignedComplexDao.read(id);
        assignedComplexDao.delete(id);
        transaction.commit();

        assertEquals(actual, assignedComplex);
    }

    @Test(priority = 3, dataProvider = "assignedComplex")
    public void testDelete(AssignedComplex assignedComplex) throws PersistentException {
        int id = assignedComplexDao.create(assignedComplex);
        assignedComplexDao.delete(id);
        assignedComplex = assignedComplexDao.read(id);
        transaction.commit();

        assertNull(assignedComplex);
    }

    @Test(priority = 4, dataProvider = "assignedComplex")
    public void testReadUnexecutedByVisitor(AssignedComplex assignedComplex) throws PersistentException {
        int id = assignedComplexDao.create(assignedComplex);
        assignedComplex.setId(id);

        List<AssignedComplex> list = assignedComplexDao.readUnexecutedByVisitor(assignedComplex.getVisitor());

        if (assignedComplex.getDateExecuted() == null) {
            assertTrue(list.contains(assignedComplex));
        } else {
            assertFalse(list.contains(assignedComplex));
        }
        assignedComplexDao.delete(id);
        transaction.commit();
    }

    @DataProvider(name = "period")
    public Object[][] createData1() {
        Visitor visitor = new Visitor();
        visitor.setId(visitorId);
        Complex complex = new Complex();
        complex.setId(complexId1);
        int periodDays = 30;

        AssignedComplex assignedComplex = new AssignedComplex();
        assignedComplex.setVisitor(visitor);
        assignedComplex.setComplex(complex);
        LocalDate now = LocalDate.now();
        assignedComplex.setDateExpected(now);
        assignedComplex.setDateExecuted(now);

        AssignedComplex assignedComplex2 = new AssignedComplex();
        assignedComplex2.setVisitor(visitor);
        assignedComplex2.setComplex(complex);
        LocalDate firstDayOfPeriod = LocalDate.now().minusDays(periodDays-1);
        assignedComplex2.setDateExpected(firstDayOfPeriod);
        assignedComplex2.setDateExecuted(firstDayOfPeriod);
        // dateExecuted = null;
        return new Object[][]{
                {assignedComplex, periodDays},
                {assignedComplex2, periodDays}
        };
    }

    @Test(priority = 4, dataProvider = "period")
    public void testReadExecutedByVisitorForPeriod(AssignedComplex assignedComplex, int periodDays) throws PersistentException {
        int id = assignedComplexDao.create(assignedComplex);
        assignedComplex.setId(id);

        List<AssignedComplex> list = assignedComplexDao
                .readExecutedByVisitorForPeriod(assignedComplex.getVisitor(), periodDays);

        if (assignedComplex.getDateExecuted() == null) {
            assertFalse(list.contains(assignedComplex));
        } else {
            assertTrue(list.contains(assignedComplex));
        }
        assignedComplexDao.delete(id);
        transaction.commit();
    }

    @Test(priority = 4, dataProvider = "period")
    public void testReadExecutedForPeriod(AssignedComplex assignedComplex, int periodDays) throws PersistentException {
        int id = assignedComplexDao.create(assignedComplex);
        assignedComplex.setId(id);
        List<AssignedComplex> list = assignedComplexDao.readExecutedForPeriod(periodDays);
        if (assignedComplex.getDateExecuted() == null) {
            assertFalse(list.contains(assignedComplex));
        } else {
            assertTrue(list.contains(assignedComplex));
        }
        assignedComplexDao.delete(id);
        transaction.commit();
    }
}