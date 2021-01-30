package by.ksu.training.dao;

import by.ksu.training.dao.database.BaseDaoImpl;
import by.ksu.training.exception.PersistentException;

public interface DaoFactory {
    <Type extends BaseDaoImpl> Type getDao(Class<Type> key) throws PersistentException;
}
