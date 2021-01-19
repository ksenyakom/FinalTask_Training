package by.ksu.training.dao.database;

import by.ksu.training.dao.AssignedTrainerDao;
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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AssignedTrainerDaoImplTest {

    private Transaction transaction;
    private Connection connection;
    private UserDao userDao;
    private AssignedTrainerDao atDao;
    private int visitorId;
    private int trainerId1;
    private int trainerId2;

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
        atDao = transaction.createDao(AssignedTrainerDao.class);

        User user = new User();
        user.setLogin("TrainerPetya");
        user.setPassword("12345");
        user.setEmail("mail@mail.ru");
        user.setRole(Role.VISITOR);
        visitorId = userDao.create(user);
        user = new User();
        user.setLogin("VeryGoodVisitor");
        user.setPassword("112233");
        user.setEmail("mail@mail.ru");
        user.setRole(Role.TRAINER);
        trainerId1 = userDao.create(user);

        user = new User();
        user.setLogin("TrainerVasya");
        user.setPassword("445566");
        user.setEmail("mail@mail.ru");
        user.setRole(Role.TRAINER);
        trainerId2 = userDao.create(user);

        transaction.commit();
    }

    @AfterClass
    public void destroy() throws PersistentException, SQLException {
        userDao.delete(trainerId1);
        userDao.delete(trainerId2);
        userDao.delete(visitorId);
        transaction.commit();
        connection.close();
    }

    @DataProvider(name = "assignedTrainer")
    public Object[] createData() {
        User visitor = new User(visitorId);
        User trainer = new User(trainerId1);

        AssignedTrainer assignedTrainer = new AssignedTrainer();
        assignedTrainer.setVisitor(visitor);
        assignedTrainer.setTrainer(trainer);
        assignedTrainer.setBeginDate(LocalDate.of(2020, 11, 30));
        assignedTrainer.setEndDate(LocalDate.of(2020, 12, 30));

        AssignedTrainer assignedTrainer2 = new AssignedTrainer();
        assignedTrainer2.setVisitor(visitor);
        assignedTrainer2.setTrainer(trainer);
        assignedTrainer2.setBeginDate(LocalDate.of(2020, 12, 30));

        return new Object[]{
                assignedTrainer,
                assignedTrainer2
        };
    }


    @Test(priority = 1, dataProvider = "assignedTrainer")
    public void testCreateRead(AssignedTrainer assignedTrainer) throws PersistentException {
        int id = atDao.create(assignedTrainer);
        assignedTrainer.setId(id);
        AssignedTrainer actual = atDao.read(id);
        atDao.delete(id);
        transaction.commit();

        assertEquals(actual, assignedTrainer);
    }

    @Test(priority = 2, dataProvider = "assignedTrainer")
    public void testUpdate(AssignedTrainer assignedTrainer) throws PersistentException {
        int id = atDao.create(assignedTrainer);
        assignedTrainer.setId(id);

        User visitor = new User(visitorId);
        User trainer = new User(trainerId2);

        assignedTrainer.setTrainer(trainer);
        assignedTrainer.setBeginDate(LocalDate.of(2018, 12, 31));
        assignedTrainer.setEndDate(LocalDate.of(2019, 12, 31));

        atDao.update(assignedTrainer);
        AssignedTrainer actual = atDao.read(id);
        atDao.delete(id);
        transaction.commit();

        assertEquals(actual, assignedTrainer);
    }

    @Test(priority = 3, dataProvider = "assignedTrainer", expectedExceptions = PersistentException.class)
    public void testDeleteException(AssignedTrainer assignedTrainer) throws PersistentException {
        int id = atDao.create(assignedTrainer);
        assignedTrainer.setId(id);
        atDao.delete(id);
        atDao.read(id);
    }

    @Test(priority = 4, dataProvider = "assignedTrainer")
    public void testReadListVisitorsByTrainer(AssignedTrainer assignedTrainer) throws PersistentException {
        int id = atDao.create(assignedTrainer);
        assignedTrainer.setId(id);
        List<User> visitors = atDao.readListVisitorsByTrainer(assignedTrainer.getTrainer());
        atDao.delete(id);
        transaction.commit();

        assertTrue(visitors.contains(assignedTrainer.getVisitor()));
    }
}