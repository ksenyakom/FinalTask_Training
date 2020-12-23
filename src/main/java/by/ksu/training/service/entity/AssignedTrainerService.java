package by.ksu.training.service.entity;

import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.entity.Trainer;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.EntityService;

import java.util.List;

public interface AssignedTrainerService extends EntityService {
    List<AssignedTrainer> findAll() throws PersistentException;

    AssignedTrainer findByIdentity(Integer id) throws PersistentException;

    void save(AssignedTrainer assignedTrainer) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
