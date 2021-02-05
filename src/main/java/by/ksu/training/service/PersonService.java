package by.ksu.training.service;

import by.ksu.training.entity.Person;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface PersonService extends EntityService<Person> {
    List<Person> findAll() throws PersistentException;

}
