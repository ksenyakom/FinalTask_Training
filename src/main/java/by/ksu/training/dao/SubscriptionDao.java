package by.ksu.training.dao;

import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface SubscriptionDao extends Dao<Subscription> {
    List<Subscription> read() throws PersistentException;
}
