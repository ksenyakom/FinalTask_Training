package by.ksu.training.service;

import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface AssignedComplexService extends EntityService<AssignedComplex> {

    AssignedComplex find(AssignedComplex assignedComplex) throws PersistentException;

    List<AssignedComplex> findUnexecutedByUser(User user) throws PersistentException;

    List<AssignedComplex> findExecutedByUserForPeriod(User user, int periodDays) throws PersistentException;

    List<AssignedComplex> findExecutedForPeriod(int period) throws PersistentException;


}
