package by.ksu.training.dao.database.impl;

import by.ksu.training.dao.BaseDaoImpl;
import by.ksu.training.entity.Person;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.dao.database.PersonDao;
import by.ksu.training.service.ParseDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDaoImpl extends BaseDaoImpl implements PersonDao {
    private static Logger logger = LogManager.getLogger(PersonDaoImpl.class);
    ParseDate parseDate = new ParseDate();


    private static final String CREATE =
            "REPLACE INTO `person` (`id`,`surname`,`name`,`patronymic`,`date_of_birth`,`address`,`phone`,`achievements`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String READ_BY_ID = "SELECT * FROM `person` WHERE `id`= ?";
    private static final String READ_ALL = "SELECT * FROM `person`";
    private static final String UPDATE =
            "UPDATE `person` SET `surname`=?,`name`=?,`patronymic`=?,`date_of_birth`=?,`address`=?,`phone`=?,`achievements`=? WHERE `id` = ?";
    private static final String DELETE = "DELETE FROM `person` WHERE `id`= ?";

    @Override
    public Integer create(Person entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE)) {
            statement.setInt(1, entity.getId());
            statement.setString(2, entity.getSurname());
            statement.setString(3, entity.getName());
            statement.setString(4, entity.getPatronymic());
            statement.setDate(5, parseDate.localToSql(entity.getDateOfBirth()));
            statement.setString(6, entity.getAddress());
            statement.setString(7, entity.getPhone());
            statement.setString(8, entity.getAchievements());

            statement.executeUpdate();
            logger.debug("inserted person {}", entity);
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
        return entity.getId();
    }

    @Override
    public Person read(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Person person = null;

            if (resultSet.next()) {
                person = new Person();
                person.setId(id);
                person.setSurname(resultSet.getString("surname"));
                person.setName(resultSet.getString("name"));
                person.setPatronymic(resultSet.getString("patronymic"));
                person.setDateOfBirth(parseDate.sqlToLocal(resultSet.getDate("date_of_birth")));
                person.setAddress(resultSet.getString("address"));
                person.setPhone(resultSet.getString("phone"));
                person.setAchievements(resultSet.getString("achievements"));
            }
            return person;

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public Person read(Person person) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_ID)) {
            statement.setInt(1, person.getId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                person.setSurname(resultSet.getString("surname"));
                person.setName(resultSet.getString("name"));
                person.setPatronymic(resultSet.getString("patronymic"));
                person.setDateOfBirth(parseDate.sqlToLocal(resultSet.getDate("date_of_birth")));
                person.setAddress(resultSet.getString("address"));
                person.setPhone(resultSet.getString("phone"));
                person.setAchievements(resultSet.getString("achievements"));
            }
            return person;

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Person> read() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            Person person = null;
            List<Person> list = new ArrayList<>();
            while (resultSet.next()) {
                person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setSurname(resultSet.getString("surname"));
                person.setName(resultSet.getString("name"));
                person.setPatronymic(resultSet.getString("patronymic"));
                person.setAddress(resultSet.getString("address"));
                person.setPhone(resultSet.getString("phone"));
                person.setAchievements(resultSet.getString("achievements"));
                person.setDateOfBirth(parseDate.sqlToLocal(resultSet.getDate("date_of_birth")));
                list.add(person);
            }
            return list;

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public void update(Person entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getSurname());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getPatronymic());
            statement.setDate(4, parseDate.localToSql(entity.getDateOfBirth()));
            statement.setString(5, entity.getAddress());
            statement.setString(6, entity.getPhone());
            statement.setString(7, entity.getAchievements());
            statement.setInt(8, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.debug("deleted person id = {}", id);

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }


}
