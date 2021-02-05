package by.ksu.training.service.impl;

import by.ksu.training.dao.database.PersonDao;
import by.ksu.training.entity.Person;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ServiceImpl;
import by.ksu.training.service.PersonService;

import java.util.List;

public class PersonServiceImpl extends ServiceImpl implements PersonService {
    @Override
    public List<Person> findAll() throws PersistentException {
        PersonDao personDao = transaction.createDao(PersonDao.class);
        return personDao.read();
    }

    @Override
    public Person findById(Integer id) throws PersistentException {
        PersonDao personDao = transaction.createDao(PersonDao.class);
        return personDao.read(id);
    }


    @Override
    public void save(Person person) throws PersistentException {
        PersonDao personDao = transaction.createDao(PersonDao.class);
        personDao.create(person);
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        PersonDao personDao = transaction.createDao(PersonDao.class);
        personDao.delete(id);
    }

}
