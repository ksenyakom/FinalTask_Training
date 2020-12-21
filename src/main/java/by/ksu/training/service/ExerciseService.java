package by.ksu.training.service;

import by.ksu.training.entity.Exercise;
import by.ksu.training.entity.Trainer;

import java.util.List;

public interface ExerciseService {
    List<Exercise> findAll() throws ServiceException;

    Exercise findByIdentity(Integer identity) throws ServiceException;

    void save(Exercise user) throws ServiceException;
    void update(Exercise user) throws ServiceException;

    void delete(Integer identity) throws ServiceException;
}
