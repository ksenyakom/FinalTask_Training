package by.ksu.training.service.impl;

import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.EntityService;

import java.util.List;

public interface SubscriptionService extends EntityService {
    List<Subscription> findAll() throws PersistentException;

    Subscription findByIdentity(Integer id) throws PersistentException;

    void save(Subscription subscription) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
