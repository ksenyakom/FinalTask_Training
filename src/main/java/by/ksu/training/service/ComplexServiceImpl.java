package by.ksu.training.service;

import by.ksu.training.dao.ComplexDao;
import by.ksu.training.entity.Complex;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public class ComplexServiceImpl extends ServiceImpl implements ComplexService {
    @Override
    public Complex findByIdentity(Integer id) throws PersistentException {
        ComplexDao complexDao = transaction.createDao(ComplexDao.class);
        return complexDao.read(id);
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
}