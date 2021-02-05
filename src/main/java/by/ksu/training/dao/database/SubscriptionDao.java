package by.ksu.training.dao.database;

import by.ksu.training.dao.Dao;
import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface SubscriptionDao extends Dao<Subscription> {
    List<Subscription> read() throws PersistentException;

    List<Subscription> readAllActive() throws PersistentException;

    List<Subscription> readByUser(User user) throws PersistentException;

    Subscription readActiveByUser(User user) throws PersistentException;
}
