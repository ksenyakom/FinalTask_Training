package by.ksu.training.dao;

import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface ExerciseDao extends Dao<Exercise>{
    List<Exercise> read() throws PersistentException;

    void readByExercise(List<Exercise> exercises) throws PersistentException;
}
