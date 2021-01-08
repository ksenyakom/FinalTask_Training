package by.ksu.training.service.impl;

import by.ksu.training.dao.ComplexDao;
import by.ksu.training.entity.Complex;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ComplexService;
import by.ksu.training.service.ServiceImpl;

import java.util.List;

public class ComplexServiceImpl extends ServiceImpl implements ComplexService {
    @Override
    public Complex findByIdentity(Integer id) throws PersistentException {
        ComplexDao complexDao = transaction.createDao(ComplexDao.class);
        return complexDao.read(id);
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
}
