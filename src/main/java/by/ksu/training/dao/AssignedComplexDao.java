package by.ksu.training.dao;

import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.User;
import by.ksu.training.entity.Visitor;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface AssignedComplexDao extends Dao<AssignedComplex> {
    List<AssignedComplex> readUnexecutedByUser(User user) throws PersistentException;
    AssignedComplex read(AssignedComplex assignedComplex) throws PersistentException;
    List<AssignedComplex> readExecutedByUserForPeriod(User user, int periodDays) throws PersistentException;
    List<AssignedComplex> readExecutedForPeriod(int period) throws PersistentException;
}
