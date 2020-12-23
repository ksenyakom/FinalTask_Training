package by.ksu.training.service.entity;

import by.ksu.training.entity.Complex;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.EntityService;

import java.util.List;

public interface ComplexService extends EntityService {

    Complex findByIdentity(Integer id) throws PersistentException;

    List<Complex> findAll() throws PersistentException;

    void save(Complex complex) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
