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

    /**
     * Finds Person by id.
     *
     * @param id - identity of record to find.
     * @return found Person object or null, if record with this id not found.
     * @throws PersistentException - if exception occur in dao layer.
     */
    @Override
    public Person findById(final Integer id) throws PersistentException {
        PersonDao personDao = transaction.createDao(PersonDao.class);
        return personDao.read(id);
    }


    @Override
    public void save(final Person person) throws PersistentException {
        try {
            PersonDao personDao = transaction.createDao(PersonDao.class);
            personDao.create(person);
            transaction.commit();
        } catch (PersistentException e) {
            transaction.rollback();
            throw new PersistentException("Person can not be updated or saved", e);
        }
    }

    @Override
    public void delete(final Integer id) throws PersistentException {
        try {
            PersonDao personDao = transaction.createDao(PersonDao.class);
            personDao.delete(id);
            transaction.commit();
        } catch (PersistentException e) {
            transaction.rollback();
            throw new PersistentException("Person can not be deleted", e);
        }
    }

}
