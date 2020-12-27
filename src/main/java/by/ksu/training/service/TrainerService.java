package by.ksu.training.service;

import by.ksu.training.entity.Trainer;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.EntityService;

import java.util.List;

public interface TrainerService extends EntityService {
    List<Trainer> findAll() throws PersistentException;

    Trainer findByIdentity(Integer id) throws PersistentException;

    void save(Trainer trainer) throws PersistentException;

    void update(Trainer trainer) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
