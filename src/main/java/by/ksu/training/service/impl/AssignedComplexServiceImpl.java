package by.ksu.training.service.impl;

import by.ksu.training.dao.database.AssignedComplexDao;
import by.ksu.training.dao.database.ComplexDao;
import by.ksu.training.dao.database.UserDao;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.AssignedComplexService;
import by.ksu.training.service.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;


public class AssignedComplexServiceImpl extends ServiceImpl implements AssignedComplexService {
    @Override
    public void save(final AssignedComplex assignedComplex) throws PersistentException {
        try {
            AssignedComplexDao dao = transaction.createDao(AssignedComplexDao.class);
            if (assignedComplex.getId() != null) {
                dao.update(assignedComplex);
            } else {
                assignedComplex.setId(dao.create(assignedComplex));
            }
            transaction.commit();
        } catch (PersistentException e) {
            transaction.rollback();
            throw new PersistentException("AssignedComplex can not be updated or saved", e);
        }
    }

    /**
     * Finds AssignedComplex by id.
     * @param id - identity of record to find.
     * @return found AssignedComplex or null, if record with this id not found.
     * @throws PersistentException - if exception occur in dao layer.
     */
    @Override
    public AssignedComplex findById(final Integer id) throws PersistentException {
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
    public AssignedComplex find(final AssignedComplex assignedComplex) throws PersistentException {
        AssignedComplexDao dao = transaction.createDao(AssignedComplexDao.class);
        return dao.read(assignedComplex);
    }

    /**
     * Find AssignedComplex which user did not execute yet.
     *
     * @param user - visitor, whose assignment should be found.
     * @return - list of AssignedComplex, never null.
     * @throws PersistentException - if exception occur in dao layer.
     */
    @Override
    public List<AssignedComplex> findUnexecutedByUser(final User user) throws PersistentException {
        AssignedComplexDao dao = transaction.createDao(AssignedComplexDao.class);
        List<AssignedComplex> assignedComplexes = dao.readUnexecutedByUser(user);
        readComplexTitle(assignedComplexes);
        return assignedComplexes;
    }

    /**
     * Find AssignedComplex which user  executed for period.
     *
     * @param user       - visitor, whose assignment should be found.
     * @param periodDays - period in days.
     * @return - list of executed AssignedComplex, never null.
     * @throws PersistentException - if exception occur in dao layer.
     */
    @Override
    public List<AssignedComplex> findExecutedByUserForPeriod(final User user, final int periodDays)
            throws PersistentException {
        AssignedComplexDao dao = transaction.createDao(AssignedComplexDao.class);
        List<AssignedComplex> assignedComplexes = dao.readExecutedByUserForPeriod(user, periodDays);
        readComplexTitle(assignedComplexes);
        return assignedComplexes;
    }

    /**
     * Find AssignedComplex executed for period by all visitors.
     *
     * @param period - period in days.
     * @return - list of executed AssignedComplex, never null.
     * @throws PersistentException - if exception occur in dao layer.
     */
    @Override
    public List<AssignedComplex> findExecutedForPeriod(final int period)
            throws PersistentException {
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

    private void readComplexTitle(final List<AssignedComplex> assignedComplexes) throws PersistentException {
        List<Complex> complexes = assignedComplexes.stream()
                .map(AssignedComplex::getComplex)
                .distinct()
                .collect(Collectors.toList());
        ComplexDao complexDao = transaction.createDao(ComplexDao.class);
        complexDao.readTitle(complexes);
    }

    @Override
    public void delete(final Integer id) throws PersistentException {
        try {
            AssignedComplexDao dao = transaction.createDao(AssignedComplexDao.class);
            dao.delete(id);
            transaction.commit();
        } catch (PersistentException e) {
            transaction.rollback();
            throw new PersistentException("AssignedComplex can not be deleted", e);
        }
    }
}
