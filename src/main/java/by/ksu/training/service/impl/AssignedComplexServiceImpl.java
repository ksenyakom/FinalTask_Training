package by.ksu.training.service.impl;

import by.ksu.training.dao.AssignedComplexDao;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.User;
import by.ksu.training.entity.Visitor;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class AssignedComplexServiceImpl extends ServiceImpl implements AssignedComplexService {
    @Override
    public AssignedComplex findByIdentity(Integer id) throws PersistentException {
        AssignedComplexDao acDao = transaction.createDao(AssignedComplexDao.class);
        return acDao.read(id);
    }
    @Override
    public AssignedComplex find(AssignedComplex assignedComplex) throws PersistentException {
        AssignedComplexDao acDao = transaction.createDao(AssignedComplexDao.class);
        return acDao.read(assignedComplex);
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
    public List<AssignedComplex> findUnexecutedByUser(User user) throws PersistentException {
        AssignedComplexDao acDao = transaction.createDao(AssignedComplexDao.class);
        return acDao.readUnexecutedByUser(user);
    }

    @Override
    public List<AssignedComplex> findExecutedByUserForPeriod(User user, int periodDays) throws PersistentException {
        AssignedComplexDao acDao = transaction.createDao(AssignedComplexDao.class);
        return acDao.readExecutedByUserForPeriod(user,periodDays);
    }

    @Override
    public List<AssignedComplex> findExecutedForPeriod(int period) throws PersistentException {
        AssignedComplexDao acDao = transaction.createDao(AssignedComplexDao.class);
        return acDao.readExecutedForPeriod(period);
    }
}
