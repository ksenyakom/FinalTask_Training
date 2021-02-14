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
        return dao.readCount();
    }

    @Override
    public List<Exercise> find(final int currentPage, final int recordsPerPage) throws PersistentException {
        ExerciseDao dao = transaction.createDao(ExerciseDao.class);
        return dao.read(currentPage, recordsPerPage);
    }

    @Override
    public void find(final List<Exercise> exercises) throws PersistentException {
        ExerciseDao dao = transaction.createDao(ExerciseDao.class);
        dao.readByExercise(exercises);
    }

    /**
     * Finds Exercise by id.
     *
     * @param id - identity of record to find.
     * @return found Exercise object or null, if record with this id not found.
     * @throws PersistentException - if exception occur in dao layer.
     */
    @Override
    public Exercise findById(final Integer id) throws PersistentException {
        ExerciseDao dao = transaction.createDao(ExerciseDao.class);
        return dao.read(id);
    }

    @Override
    public void save(final Exercise exercise) throws PersistentException {
        try {
            ExerciseDao dao = transaction.createDao(ExerciseDao.class);
            if (exercise.getId() != null) {
                dao.update(exercise);
            } else {
                exercise.setId(dao.create(exercise));
            }
            transaction.commit();
        } catch (PersistentException e) {
            transaction.rollback();
            throw new PersistentException("Exercise can not be updated or saved", e);
        }
    }

    @Override
    public void delete(final Integer id) throws PersistentException {
        try {
            ExerciseDao dao = transaction.createDao(ExerciseDao.class);
            dao.delete(id);
            transaction.commit();
        } catch (PersistentException e) {
            transaction.rollback();
            throw new PersistentException("Exercise can not be deleted", e);
        }
    }
}
