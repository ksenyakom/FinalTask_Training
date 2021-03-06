package by.ksu.training.service;

import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface AssignedTrainerService extends EntityService<AssignedTrainer> {
    List<AssignedTrainer> findAll() throws PersistentException;

    List<AssignedTrainer> findAllActive() throws PersistentException;

    List<AssignedTrainer> findAllWithoutTrainer() throws PersistentException;

    User findTrainerByVisitor(User visitor) throws PersistentException;

    List<User> findVisitorsByTrainer(User trainer) throws PersistentException;

    boolean checkTrainerByVisitor(User trainer, User visitor) throws PersistentException;
}
