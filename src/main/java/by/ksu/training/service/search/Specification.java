package by.ksu.training.service.search;

import by.ksu.training.entity.Entity;
import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.EntityService;
import by.ksu.training.service.Service;
import by.ksu.training.service.SubscriptionService;

import java.util.List;

public interface Specification<T extends Entity> {
    List<T> search(EntityService<T> service) throws PersistentException;
}


