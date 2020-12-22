package by.ksu.training.service;

import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.exception.PersistentException;

public interface AssignedComplexService {

    AssignedComplex findByIdentity(Integer id) throws PersistentException;

    void save(AssignedComplex assignedComplex) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
