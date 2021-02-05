package by.ksu.training.dao.database;

import by.ksu.training.dao.Dao;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface ComplexDao extends Dao<Complex> {
    List<Complex> read() throws PersistentException;

    Integer createComplex(Complex entity) throws PersistentException;

    void createListExercisesForComplex(Complex complex) throws PersistentException;

    Complex readComplex(Integer id) throws PersistentException;

    void readTitle(List<Complex> complexes) throws PersistentException;

    List<Complex> readAllCommonComplexMetaData() throws PersistentException;

    List<Complex> readComplexMetaDataByUser(User visitor) throws PersistentException;

    List<Complex> readIndividualComplexMetaDataByVisitors(List<User> visitors) throws PersistentException;

    List<Complex> readComplex() throws PersistentException;

    List<Complex.ComplexUnit> readListOfUnitsByComplexId(Integer complexId) throws PersistentException;

    void updateComplex(Complex entity) throws PersistentException;

    void updateListOfExercises(Complex complex) throws PersistentException;

    void deleteComplex(Integer id) throws PersistentException;

    void deleteListOfExercises(Integer id) throws PersistentException;

    boolean checkTitleExist(String title) throws PersistentException;
}
