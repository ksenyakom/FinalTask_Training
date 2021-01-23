package by.ksu.training.service.impl;

import by.ksu.training.dao.ComplexDao;
import by.ksu.training.dao.UserDao;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class ComplexServiceImpl extends ServiceImpl implements ComplexService {
    @Override
    public Complex findById(Integer id) throws PersistentException {
        ComplexDao complexDao = transaction.createDao(ComplexDao.class);
        return complexDao.read(id);
    }

    @Override
    public List<Complex> findAllCommonComplexMetaData() throws PersistentException {
        ComplexDao complexDao = transaction.createDao(ComplexDao.class);
        List<Complex> complexes = complexDao.readAllCommonComplexMetaData();
        readTrainerLogin(complexes);
        return complexes;
    }

    /**
     * A method finds all complexes available for visitor: all common complexes and special developed for him.
     * @param visitor - user, for who we find complexes
     * @return - list of available complexes
     * @throws PersistentException - when exception occur in dao layout.
     */
    @Override
    public List<Complex> findComplexesMetaDataByUser(User visitor) throws PersistentException {
        ComplexDao complexDao = transaction.createDao(ComplexDao.class);
        return complexDao.readComplexMetaDataByUser(visitor);
    }

    @Override
    public void findTitle(List<Complex> complexes) throws PersistentException {
        ComplexDao complexDao = transaction.createDao(ComplexDao.class);
        complexDao.readTitle(complexes);
    }

    @Override
    public List<Complex> findAll() throws PersistentException {
        ComplexDao complexDao = transaction.createDao(ComplexDao.class);
        return complexDao.read();
    }

    @Override
    public void save(Complex complex) throws PersistentException {
        ComplexDao complexDao = transaction.createDao(ComplexDao.class);

        if (complex.getId() != null) {
            complexDao.update(complex);
        }
        else {
            complex.setId(complexDao.create(complex));
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        ComplexDao complexDao = transaction.createDao(ComplexDao.class);
        complexDao.delete(id);
    }

    private void readTrainerLogin(List<Complex> complexes) throws PersistentException {
        List<User> trainers = complexes.stream()
                .map(Complex::getTrainerDeveloped)
                .distinct()
                .collect(Collectors.toList());
        UserDao userDao = transaction.createDao(UserDao.class);
        userDao.readLogin(trainers);
    }
}
