package by.ksu.training.dao.database;

import by.ksu.training.dao.Dao;
import by.ksu.training.entity.Person;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface PersonDao extends Dao<Person> {
    List<Person> read() throws PersistentException;

    Person read(Person person) throws PersistentException;
}
