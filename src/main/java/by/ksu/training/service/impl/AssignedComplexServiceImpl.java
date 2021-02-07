package by.ksu.training.service.impl;

import by.ksu.training.dao.database.AssignedComplexDao;
import by.ksu.training.dao.database.ComplexDao;
import by.ksu.training.dao.database.UserDao;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.ServiceImpl;
import by.ksu.training.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class AssignedComplexServiceImpl extends ServiceImpl implements AssignedComplexService {
    @Override
    public AssignedComplex findById(Integer id) throws PersistentException {
        AssignedComplexDao dao = transaction.createDao(AssignedComplexDao.class);
        AssignedComplex assignedComplex = dao.read(id);
        if (assignedComplex != null) {
            readComplexTitle(List.of(assignedComplex));
            if (assignedComplex.getVisitor() != null) {
                UserDao userDao = transaction.createDao(UserDao.class);
                userDao.readLogin(List.of(assignedComplex.getVisitor()));
            }
        }
        return assignedComplex;
    }

    @Override
    public AssignedComplex find(AssignedComplex assignedComplex) throws PersistentException {
        AssignedComplexDao dao = transaction.createDao(AssignedComplexDao.class);
        return dao.read(assignedComplex);
    }

    @Override
    public void save(AssignedComplex assignedComplex) throws PersistentException {
        AssignedComplexDao dao = transaction.createDao(AssignedComplexDao.class);
        if (assignedComplex.getId() != null) {
            dao.update(assignedComplex);
        } else {
            assignedComplex.setId(dao.create(assignedComplex));
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        AssignedComplexDao dao = transaction.createDao(AssignedComplexDao.class);
        dao.delete(id);
    }

    @Override
    public List<AssignedComplex> findUnexecutedByUser(User user) throws PersistentException {
        AssignedComplexDao dao = transaction.createDao(AssignedComplexDao.class);
        List<AssignedComplex> assignedComplexes = dao.readUnexecutedByUser(user);
        readComplexTitle(assignedComplexes);
        return assignedComplexes;
    }

    @Override
    public List<AssignedComplex> findExecutedByUserForPeriod(User user, int periodDays) throws PersistentException {
        AssignedComplexDao dao = transaction.createDao(AssignedComplexDao.class);
        List<AssignedComplex> assignedComplexes = dao.readExecutedByUserForPeriod(user, periodDays);
        readComplexTitle(assignedComplexes);
        return assignedComplexes;
    }

    @Override
    public List<AssignedComplex> findExecutedForPeriod(int period) throws PersistentException {
        AssignedComplexDao dao = transaction.createDao(AssignedComplexDao.class);
        List<AssignedComplex> list = dao.readExecutedForPeriod(period);

        UserDao userDao = transaction.createDao(UserDao.class);
        List<User> users = list.stream()
                .map(AssignedComplex::getVisitor)
                .distinct()
                .collect(Collectors.toList());
        userDao.readLogin(users);
        readComplexTitle(list);
        return list;
    }

    private void readComplexTitle(List<AssignedComplex> assignedComplexes) throws PersistentException {
        List<Complex> complexes = assignedComplexes.stream()
                .map(AssignedComplex::getComplex)
                .distinct()
                .collect(Collectors.toList());
        ComplexDao complexDao = transaction.createDao(ComplexDao.class);
        complexDao.readTitle(complexes);
    }
}
