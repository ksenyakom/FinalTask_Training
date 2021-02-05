package by.ksu.training.service.impl;

import by.ksu.training.dao.database.AssignedTrainerDao;
import by.ksu.training.dao.database.SubscriptionDao;
import by.ksu.training.dao.database.UserDao;
import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedTrainerService;
import by.ksu.training.service.ServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AssignedTrainerServiceImpl extends ServiceImpl implements AssignedTrainerService {
    @Override
    public boolean checkTrainerByVisitor(User checkedTrainer, User visitor) throws PersistentException {
        AssignedTrainerDao dao = transaction.createDao(AssignedTrainerDao.class);

        User currentTrainer = dao.readCurrentTrainerByVisitor(visitor);

        if (currentTrainer != null && checkedTrainer!= null && checkedTrainer.getId().equals(currentTrainer.getId())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<AssignedTrainer> findAll() throws PersistentException {
        AssignedTrainerDao dao = transaction.createDao(AssignedTrainerDao.class);
        List<AssignedTrainer> assignedTrainerList = dao.read();
        getUsersLogin(assignedTrainerList);
        return assignedTrainerList;
    }

    @Override
    public AssignedTrainer findById(Integer id) throws PersistentException {
        AssignedTrainerDao dao = transaction.createDao(AssignedTrainerDao.class);

        return dao.read(id);
    }

    @Override
    public User findTrainerByVisitor(User visitor) throws PersistentException {
        AssignedTrainerDao dao = transaction.createDao(AssignedTrainerDao.class);
        return dao.readCurrentTrainerByVisitor(visitor);
    }

    @Override
    public List<User> findVisitorsByTrainer(User trainer) throws PersistentException {
        AssignedTrainerDao dao = transaction.createDao(AssignedTrainerDao.class);
        List<User> users = dao.readListVisitorsByTrainer(trainer);
        UserDao userDao = transaction.createDao(UserDao.class);
        userDao.readLogin(users);
        return users;
    }

    @Override
    public List<AssignedTrainer> findAllActive() throws PersistentException {
        SubscriptionDao subscriptionDao = transaction.createDao(SubscriptionDao.class);
        List<Subscription> subscriptionList = subscriptionDao.readAllActive();
        List<User> users = subscriptionList.stream()
                .map(Subscription::getVisitor)
                .distinct()
                .collect(Collectors.toList());
        AssignedTrainerDao assignedTrainerDao = transaction.createDao(AssignedTrainerDao.class);
        List<AssignedTrainer> assignedTrainerList = assignedTrainerDao.readCurrentByUsers(users);
        getUsersLogin(assignedTrainerList);
        return assignedTrainerList;
    }

    @Override
    public List<AssignedTrainer> findAllWithoutTrainer() throws PersistentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(AssignedTrainer assignedTrainer) throws PersistentException {
        AssignedTrainerDao dao = transaction.createDao(AssignedTrainerDao.class);
        if (assignedTrainer.getId() != null) {
            dao.update(assignedTrainer);
        } else {
            // check if there is assigned trainer for this visitor
            AssignedTrainer existAssignment = dao.readCurrentByVisitor(assignedTrainer.getVisitor());
            if (existAssignment != null) {
                existAssignment.setEndDate(LocalDate.now());
                dao.update(existAssignment);
            }
            Integer id = dao.create(assignedTrainer);
            assignedTrainer.setId(id);
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        AssignedTrainerDao dao = transaction.createDao(AssignedTrainerDao.class);
        dao.delete(id);
    }

    private void getUsersLogin(List<AssignedTrainer> assignedTrainerList) throws PersistentException {
        List<User> trainers = assignedTrainerList.stream()
                .filter(assignedTrainer -> assignedTrainer.getTrainer() != null)
                .map(AssignedTrainer::getTrainer)
                .distinct()
                .collect(Collectors.toList());
        List<User> users = assignedTrainerList.stream()
                .filter(assignedTrainer -> assignedTrainer.getVisitor() != null)
                .map(AssignedTrainer::getVisitor)
                .distinct()
                .collect(Collectors.toList());

        users.addAll(trainers);
        UserDao userDao = transaction.createDao(UserDao.class);
        userDao.readLogin(users);

    }
}
