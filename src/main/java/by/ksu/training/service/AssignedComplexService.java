package by.ksu.training.service;

import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Visitor;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.EntityService;

import java.util.List;

public interface AssignedComplexService extends EntityService {

    AssignedComplex findByIdentity(Integer id) throws PersistentException;

    void save(AssignedComplex assignedComplex) throws PersistentException;

    void delete(Integer id) throws PersistentException;

    List<AssignedComplex> findUnexecutedByVisitor(Visitor visitor) throws PersistentException;
    List<AssignedComplex> findExecutedByVisitorForPeriod(Visitor visitor, int periodDays) throws PersistentException;
    List<AssignedComplex> findExecutedForPeriod(int period) throws PersistentException;



}
