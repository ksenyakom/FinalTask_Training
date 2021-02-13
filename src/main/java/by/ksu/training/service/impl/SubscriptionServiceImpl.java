package by.ksu.training.service.impl;

import by.ksu.training.dao.database.SubscriptionDao;
import by.ksu.training.dao.database.UserDao;
import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ServiceImpl;
import by.ksu.training.service.SubscriptionService;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public List<Subscription> findFrom(LocalDate from) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        List<Subscription> subscriptionList = sDao.readFrom(from);
        readUserLogin(subscriptionList);
        return subscriptionList;
    }

    @Override
    public List<Subscription> findTo(LocalDate to) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        List<Subscription> subscriptionList = sDao.readTo(to);
        readUserLogin(subscriptionList);
        return subscriptionList;
    }

    @Override
    public List<Subscription> findFromTo(LocalDate from, LocalDate to) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        List<Subscription> subscriptionList = sDao.readFromTo(from,to);
        readUserLogin(subscriptionList);
        return subscriptionList;
    }

    @Override
    public List<Subscription> findByUserLogin(String userLogin) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        UserDao userDao = transaction.createDao(UserDao.class);

        List<User> users = userDao.readByLoginPart(userLogin);
        List<Subscription> subscriptionList = new ArrayList<>();
        for(User user: users) {
            subscriptionList.addAll(sDao.readByUser(user));
        }
        return subscriptionList;
    }

    @Override
    public List<Subscription> findFromLogin(LocalDate from, String userLogin) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        UserDao userDao = transaction.createDao(UserDao.class);

        List<User> users = userDao.readByLoginPart(userLogin);
        List<Subscription> subscriptionList = new ArrayList<>();
        for(User user: users) {
            subscriptionList.addAll(sDao.readFromLogin(from,user));
        }
        return subscriptionList;
    }

    @Override
    public List<Subscription> findToLogin(LocalDate to, String userLogin) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        UserDao userDao = transaction.createDao(UserDao.class);

        List<User> users = userDao.readByLoginPart(userLogin);
        List<Subscription> subscriptionList = new ArrayList<>();
        for(User user: users) {
            subscriptionList.addAll(sDao.readToLogin(to, user));
        }
        return subscriptionList;
    }

    @Override
    public List<Subscription> findFromToLogin(LocalDate from, LocalDate to, String userLogin) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        UserDao userDao = transaction.createDao(UserDao.class);

        List<User> users = userDao.readByLoginPart(userLogin);
        List<Subscription> subscriptionList = new ArrayList<>();
        for(User user: users) {
            subscriptionList.addAll(sDao.readFromToLogin(from, to,  user));
        }
        return subscriptionList;
    }

    @Override
    public Subscription findById(Integer id) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        Subscription subscription = sDao.read(id);
        if (subscription != null) {
            List<Subscription> subscriptionList = List.of(subscription);
            readUserLogin(subscriptionList);
        }
        return subscription;
    }

    @Override
    public List<Subscription> findByUser(User user) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        List<Subscription> subscriptionList = sDao.readByUser(user);
        return subscriptionList;
    }

    @Override
    public Subscription findActiveByUser(User user) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        Subscription subscription = sDao.readActiveByUser(user);
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
