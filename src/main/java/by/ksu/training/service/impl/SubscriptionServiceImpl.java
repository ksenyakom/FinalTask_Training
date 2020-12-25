package by.ksu.training.service.impl;

import by.ksu.training.dao.SubscriptionDao;
import by.ksu.training.entity.Subscription;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ServiceImpl;

import java.util.List;

public class SubscriptionServiceImpl extends ServiceImpl implements SubscriptionService {
    @Override
    public List<Subscription> findAll() throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        return sDao.read();
    }

    @Override
    public Subscription findByIdentity(Integer id) throws PersistentException {
        SubscriptionDao sDao = transaction.createDao(SubscriptionDao.class);
        return sDao.read(id);
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
}
