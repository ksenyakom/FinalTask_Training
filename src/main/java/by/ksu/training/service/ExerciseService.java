package by.ksu.training.service;

import by.ksu.training.entity.Exercise;
import by.ksu.training.entity.Trainer;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface ExerciseService {
    List<Exercise> findAll() throws PersistentException;

    Exercise findByIdentity(Integer id) throws  PersistentException;

    void save(Exercise exercise) throws  PersistentException;

    void delete(Integer id) throws PersistentException;
}
