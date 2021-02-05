package by.ksu.training.dao.database;

import by.ksu.training.dao.Dao;
import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface ExerciseDao extends Dao<Exercise> {
    List<Exercise> read() throws PersistentException;
    List<Exercise> read(int currentPage, int recordsPerPage) throws PersistentException;

    void readByExercise(List<Exercise> exercises) throws PersistentException;

    List<String> readExerciseTypes() throws PersistentException;

    int readCount() throws PersistentException;
}
