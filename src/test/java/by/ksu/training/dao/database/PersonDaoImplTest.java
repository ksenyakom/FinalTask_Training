package by.ksu.training.dao.database;

import by.ksu.training.dao.PersonDao;
import by.ksu.training.dao.Transaction;
import by.ksu.training.dao.UserDao;
import by.ksu.training.dao.database.TransactionImpl;
import by.ksu.training.entity.Person;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.testng.Assert.*;

public class PersonDaoImplTest {
    private Transaction transaction;
    private Connection connection;
    private List<Integer> listId;
    private PersonDao personDao;
    private UserDao  userDao;

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
        personDao = transaction.createDao(PersonDao.class);
        int userNumber = 3;
        listId = new ArrayList<>(userNumber);
        for (int i = 0; i < userNumber; i++) {
            User user = new User();
            user.setLogin("UniqueGuest"+i);
            user.setPassword("12345");
            user.setEmail("mail@mail.ru");
            user.setRole(Role.getByIdentity(1+i%2));
            listId.add(userDao.create(user));
        }
        transaction.commit();
    }

    @AfterClass
    public void destroy() throws PersistentException, SQLException {
        personDao.delete(listId.get(2));
        userDao.delete(listId.get(0));
        userDao.delete(listId.get(1));
        userDao.delete(listId.get(2));
        transaction.commit();
        connection.close();
    }

    @DataProvider(name = "person")
    public Object[] createData() {
        Person person = new Person();
        person.setId(listId.get(0));
        person.setName("Вася");
        person.setSurname("Пупкин");
        person.setPatronymic("Петрович");
        person.setDateOfBirth(LocalDate.of(1990, 6, 21));
        person.setAddress("Минск, пр.Независимости 1-1");
        person.setPhone("375298563214");
        person.setAchievements("show achievements");

        Person personEmpty = new Person();
        personEmpty.setId(listId.get(1));
        personEmpty.setName("Саша");
        return new Object[]{
                person,
                personEmpty
        };
    }

    @Test(priority = 0, dataProvider = "person")
    public void testCreateRead(Person person) throws PersistentException {
        personDao.create(person);
        Person actual = personDao.read(person.getId());
        personDao.delete(person.getId());
        transaction.commit();

        assertEquals(actual, person);
    }

    @Test(priority = 1, expectedExceptions = PersistentException.class)
    public void testCreateException() throws PersistentException {
        Person person = new Person();
        person.setId(listId.get(2));
        person.setName("Саша");

        personDao.create(person);
        personDao.create(person);
    }

    @Test(priority = 2, dataProvider = "person")
    public void testReadAll(Person person) throws PersistentException {
        personDao.create(person);
        List<Person> list = personDao.read();
        personDao.delete(person.getId());
        transaction.commit();

        assertTrue(list.contains(person));
    }

    @Test(priority = 3, dataProvider = "person")
    public void testUpdate(Person person) throws PersistentException {
        personDao.create(person);

        person.setName("Анна");
        person.setSurname("Пупкина");
        person.setPatronymic("Петровна");
        person.setDateOfBirth(LocalDate.of(1985, 6, 21));
        person.setAddress("Минск, пл. Свободы 1-100");
        person.setPhone("375291112233");
        person.setAchievements("do not show achievements");

        personDao.update(person);
        Person actual = personDao.read(person.getId());
        personDao.delete(person.getId());
        transaction.commit();

        assertEquals(actual, person);
    }

    @Test(priority = 3, expectedExceptions = PersistentException.class)
    public void testUpdateException() throws PersistentException {
        Person person = new Person();
        person.setId(listId.get(1) + listId.get(2));
        person.setName("Саша");

        personDao.update(person);
    }

    @Test(priority = 3, dataProvider = "person")
    public void testDeleteException(Person person) throws PersistentException {
        personDao.create(person);
        personDao.delete(person.getId());
        person = personDao.read(person.getId());

        assertNull(person);
    }
}