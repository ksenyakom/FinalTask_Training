package by.ksu.training.service.impl;

import by.ksu.training.dao.SubscriptionDao;
import by.ksu.training.dao.UserDao;
import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ServiceImpl;
import by.ksu.training.service.SubscriptionService;

import java.util.List;
import java.util.stream.Collectors;

public class SubscriptionServiceImpl extends ServiceImpl implements SubscriptionService {
    @Override
    public List<Subscription> findAll() throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        List<Subscription> subscriptionList = sDao.read();
        readUserLogin(subscriptionList);
        return subscriptionList;
    }

    @Override
    public Subscription findByIdentity(Integer id) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        Subscription subscription = sDao.read(id);
        if (subscription != null) {
            List<Subscription> subscriptionList = List.of(subscription);
            readUserLogin(subscriptionList);
        }
        return subscription;
    }

    @Override
    public List<Subscription> findAllActive() throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        List<Subscription> subscriptionList = sDao.readAllActive();
        readUserLogin(subscriptionList);
        return subscriptionList;
    }

    @Override
    public void save(Subscription subscription) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        if (subscription.getId() != null) {
            sDao.update(subscription);
        } else {
            subscription.setId(sDao.create(subscription));
        }
    }


    @Override
    public void delete(Integer id) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        sDao.delete(id);
    }

    private void readUserLogin(List<Subscription> subscriptions) throws PersistentException {
        List<User> users = subscriptions.stream()
                .map(subscription -> subscription.getVisitor())
                .distinct()
                .collect(Collectors.toList());
        UserDao userDao = transaction.createDao(UserDao.class);
        userDao.readLogin(users);
    }
}
