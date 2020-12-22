package by.ksu.training.service;

import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.Trainer;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface SubscriptionService {
    List<Subscription> findAll() throws PersistentException;

    Subscription findByIdentity(Integer id) throws PersistentException;

    void save(Subscription subscription) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
