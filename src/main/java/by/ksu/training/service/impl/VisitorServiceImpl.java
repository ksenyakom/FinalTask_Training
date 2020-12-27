package by.ksu.training.service.impl;

import by.ksu.training.dao.AssignedComplexDao;
import by.ksu.training.dao.AssignedTrainerDao;
import by.ksu.training.dao.PersonDao;
import by.ksu.training.dao.UserDao;
import by.ksu.training.entity.Person;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.Visitor;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ServiceImpl;
import by.ksu.training.service.VisitorService;

import java.util.ArrayList;
import java.util.List;

public class VisitorServiceImpl extends ServiceImpl implements VisitorService {
    @Override
    public List<Visitor> findAll() throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        PersonDao personDao = transaction.createDao(PersonDao.class);
        AssignedTrainerDao atDao = transaction.createDao(AssignedTrainerDao.class);
        AssignedComplexDao acDao = transaction.createDao(AssignedComplexDao.class);
        List<Person> listId = userDao.readPersonByRole(Role.VISITOR);
        List<Visitor> visitorList = new ArrayList<>();
        Visitor visitor;

        for (Person person : listId) {
            visitor = new Visitor(personDao.read(person));
            visitor.setTrainer(atDao.readCurrentTrainerByVisitor(visitor));
            visitor.setComplexList(acDao.readUnexecutedByVisitor(visitor));
            visitorList.add(visitor);
        }

        return visitorList;
    }

    @Override
    public Visitor findById(Integer id) throws PersistentException {
        PersonDao personDao = transaction.createDao(PersonDao.class);
        AssignedTrainerDao atDao = transaction.createDao(AssignedTrainerDao.class);
        AssignedComplexDao acDao = transaction.createDao(AssignedComplexDao.class);
        Visitor visitor = new Visitor(personDao.read(id));
        visitor.setTrainer(atDao.readCurrentTrainerByVisitor(visitor));
        visitor.setComplexList(acDao.readUnexecutedByVisitor(visitor));

        return visitor;
    }


    @Override
    public void save(Visitor visitor) throws PersistentException {
        PersonDao personDao = transaction.createDao(PersonDao.class);

        personDao.create(visitor);
    }

    @Override
    public void update(Visitor visitor) throws PersistentException {
        PersonDao personDao = transaction.createDao(PersonDao.class);
        personDao.update(visitor);
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        PersonDao personDao = transaction.createDao(PersonDao.class);
        personDao.delete(id);
    }
}
