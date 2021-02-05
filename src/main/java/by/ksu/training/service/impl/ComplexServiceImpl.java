package by.ksu.training.service.impl;

import by.ksu.training.dao.database.AssignedTrainerDao;
import by.ksu.training.dao.database.ComplexDao;
import by.ksu.training.dao.database.ExerciseDao;
import by.ksu.training.dao.database.UserDao;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.Exercise;
import by.ksu.training.entity.Role;
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
        Complex complex = complexDao.read(id);
        if (complex != null ) {
            findExercises(complex);
        }
        return complex;
    }

    private void findExercises(Complex complex) throws PersistentException {
        ExerciseDao exerciseDao = transaction.createDao(ExerciseDao.class);
        List<Exercise> exercises = complex.getListOfUnits().stream()
                .map(Complex.ComplexUnit::getExercise)
                .distinct()
                .collect(Collectors.toList());
        exerciseDao.readByExercise(exercises);
    }

    @Override
    public boolean checkEditAllowed(User user, Complex complex) throws PersistentException {
        boolean allowed = false;
        if (complex != null) {
            // for admin
            if (user.getRole() == Role.ADMINISTRATOR && complex.getVisitorFor() == null) {
                allowed = true;
            } else if (user.getRole() == Role.TRAINER && complex.getVisitorFor() != null) {
                // for trainer
                AssignedTrainerDao assignedTrainerDao = transaction.createDao(AssignedTrainerDao.class);
                User trainerOfVisitor = assignedTrainerDao.readCurrentTrainerByVisitor(complex.getVisitorFor());

                if (trainerOfVisitor != null && user.getId().equals(trainerOfVisitor.getId())) {
                    allowed = true;
                }
            }
        }
        return allowed;
    }

    @Override
    public boolean checkTitleExist(String title) throws PersistentException {
        ComplexDao complexDao = transaction.createDao(ComplexDao.class);

        return complexDao.checkTitleExist(title);
    }

    @Override
    public List<Complex> findAllCommonComplexMetaData() throws PersistentException {
        ComplexDao complexDao = transaction.createDao(ComplexDao.class);
        List<Complex> complexes = complexDao.readAllCommonComplexMetaData();
        readTrainerLogin(complexes);
        return complexes;
    }

    /**
     * A method finds all complexes meta data in base.
     *
     * @return - list of complexes
     * @throws PersistentException - when exception occur in dao layout.
     */
    @Override
    public List<Complex> findAllMetaData() throws PersistentException {
        ComplexDao complexDao = transaction.createDao(ComplexDao.class);
        List<Complex> complexes = complexDao.readComplex();
        readTrainerLogin(complexes);
        readVisitorLogin(complexes);
        return complexes;
    }

    /**
     * A method finds all individual visitor complexes meta data.
     *
     * @param visitors - list of users, for who we find complexes
     * @return - list of  complexes
     * @throws PersistentException - when exception occur in dao layout.
     */
    @Override
    public List<Complex> findIndividualComplexMetaDataByUsers(List<User> visitors) throws PersistentException {
        ComplexDao complexDao = transaction.createDao(ComplexDao.class);
        List<Complex> complexes = complexDao.readIndividualComplexMetaDataByVisitors(visitors);
        readTrainerLogin(complexes);
        readVisitorLogin(complexes);
        return complexes;
    }

    /**
     * A method finds all complexes available for visitor: all common complexes and special developed for him.
     *
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
        } else {
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
                .filter(complex -> complex.getTrainerDeveloped() != null)
                .map(Complex::getTrainerDeveloped)
                .distinct()
                .collect(Collectors.toList());
        UserDao userDao = transaction.createDao(UserDao.class);
        userDao.readLogin(trainers);
    }

    private void readVisitorLogin(List<Complex> complexes) throws PersistentException {
        List<User> visitors = complexes.stream()
                .filter(complex -> complex.getVisitorFor() != null)
                .map(Complex::getVisitorFor)
                .distinct()
                .collect(Collectors.toList());
        UserDao userDao = transaction.createDao(UserDao.class);
        userDao.readLogin(visitors);
    }
}
