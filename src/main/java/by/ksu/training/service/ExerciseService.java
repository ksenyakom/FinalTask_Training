package by.ksu.training.service;

import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.EntityService;

import java.util.List;

public interface ExerciseService extends EntityService {
    List<Exercise> findAll() throws PersistentException;

    Exercise findByIdentity(Integer id) throws  PersistentException;

    void save(Exercise exercise) throws  PersistentException;

    void delete(Integer id) throws PersistentException;
}
