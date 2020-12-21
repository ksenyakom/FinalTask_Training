package by.ksu.training.dao;

import by.ksu.training.entity.Person;
import by.ksu.training.entity.Trainer;
import by.ksu.training.entity.Visitor;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface PersonDao extends Dao<Person> {
    List<Person> read() throws PersistentException;
}
