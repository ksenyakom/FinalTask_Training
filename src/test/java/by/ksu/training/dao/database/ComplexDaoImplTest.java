package by.ksu.training.dao.database;

import by.ksu.training.dao.*;
import by.ksu.training.entity.*;
import by.ksu.training.exception.PersistentException;
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

public class ComplexDaoImplTest {
    private Transaction transaction;
    private Connection connection;
    private ComplexDao complexDao;
    private UserDao userDao;
    private ExerciseDao exerciseDao;

    private int visitorId;
    private int trainerId;
    private int exerciseId1;
    private int exerciseId2;

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
        userDao = transaction.createDao(UserDao.class);
        exerciseDao = transaction.createDao(ExerciseDao.class);
        complexDao = transaction.createDao(ComplexDao.class);

        User user = new User();
        user.setLogin("ComplexGuest");
        user.setPassword("12345");
        user.setEmail("mail@mail.ru");
        user.setRole(Role.VISITOR);
        visitorId = userDao.create(user);

        user = new User();
        user.setLogin("ComplexTrainer");
        user.setPassword("112233");
        user.setEmail("mail@mail.ru");
        user.setRole(Role.TRAINER);
        trainerId = userDao.create(user);


        Exercise exercise = new Exercise();
        exercise.setTitle("Тадасана");
        exercise.setAdjusting("Adjusting.");
        exercise.setMistakes("Mistakes.");
        exercise.setAudioPath("audio.mp3");
        exercise.setPicturePath("picture.img");
        exercise.setType("yoga");
        exerciseId1 = exerciseDao.create(exercise);

        Exercise exercise2 = new Exercise();
        exercise2.setTitle("Шавасана");
        exercise2.setAdjusting("Adjusting shavasana.");
        exercise2.setMistakes("Mistakes shavasana.");
        exercise2.setAudioPath("audio1.mp3");
        exercise2.setPicturePath("picture1.img");
        exercise2.setType("yoga");
        exerciseId2 = exerciseDao.create(exercise2);

        Complex.ComplexUnit unit = new Complex.ComplexUnit();
        exercise.setId(exerciseId1);
        unit.setExercise(exercise);
        unit.setGroup(0);
        unit.setRepeat(0);


 //       int userNumber = 3;
//        listId = new ArrayList<>(userNumber);
//        for (int i = 0; i < userNumber; i++) {
//            User user = new User();
//            user.setLogin("UniqueGuest"+i);
//            user.setPassword("12345");
//            user.setRole(Role.getByIdentity(1+i%2));
//            listId.add(userDao.create(user));
//        }
        transaction.commit();
    }

    @AfterClass
    public void destroy() throws PersistentException, SQLException {
        exerciseDao.delete(exerciseId1);
        exerciseDao.delete(exerciseId2);
        userDao.delete(trainerId);
        userDao.delete(visitorId);
        transaction.commit();
        connection.close();
    }

    @DataProvider (name = "complex")
    public Object[] createData() {
        Complex.ComplexUnit unit = new Complex.ComplexUnit();
        Exercise exercise = new Exercise();
        exercise.setId(exerciseId1);
        unit.setExercise(exercise);
        unit.setGroup(0);
        unit.setRepeat(0);

        Complex complex = new Complex();
        complex.setTitle("Растяжка111");
        User trainer = new User(trainerId);
        complex.setTrainerDeveloped(trainer);
        User visitor = new User(visitorId);
        complex.setVisitorFor(visitor);
        complex.setRating(4.5f);
        complex.setListOfComplexUnit(List.of(unit, unit, unit));

        Complex complexWithoutVisitorFor = new Complex();
        complexWithoutVisitorFor.setTitle("Растяжка999");
        complexWithoutVisitorFor.setTrainerDeveloped(trainer);
        complexWithoutVisitorFor.setRating(3f);
        complexWithoutVisitorFor.setListOfComplexUnit(List.of(unit, unit, unit));


        Complex complexWithoutVisitorAndTrainer = new Complex();
        complexWithoutVisitorAndTrainer.setTitle("Растяжка555");
        complexWithoutVisitorAndTrainer.setRating(3.9f);
        complexWithoutVisitorAndTrainer.setListOfComplexUnit(List.of(unit, unit, unit));
        return new Object[] {
                complex,
                complexWithoutVisitorFor,
                complexWithoutVisitorAndTrainer
        };
    }


    @Test(priority = 1, dataProvider = "complex")
    public void testCreateRead(Complex complex) throws PersistentException {
        int id = complexDao.create(complex);
        complex.setId(id);
        Complex actual = complexDao.read(id);
        complexDao.delete(id);
        transaction.commit();

        assertEquals(actual, complex);
    }

    @Test(priority = 2, dataProvider = "complex")
    public void testUpdate(Complex complex) throws PersistentException {
        int id = complexDao.create(complex);
        complex.setId(id);

        complex.setTitle("Растяжка-разминка");
        complex.setRating(5);
        complex.getComplexUnit(0).getExercise().setId(exerciseId2);
        complex.deleteComplexUnit(1);

        complexDao.update(complex);
        Complex actual = complexDao.read(id);
        complexDao.delete(id);

        assertEquals(actual, complex);
    }


    @Test(priority = 3, dataProvider = "complex")
    public void testDelete(Complex complex) throws PersistentException {
        int id = complexDao.create(complex);
        complexDao.delete(id);
        complex = complexDao.read(id);
        transaction.commit();

        assertNull(complex);
    }

    @Test(priority = 3, dataProvider = "complex")
    public void testReadTitle(Complex complex) throws PersistentException {

            int id = complexDao.create(complex);
            Complex complex1 = new Complex(id);
            complexDao.readTitle(List.of(complex1));
            complexDao.delete(id);
            transaction.commit();

            assertEquals(complex1.getTitle(), complex.getTitle());
    }

    // test for ordering of complexes by rating
    @Test(priority = 3, dataProvider = "complex")
    public void testReadAllCommonComplexMetaData(Complex complex) throws PersistentException {
        int id = complexDao.create(complex);
        Complex complex1 = new Complex(id);
        List<Complex> complexes = complexDao.readAllCommonComplexMetaData();
        complexDao.delete(id);
        transaction.commit();

        if (complexes.size() > 1) {

            assertTrue(complexes.get(0).getRating() > complexes.get(1).getRating(),
                    String.format("rating complex 0 : %s ; rating complex 1 : %s" ,
                            complexes.get(0).getRating(), complexes.get(1).getRating()));
        }
        }
}