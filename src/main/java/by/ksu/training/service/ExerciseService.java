package by.ksu.training.service;

import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.EntityService;

import java.util.List;

public interface ExerciseService extends EntityService<Exercise> {
    List<Exercise> findAll() throws PersistentException;
}
