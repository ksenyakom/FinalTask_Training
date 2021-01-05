package by.ksu.training.service.impl;

import by.ksu.training.dao.AssignedComplexDao;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.User;
import by.ksu.training.entity.Visitor;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.ServiceImpl;

import java.util.List;

public class AssignedComplexServiceImpl extends ServiceImpl implements AssignedComplexService {
    @Override
    public AssignedComplex findByIdentity(Integer id) throws PersistentException {
        AssignedComplexDao acDao = transaction.createDao(AssignedComplexDao.class);
        return acDao.read(id);
    }

    @Override
    public void save(AssignedComplex assignedComplex) throws PersistentException {
        AssignedComplexDao acDao = transaction.createDao(AssignedComplexDao.class);

        if (assignedComplex.getId() != null) {
            acDao.update(assignedComplex);
        } else {
            assignedComplex.setId(acDao.create(assignedComplex));
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        AssignedComplexDao acDao = transaction.createDao(AssignedComplexDao.class);
        acDao.delete(id);
    }

    @Override
    public List<AssignedComplex> findUnexecutedByVisitor(Visitor visitor) throws PersistentException {
        AssignedComplexDao acDao = transaction.createDao(AssignedComplexDao.class);
        return acDao.readUnexecutedByVisitor(visitor);
    }

    @Override
    public List<AssignedComplex> findExecutedByVisitorForPeriod(Visitor visitor, int periodDays) throws PersistentException {
        AssignedComplexDao acDao = transaction.createDao(AssignedComplexDao.class);
        return acDao.readExecutedByVisitorForPeriod(visitor,periodDays);
    }

    @Override
    public List<AssignedComplex> findExecutedForPeriod(int period) throws PersistentException {
        AssignedComplexDao acDao = transaction.createDao(AssignedComplexDao.class);
        List<AssignedComplex> list = acDao.readExecutedForPeriod(period);
        List<User> users = list.stream().m
        return
    }
}
