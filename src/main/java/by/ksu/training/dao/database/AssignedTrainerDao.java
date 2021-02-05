package by.ksu.training.dao.database;

import by.ksu.training.dao.Dao;
import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface AssignedTrainerDao extends Dao<AssignedTrainer> {
    List<User> readListVisitorsByTrainer(User trainer) throws PersistentException;
    User readCurrentTrainerByVisitor(User visitor) throws PersistentException; //TODO test
    AssignedTrainer readCurrentByVisitor(User visitor) throws PersistentException;//TODO test
    List<AssignedTrainer> read() throws PersistentException;//TODO test

    List<AssignedTrainer> readCurrentByUsers(List<User> users) throws PersistentException;;
}
