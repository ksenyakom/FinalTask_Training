package by.ksu.training.service;

import by.ksu.training.entity.Visitor;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface VisitorService {
    List<Visitor> findAll() throws PersistentException;

    Visitor findByIdentity(Integer id) throws PersistentException;

    void save(Visitor visitor) throws PersistentException;

    void update(Visitor visitor) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
