package by.ksu.training.service;

import by.ksu.training.dao.AssignedComplexDao;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;

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
}