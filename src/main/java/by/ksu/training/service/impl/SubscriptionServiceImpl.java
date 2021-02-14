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
    public List<Subscription> findFrom(final LocalDate from) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        List<Subscription> subscriptionList = sDao.readFrom(from);
        readUserLogin(subscriptionList);
        return subscriptionList;
    }

    @Override
    public List<Subscription> findTo(final LocalDate to) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        List<Subscription> subscriptionList = sDao.readTo(to);
        readUserLogin(subscriptionList);
        return subscriptionList;
    }

    @Override
    public List<Subscription> findFromTo(final LocalDate from, final LocalDate to) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        List<Subscription> subscriptionList = sDao.readFromTo(from, to);
        readUserLogin(subscriptionList);
        return subscriptionList;
    }

    @Override
    public List<Subscription> findByUserLogin(final String userLogin) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        UserDao userDao = transaction.createDao(UserDao.class);

        List<User> users = userDao.readByLoginPart(userLogin);
        List<Subscription> subscriptionList = new ArrayList<>();
        for (User user : users) {
            subscriptionList.addAll(sDao.readByUser(user));
        }
        return subscriptionList;
    }

    @Override
    public List<Subscription> findFromLogin(final LocalDate from, final String userLogin) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        UserDao userDao = transaction.createDao(UserDao.class);

        List<User> users = userDao.readByLoginPart(userLogin);
        List<Subscription> subscriptionList = new ArrayList<>();
        for (User user : users) {
            subscriptionList.addAll(sDao.readFromLogin(from, user));
        }
        return subscriptionList;
    }

    @Override
    public List<Subscription> findToLogin(final LocalDate to, final String userLogin) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        UserDao userDao = transaction.createDao(UserDao.class);

        List<User> users = userDao.readByLoginPart(userLogin);
        List<Subscription> subscriptionList = new ArrayList<>();
        for (User user : users) {
            subscriptionList.addAll(sDao.readToLogin(to, user));
        }
        return subscriptionList;
    }

    @Override
    public List<Subscription> findFromToLogin(final LocalDate from, final LocalDate to, final String userLogin) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        UserDao userDao = transaction.createDao(UserDao.class);

        List<User> users = userDao.readByLoginPart(userLogin);
        List<Subscription> subscriptionList = new ArrayList<>();
        for (User user : users) {
            subscriptionList.addAll(sDao.readFromToLogin(from, to, user));
        }
        return subscriptionList;
    }

    /**
     * Finds Subscription by id.
     *
     * @param id - identity of record to find.
     * @return found Subscription object or null, if record with this id not found.
     * @throws PersistentException - if exception occur in dao layer.
     */
    @Override
    public Subscription findById(final Integer id) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        Subscription subscription = sDao.read(id);
        if (subscription != null) {
            List<Subscription> subscriptionList = List.of(subscription);
            readUserLogin(subscriptionList);
        }
        return subscription;
    }

    @Override
    public List<Subscription> findByUser(final User user) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        return sDao.readByUser(user);
    }

    @Override
    public Subscription findActiveByUser(final User user) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        return sDao.readActiveByUser(user);
    }

    @Override
    public List<Subscription> findAllActive() throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        List<Subscription> subscriptionList = sDao.readAllActive();
        readUserLogin(subscriptionList);
        return subscriptionList;
    }

    @Override
    public void save(final Subscription subscription) throws PersistentException {
        try {
            SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
            if (subscription.getId() != null) {
                sDao.update(subscription);
            } else {
                subscription.setId(sDao.create(subscription));
            }
            transaction.commit();
        } catch (PersistentException e) {
            transaction.rollback();
            throw new PersistentException("Subscription can not be updated or saved", e);
        }
    }


    @Override
    public void delete(final Integer id) throws PersistentException {
        try {
            SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
            sDao.delete(id);
            transaction.commit();
        } catch (PersistentException e) {
            transaction.rollback();
            throw new PersistentException("Subscription can not be deleted", e);
        }
    }

    private void readUserLogin(final List<Subscription> subscriptions) throws PersistentException {
        List<User> users = subscriptions.stream()
                .map(Subscription::getVisitor)
                .distinct()
                .collect(Collectors.toList());
        UserDao userDao = transaction.createDao(UserDao.class);
        userDao.readLogin(users);
    }
}
