package by.ksu.training.service.impl;

import by.ksu.training.dao.database.ExerciseDao;
import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ExerciseService;
import by.ksu.training.service.ServiceImpl;

import java.util.List;

public class ExerciseServiceImpl extends ServiceImpl implements ExerciseService {
    @Override
    public List<Exercise> findAll() throws PersistentException {
        ExerciseDao dao = transaction.createDao(ExerciseDao.class);
        return dao.read();
    }

    @Override
    public List<String> findExerciseTypes() throws PersistentException {
        ExerciseDao dao = transaction.createDao(ExerciseDao.class);
        return dao.readExerciseTypes();
    }

    @Override
    public int findTotalCount() throws PersistentException {
        ExerciseDao dao = transaction.createDao(ExerciseDao.class);
        return dao.readCount() ;
    }

    @Override
    public List<Exercise> find(int currentPage, int recordsPerPage) throws PersistentException {
        ExerciseDao dao = transaction.createDao(ExerciseDao.class);
        return dao.read(currentPage, recordsPerPage);
    }

    @Override
    public void find(List<Exercise> exercises) throws PersistentException {
        ExerciseDao dao = transaction.createDao(ExerciseDao.class);
        dao.readByExercise(exercises);
    }

    @Override
    public Exercise findById(Integer id) throws PersistentException {
        ExerciseDao dao = transaction.createDao(ExerciseDao.class);
        return dao.read(id);
    }

    @Override
    public void save(Exercise exercise) throws PersistentException {
        ExerciseDao dao = transaction.createDao(ExerciseDao.class);
        if (exercise.getId() != null) {
            dao.update(exercise);
        }else {
            exercise.setId(dao.create(exercise));
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        ExerciseDao dao = transaction.createDao(ExerciseDao.class);
        dao.delete(id);
    }
}
