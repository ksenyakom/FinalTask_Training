package by.ksu.training.service;

import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.EntityService;

import java.util.List;

public interface SubscriptionService extends EntityService<Subscription> {
    List<Subscription> findAll() throws PersistentException;

    List<Subscription> findAllActive() throws PersistentException;

    List<Subscription> findByUser(User user) throws PersistentException;

    Subscription findActiveByUser(User user) throws PersistentException;
}
