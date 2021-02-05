package by.ksu.training.service.impl;

import by.ksu.training.dao.GetDbProperties;
import by.ksu.training.dao.GetProperties;
import by.ksu.training.dao.Transaction;
import by.ksu.training.dao.TransactionImpl;
import by.ksu.training.entity.Person;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.*;
import by.ksu.training.service.PersonService;
import by.ksu.training.service.UserService;
import org.testng.annotations.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.testng.Assert.*;

public class PersonServiceImplTest {
    private Connection connection;
    private Transaction transaction;
    PersonService personService;
    UserService userService;


    @BeforeClass
    public void init() throws PersistentException, ClassNotFoundException, SQLException, IOException {

        GetProperties getDBProperties = new GetDbProperties();
        Properties properties = getDBProperties.fromFile("properties/database.properties");
        String driverName = (String) properties.get("driver");
        String databaseUrl = (String) properties.get("db.url");
        Class.forName(driverName);

        connection = DriverManager.getConnection(databaseUrl, properties);
        connection.setAutoCommit(false);

        transaction = new TransactionImpl(connection);
        personService = new PersonServiceImpl();
        ((ServiceImpl) personService).setTransaction(transaction);
        userService = new UserServiceImpl();
        ((ServiceImpl) userService).setTransaction(transaction);
    }


    @AfterClass
    public void destroy() throws PersistentException, SQLException {
        transaction.commit();
        connection.close();
    }

    @Test
    public void testFindAll() throws PersistentException {
        List<Person> people = personService.findAll();
        int expectedCount = 4;

        assertEquals(people.size(), expectedCount);
    }

    @Test
    public void testFindById() throws PersistentException {
        Integer id = 2;
        Person person = personService.findById(id);

        assertEquals(person.getId(), id);
    }

    @Test(expectedExceptions = PersistentException.class)
    public void testSaveException() throws PersistentException {
        int anyNumber = 100;
        Person personWithoutName = new Person(anyNumber);
        personService.save(personWithoutName);
    }

    @Test
    public void testSave() throws PersistentException {
        User user = new User();
        user.setLogin("PersonGuest");
        user.setPassword("12345");
        user.setEmail("mail@mail.ru");
        user.setRole(Role.VISITOR);
        userService.save(user);
        Person person = new Person(user.getId());
        person.setName("Irina");
        personService.save(person);
        Person personRead = personService.findById(person.getId());

        personService.delete(person.getId());
        userService.delete(user.getId());

        assertEquals(personRead, person);
    }

    @Test
    public void testDelete() throws PersistentException {
        User user = new User();
        user.setLogin("PersonGuest");
        user.setPassword("12345");
        user.setEmail("mail@mail.ru");
        user.setRole(Role.VISITOR);
        userService.save(user);
        Person person = new Person(user.getId());
        person.setName("Irina");
        personService.save(person);
        personService.delete(person.getId());
        userService.delete(user.getId());

        Person personRead = personService.findById(person.getId());

        assertNull(personRead);
    }
}