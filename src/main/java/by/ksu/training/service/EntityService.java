package by.ksu.training.service;

import by.ksu.training.entity.Entity;
import by.ksu.training.exception.PersistentException;

public interface EntityService<T extends Entity> {
    T findById(Integer id) throws PersistentException;

    void save(T entity) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
