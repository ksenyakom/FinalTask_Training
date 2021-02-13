package by.ksu.training.service;

import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionService extends EntityService<Subscription> {
    List<Subscription> findAll() throws PersistentException;

    List<Subscription> findAllActive() throws PersistentException;

    List<Subscription> findByUser(User user) throws PersistentException;

    Subscription findActiveByUser(User user) throws PersistentException;

    List<Subscription> findFrom(LocalDate from) throws PersistentException;
    List<Subscription> findTo(LocalDate to) throws PersistentException;
    List<Subscription> findFromTo(LocalDate from,LocalDate to) throws PersistentException;
    List<Subscription> findByUserLogin(String userLogin) throws PersistentException;
    List<Subscription> findFromLogin(LocalDate from,String userLogin) throws PersistentException;
    List<Subscription> findToLogin(LocalDate to,String userLogin) throws PersistentException;
    List<Subscription> findFromToLogin(LocalDate from,LocalDate to,String userLogin) throws PersistentException;
}
