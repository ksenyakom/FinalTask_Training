package by.ksu.training.service;

import by.ksu.training.entity.Complex;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface ComplexService {

    Complex findByIdentity(Integer id) throws PersistentException;

    List<Complex> findAll() throws PersistentException;

    void save(Complex complex) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
