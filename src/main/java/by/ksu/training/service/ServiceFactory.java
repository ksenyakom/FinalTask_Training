package by.ksu.training.service;

import by.ksu.training.exception.PersistentException;
/**
 * A factory for entity service classes.
 *
 * @author Kseniya Oznobishina
 * @version 1.0, 23.12.2020
 */
public interface ServiceFactory {
    <T extends EntityService> T getService(Class<T> clazz) throws PersistentException;
    void close();
}
