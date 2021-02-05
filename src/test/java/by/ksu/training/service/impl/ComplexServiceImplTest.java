package by.ksu.training.service.impl;

import by.ksu.training.dao.GetDbProperties;
import by.ksu.training.dao.GetProperties;
import by.ksu.training.dao.Transaction;
import by.ksu.training.dao.TransactionImpl;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.Exercise;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.ServiceImpl;

import static org.testng.Assert.*;

import by.ksu.training.service.UserService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class ComplexServiceImplTest {
    private Connection connection;
    private Transaction transaction;
    ComplexService complexService;
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
        complexService = new ComplexServiceImpl();
        ((ServiceImpl) complexService).setTransaction(transaction);
        userService = new UserServiceImpl();
        ((ServiceImpl) userService).setTransaction(transaction);
    }

    @AfterClass
    public void destroy() throws PersistentException, SQLException {
        transaction.commit();
        connection.close();
    }

    @DataProvider(name = "complex")
    public Object[] createData() {
        Complex.ComplexUnit unit = new Complex.ComplexUnit();
        unit.setExercise(new Exercise(1));
        unit.setGroup(0);
        unit.setRepeat(0);

        Complex complex = new Complex();
        complex.setTitle("Фитнес йога");
        User trainer = new User(4);
        complex.setTrainerDeveloped(trainer);
        User visitor = new User(3);
        complex.setVisitorFor(visitor);
        complex.setRating(4.5f);
        complex.setListOfComplexUnit(List.of(unit, unit, unit));

        Complex complexWithoutVisitorFor = new Complex();
        complexWithoutVisitorFor.setTitle("Фитнес йога 1");
        complexWithoutVisitorFor.setTrainerDeveloped(trainer);
        complexWithoutVisitorFor.setRating(3f);
        complexWithoutVisitorFor.setListOfComplexUnit(List.of(unit, unit, unit));


        Complex complexWithoutVisitorAndTrainer = new Complex();
        complexWithoutVisitorAndTrainer.setTitle("Фитнес йога 2");
        complexWithoutVisitorAndTrainer.setRating(3.9f);
        complexWithoutVisitorAndTrainer.setListOfComplexUnit(List.of(unit, unit, unit));
        return new Object[]{
                complex,
                complexWithoutVisitorFor,
                complexWithoutVisitorAndTrainer
        };
    }

    @Test(priority = 1, dataProvider = "complex")
    public void testSave(Complex complex) throws PersistentException {
        complexService.save(complex);
        Complex actual = complexService.findById(complex.getId());
        complexService.delete(complex.getId());

        assertEquals(actual, complex);
    }

    @Test(priority = 1, expectedExceptions = PersistentException.class)
    public void testSaveException() throws PersistentException {
        Complex complex = new Complex();
        complexService.save(complex);
    }

    @Test(priority = 1, dataProvider = "complex")
    public void testDelete(Complex complex) throws PersistentException {
        complexService.save(complex);
        complexService.delete(complex.getId());
        Complex actual = complexService.findById(complex.getId());

        assertNull(actual);
    }

    @Test(priority = 1, dataProvider = "complex")
    public void testFindById(Complex complex) throws PersistentException {
        complexService.save(complex);

        Complex actual = complexService.findById(complex.getId());
        complexService.delete(complex.getId());

        assertEquals(actual, complex);
    }

    @DataProvider(name = "checkEdit")
    public Object[][] createData1() {
        return new Object[][]{
                {1, 4, false}, //trainer
                {1, 1, true}, //admin
                {2, 4, false}, //trainer
                {2, 1, true}, //admin
                {3, 4, true},// trainer
                {3, 5, false},//other trainer
                {3, 1, false}, //admin
        };
    }

    @Test(priority = 2, dataProvider = "checkEdit")
    public void testCheckEditAllowed(int complexId, int userId, boolean expected) throws PersistentException {
        Complex complex = complexService.findById(complexId);
        User user = userService.findById(userId);

        boolean actual = complexService.checkEditAllowed(user, complex);
        assertEquals(actual, expected);
    }

    @Test(priority = 2, dataProvider = "complex")
    public void testCheckTitleExistTrue(Complex complex) throws PersistentException {
        complexService.save(complex);
        boolean actual = complexService.checkTitleExist(complex.getTitle());
        complexService.delete(complex.getId());

        assertTrue(actual);
    }

    @Test(priority = 2, dataProvider = "complex")
    public void testCheckTitleExistFalse(Complex complex) throws PersistentException {
        boolean actual = complexService.checkTitleExist(complex.getTitle());

        assertFalse(actual);
    }

    @Test(priority = 2)
    public void testFindAllCommonComplexMetaData() throws PersistentException {
        List<Complex> complexes = complexService.findAllCommonComplexMetaData();
        int expectedCount = 2;

        assertEquals(complexes.size(), expectedCount);
    }

    @Test(priority = 2)
    public void testFindAllMetaData() throws PersistentException {
        List<Complex> complexes = complexService.findAllMetaData();
        int expectedCount = 3;

        assertEquals(complexes.size(), expectedCount);

    }

    @Test(priority = 2)
    public void testFindIndividualComplexMetaDataByUsers() throws PersistentException {
        User user = new User(2);
        List<Complex> complexes = complexService.findIndividualComplexMetaDataByUsers(List.of(user));
        int expectedCount = 1;

        assertEquals(complexes.size(), expectedCount);
    }

    @Test(priority = 2)
    public void testFindComplexesMetaDataByUser() throws PersistentException {
        User user = new User(2);
        List<Complex> complexes = complexService.findComplexesMetaDataByUser(user);
        int expectedCount = 3;

        assertEquals(complexes.size(), expectedCount);
    }

    @Test(priority = 2)
    public void testFindTitle() throws PersistentException {
        Complex complex = new Complex(2);
        complexService.findTitle(List.of(complex));
        String expected = "Фитнес";

        assertEquals(complex.getTitle(), expected );
    }

    @Test(priority = 2)
    public void testFindAll() throws PersistentException {
        List<Complex> complexes = complexService.findAll();
        int expectedCount = 3;

        assertEquals(complexes.size(), expectedCount);
    }

}