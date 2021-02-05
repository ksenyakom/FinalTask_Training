package by.ksu.training.service.impl;

import by.ksu.training.dao.GetDbProperties;
import by.ksu.training.dao.GetProperties;
import by.ksu.training.dao.Transaction;
import by.ksu.training.dao.TransactionImpl;
import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ExerciseService;
import by.ksu.training.service.ServiceImpl;
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

public class ExerciseServiceImplTest {
    private Connection connection;
    private Transaction transaction;
    ExerciseService exerciseService;

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
        exerciseService = new ExerciseServiceImpl();
        ((ServiceImpl) exerciseService).setTransaction(transaction);
    }

    @AfterClass
    public void destroy() throws PersistentException, SQLException {
        transaction.commit();
        connection.close();
    }

    @DataProvider(name = "exercise")
    public Object[] createData() {
        Exercise exercise = new Exercise();
        exercise.setTitle("Тадасана");
        exercise.setAdjusting("Adjusting.");
        exercise.setMistakes("Mistakes.");
        exercise.setAudioPath("audio.mp3");
        exercise.setPicturePath("picture.img");
        exercise.setType("yoga");
        return new Object[]{
                exercise
        };
    }

    @Test(dataProvider = "exercise", priority = 1)
    public void testSaveFind(Exercise exercise) throws PersistentException {
        exerciseService.save(exercise);
        Exercise actual = new Exercise(exercise.getId());
        exerciseService.find(List.of(actual));
        exerciseService.delete(exercise.getId());

        assertEquals(actual, exercise);
    }

    @Test(priority = 1, expectedExceptions = PersistentException.class)
    public void testSaveException() throws PersistentException {
        Exercise exercise = new Exercise();
        exerciseService.save(exercise);

    }


    @Test (dataProvider = "exercise",priority = 1)
    public void testDelete(Exercise exercise) throws PersistentException {
        exerciseService.save(exercise);
        exerciseService.delete(exercise.getId());
        Exercise actual = exerciseService.findById(exercise.getId());

        assertNull(actual);
    }

    @Test (priority = 2)
    public void testFindAll() throws PersistentException {
        List<Exercise> exercises = exerciseService.findAll();
        int expectedCount = 6;

        assertEquals(exercises.size(), expectedCount);
    }

    @Test (priority = 2)
    public void testFindExerciseTypes() throws PersistentException {
        List<String> types = exerciseService.findExerciseTypes();

        assertTrue(types.contains("yoga"));
        assertTrue(types.contains("common"));
    }

    @Test (priority = 2)
    public void testFindTotalCount() throws PersistentException {
        int count = exerciseService.findTotalCount();
        int expectedCount = 6;

        assertEquals(count, expectedCount);
    }



    @DataProvider(name = "readByPages")
    public Object[][] createData1() {
        return new Object[][]{
                {1,5,5},
                {2,5,1},
                {3,5,0},
        };
    }

    @Test(dataProvider = "readByPages", priority = 2)
    public void testTestFind(int currentPage, int recordsPerPage, int expectedCount) throws PersistentException {
        List<Exercise> exercises = exerciseService.find(currentPage, recordsPerPage);

        assertEquals(exercises.size(), expectedCount);
    }

    @Test(dataProvider = "exercise", priority = 2)
    public void testFindById(Exercise exercise) throws PersistentException {
        exerciseService.save(exercise);
        Exercise actual = new Exercise(exercise.getId());
        actual = exerciseService.findById(actual.getId());
        exerciseService.delete(exercise.getId());

        assertEquals(actual, exercise);
    }

}