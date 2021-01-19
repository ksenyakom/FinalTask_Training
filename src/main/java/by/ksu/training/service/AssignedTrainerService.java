package by.ksu.training.service;

import by.ksu.training.entity.AssignedTrainer;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.EntityService;

import java.util.List;

public interface AssignedTrainerService extends EntityService<AssignedTrainer> {
    List<AssignedTrainer> findAll() throws PersistentException;
}
