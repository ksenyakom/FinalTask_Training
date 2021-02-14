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
    public void save(final AssignedTrainer assignedTrainer) throws PersistentException {
        try {
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
            transaction.commit();
        } catch (PersistentException e) {
            transaction.rollback();
            throw new PersistentException("AssignedTrainer can not be updated or saved", e);
        }
    }

    /**
     * Checks if trainer is a current assigned trainer for visitor.
     *
     * @param checkedTrainer - a trainer, who is checked to be visitor trainer.
     * @param visitor        - a visitor.
     * @return - true, if trainer is a current assigned trainer of visitor;
     * false, if not.
     * @throws PersistentException - if PersistentException occur in dao layout.
     */
    @Override
    public boolean checkTrainerByVisitor(final User checkedTrainer, final User visitor) throws PersistentException {
        AssignedTrainerDao dao = transaction.createDao(AssignedTrainerDao.class);

        User currentTrainer = dao.readCurrentTrainerByVisitor(visitor);

        return currentTrainer != null && checkedTrainer != null && checkedTrainer.getId().equals(currentTrainer.getId());
    }

    @Override
    public List<AssignedTrainer> findAll() throws PersistentException {
        AssignedTrainerDao dao = transaction.createDao(AssignedTrainerDao.class);
        List<AssignedTrainer> assignedTrainerList = dao.read();
        getUsersLogin(assignedTrainerList);
        return assignedTrainerList;
    }
    /**
     * Finds AssignedTrainer by id.
     * @param id - identity of record to find.
     * @return found AssignedTrainer object or null, if record with this id not found.
     * @throws PersistentException - if exception occur in dao layer.
     */
    @Override
    public AssignedTrainer findById(final Integer id) throws PersistentException {
        AssignedTrainerDao dao = transaction.createDao(AssignedTrainerDao.class);

        return dao.read(id);
    }

    @Override
    public User findTrainerByVisitor(final User visitor) throws PersistentException {
        AssignedTrainerDao dao = transaction.createDao(AssignedTrainerDao.class);

        User trainer = dao.readCurrentTrainerByVisitor(visitor);
        if (trainer != null) {
            UserDao userDao = transaction.createDao(UserDao.class);
            userDao.readLogin(List.of(trainer));
        }
        return trainer;
    }

    @Override
    public List<User> findVisitorsByTrainer(final User trainer) throws PersistentException {
        AssignedTrainerDao dao = transaction.createDao(AssignedTrainerDao.class);
        List<User> users = dao.readListVisitorsByTrainer(trainer);
        UserDao userDao = transaction.createDao(UserDao.class);
        userDao.readLogin(users);
        return users;
    }

    /**
     * Finds all AssignedTrainer for visitors with active subscription.
     *
     * @return list of AssignedTrainer, never null.
     * @throws PersistentException - if PersistentException occur in dao layout.
     */
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
    public void delete(final Integer id) throws PersistentException {
        try {
            AssignedTrainerDao dao = transaction.createDao(AssignedTrainerDao.class);
            dao.delete(id);
            transaction.commit();
        } catch (PersistentException e) {
            transaction.rollback();
            throw new PersistentException("AssignedTrainer can not be deleted", e);
        }
    }

    private void getUsersLogin(final List<AssignedTrainer> assignedTrainerList) throws PersistentException {
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
