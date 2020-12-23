package by.ksu.training.service.entity;

import by.ksu.training.entity.Visitor;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.EntityService;

import java.util.List;

public interface VisitorService extends EntityService {
    List<Visitor> findAll() throws PersistentException;

    Visitor findByIdentity(Integer id) throws PersistentException;

    void save(Visitor visitor) throws PersistentException;

    void update(Visitor visitor) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
