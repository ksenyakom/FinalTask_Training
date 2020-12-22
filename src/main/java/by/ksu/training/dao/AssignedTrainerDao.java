package by.ksu.training.dao;

import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.entity.Trainer;
import by.ksu.training.entity.Visitor;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface AssignedTrainerDao extends Dao<AssignedTrainer>{
    List<Visitor> readListVisitorsByTrainer(Trainer trainer) throws PersistentException;
    Trainer readCurrentTrainerByVisitor(Visitor visitor) throws PersistentException; //TODO test
    AssignedTrainer readCurrentByVisitor(Visitor visitor) throws PersistentException;//TODO test
    List<AssignedTrainer> read() throws PersistentException;//TODO test
}
