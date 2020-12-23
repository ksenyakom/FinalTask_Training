package by.ksu.training.service.entity;

import by.ksu.training.dao.ExerciseDao;
import by.ksu.training.entity.Exercise;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ServiceImpl;

import java.util.List;

public class ExerciseServiceImpl extends ServiceImpl implements ExerciseService {
    @Override
    public List<Exercise> findAll() throws PersistentException {
        ExerciseDao dao = transaction.createDao(ExerciseDao.class);
        return dao.read();
    }

    @Override
    public Exercise findByIdentity(Integer id) throws PersistentException {
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
