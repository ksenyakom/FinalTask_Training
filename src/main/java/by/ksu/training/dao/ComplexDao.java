package by.ksu.training.dao;

import by.ksu.training.entity.Complex;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface ComplexDao extends Dao<Complex>{
    List<Complex> read() throws PersistentException;

    void readTitle(List<Complex> complexes) throws PersistentException;
}
