package by.ksu.training.dao;

import by.ksu.training.dao.database.impl.*;
import by.ksu.training.exception.PersistentException;

import java.util.concurrent.locks.ReentrantLock;

/**
 * A factory for dao classes. Singleton.
 *
 * @Author Kseniya Oznobishina
 * @Date 30.01.2021
 */
public class DaoFactoryImpl implements DaoFactory {

    private static DaoFactoryImpl instance;
    private static final ReentrantLock lock = new ReentrantLock();

    private DaoFactoryImpl() {
    }

    public static DaoFactoryImpl getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new DaoFactoryImpl();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }

    @Override
    public <T extends BaseDaoImpl> T getDao(Class<T> clazz) throws PersistentException {
        BaseDaoImpl baseDao = null;
        if (clazz == AssignedComplexDaoImpl.class) {
            baseDao = new AssignedComplexDaoImpl();
        } else if (clazz == AssignedTrainerDaoImpl.class) {
            baseDao = new AssignedTrainerDaoImpl();
        } else if (clazz == ComplexDaoImpl.class) {
            baseDao = new ComplexDaoImpl();
        } else if (clazz == ExerciseDaoImpl.class) {
            baseDao = new ExerciseDaoImpl();
        } else if (clazz == SubscriptionDaoImpl.class) {
            baseDao = new SubscriptionDaoImpl();
        } else if (clazz == PersonDaoImpl.class) {
            baseDao = new PersonDaoImpl();
        } else if (clazz == UserDaoImpl.class) {
            baseDao = new UserDaoImpl();
        }
        if (baseDao != null) {
            return (T) baseDao;
        } else {
            throw new PersistentException("No such dao: " + clazz);
        }
    }
}
