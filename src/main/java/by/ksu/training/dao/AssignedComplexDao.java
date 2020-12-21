package by.ksu.training.dao;

import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Visitor;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface AssignedComplexDao extends Dao<AssignedComplex> {
    List<AssignedComplex> readUnexecutedByVisitor(Visitor visitor) throws PersistentException;
    List<AssignedComplex> readExecutedByVisitorForPeriod(Visitor visitor, int periodDays) throws PersistentException;
    List<AssignedComplex> readExecutedForPeriod(int period) throws PersistentException;
}
