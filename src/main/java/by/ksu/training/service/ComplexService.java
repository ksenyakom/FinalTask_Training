package by.ksu.training.service;

import by.ksu.training.entity.Complex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.EntityService;

import java.util.List;

public interface ComplexService extends EntityService<Complex> {

    List<Complex> findAll() throws PersistentException;

    void findTitle(List<Complex> complexes) throws PersistentException;

    List<Complex> findAllCommonComplexMetaData() throws PersistentException;

    List<Complex> findComplexesMetaDataByUser(User visitor)  throws PersistentException;
}
