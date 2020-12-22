package by.ksu.training.service;

import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.entity.Trainer;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface AssignedTrainerService {
    List<AssignedTrainer> findAll() throws PersistentException;

    AssignedTrainer findByIdentity(Integer id) throws PersistentException;

    void save(AssignedTrainer assignedTrainer) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
