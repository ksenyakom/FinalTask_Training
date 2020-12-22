package by.ksu.training.dao.database;

import by.ksu.training.dao.ExerciseDao;
import by.ksu.training.dao.Transaction;
import by.ksu.training.dao.database.TransactionImpl;
import by.ksu.training.entity.Exercise;
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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class ExerciseDaoImplTest {
    private Transaction transaction;
    private Connection connection;
    private ExerciseDao exerciseDao;

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
        exerciseDao = transaction.createDao(ExerciseDao.class);
        transaction.commit();

        Exercise.types = List.of("common", "yoga"); //TODO где сделать инициализацию
    }

    @AfterClass
    public void destroy() throws SQLException {
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
        exercise.setType(1);
        return new Object[]{
                exercise
        };
    }

    @Test(priority = 1, dataProvider = "exercise")
    public void testCreateRead(Exercise exerciseActual) throws PersistentException {
        int id = exerciseDao.create(exerciseActual);
        exerciseActual.setId(id);
        Exercise exerciseExpected = exerciseDao.read(id);
        exerciseDao.delete(id);
        transaction.commit();

        assertEquals(exerciseActual, exerciseExpected);
    }


    @Test(priority = 2, dataProvider = "exercise")
    public void testUpdate(Exercise exerciseExpected) throws PersistentException {
        int id = exerciseDao.create(exerciseExpected);
        exerciseExpected.setId(id);

        exerciseExpected.setTitle("another Title");
        exerciseExpected.setAdjusting("another adjusting.");
        exerciseExpected.setMistakes("another mistakes.");
        exerciseExpected.setAudioPath("anotherAudio.img");
        exerciseExpected.setPicturePath("anotherPicture.img");

        exerciseDao.update(exerciseExpected);
        Exercise exerciseActual = exerciseDao.read(id);
        exerciseDao.delete(id);
        transaction.commit();

        assertEquals(exerciseActual, exerciseExpected);
    }

    @Test(priority = 3, dataProvider = "exercise")
    public void testDelete(Exercise exercise) throws PersistentException {
        int id = exerciseDao.create(exercise);
        exercise.setId(id);
        exerciseDao.delete(id);
        exercise = exerciseDao.read(id);
        transaction.commit();

        assertNull(exercise);
    }
}