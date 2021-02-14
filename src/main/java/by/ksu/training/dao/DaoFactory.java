package by.ksu.training.dao;

import by.ksu.training.exception.PersistentException;

public interface DaoFactory {
    <T extends BaseDaoImpl> T getDao(Class<T> key) throws PersistentException;
}
