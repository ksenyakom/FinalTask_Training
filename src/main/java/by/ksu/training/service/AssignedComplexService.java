package by.ksu.training.service;

import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.User;
import by.ksu.training.entity.Visitor;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.EntityService;

import java.util.List;

public interface AssignedComplexService extends EntityService {

    AssignedComplex findByIdentity(Integer id) throws PersistentException;
    AssignedComplex find(AssignedComplex assignedComplex) throws PersistentException;

    void save(AssignedComplex assignedComplex) throws PersistentException;

    void delete(Integer id) throws PersistentException;

    List<AssignedComplex> findUnexecutedByUser(User user) throws PersistentException;
    List<AssignedComplex> findExecutedByUserForPeriod(User user, int periodDays) throws PersistentException;
    List<AssignedComplex> findExecutedForPeriod(int period) throws PersistentException;



}
