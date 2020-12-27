package by.ksu.training.service.impl;

import by.ksu.training.dao.AssignedTrainerDao;
import by.ksu.training.dao.PersonDao;
import by.ksu.training.dao.UserDao;
import by.ksu.training.entity.Person;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.Trainer;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ServiceImpl;
import by.ksu.training.service.TrainerService;

import java.util.ArrayList;
import java.util.List;

public class TrainerServiceImpl extends ServiceImpl implements TrainerService {

    @Override
    public List<Trainer> findAll() throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        PersonDao personDao = transaction.createDao(PersonDao.class);
        AssignedTrainerDao atDao = transaction.createDao(AssignedTrainerDao.class);
        List<Person> listId = userDao.readPersonByRole(Role.TRAINER);
        List<Trainer> trainerList = new ArrayList<>();
        Trainer trainer;

        for (Person person : listId) {
            trainer = new Trainer(personDao.read(person));
            trainer.setVisitorList(atDao.readListVisitorsByTrainer(trainer));
        }

        return trainerList;
    }

    @Override
    public Trainer findByIdentity(Integer id) throws PersistentException {
        PersonDao personDao = transaction.createDao(PersonDao.class);
        AssignedTrainerDao atDao = transaction.createDao(AssignedTrainerDao.class);

        Trainer trainer = new Trainer(personDao.read(id));
        trainer.setVisitorList(atDao.readListVisitorsByTrainer(trainer));

        return trainer;
    }

    @Override
    public void save(Trainer trainer) throws PersistentException {
        PersonDao personDao = transaction.createDao(PersonDao.class);
        personDao.create(trainer);
    }

    @Override
    public void update(Trainer trainer) throws PersistentException {
        PersonDao personDao = transaction.createDao(PersonDao.class);
        personDao.update(trainer);
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        PersonDao personDao = transaction.createDao(PersonDao.class);
        personDao.delete(id);
    }
}
