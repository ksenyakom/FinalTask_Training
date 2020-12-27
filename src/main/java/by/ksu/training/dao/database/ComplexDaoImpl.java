package by.ksu.training.dao.database;

import by.ksu.training.dao.ComplexDao;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.Exercise;
import by.ksu.training.entity.Trainer;
import by.ksu.training.entity.Visitor;
import by.ksu.training.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplexDaoImpl extends BaseDaoImpl implements ComplexDao {
    private static Logger logger = LogManager.getLogger(ComplexDaoImpl.class);

    private static final String CREATE = "INSERT INTO `complex`(`title`,`trainer_id`,`visitor_id`,`rating`) VALUES (?,?,?,?)";
    private static final String READ_BY_ID = "SELECT * FROM `complex` WHERE `id` = ? ";
    private static final String READ_ALL = "SELECT * FROM `complex` ORDER BY `id`";
    private static final String UPDATE_COMPLEX = "UPDATE `complex` SET `title`=?,`trainer_id`=?,`visitor_id`=?,`rating`=? WHERE `id` = ?";
    private static final String DELETE = "DELETE FROM `complex` WHERE `id` = ?";

    private static final String CREATE_EXERCISE_IN_COMPLEX =
            "INSERT INTO `exercise_in_complex` (`complex_id`,`serial_number`,`exercise_id`, `repeat`, `group`) VALUES (?,?,?,?,?)";
    private static final String READ_LIST_OF_EXERCISES_BY_COMPLEX_ID =
            "SELECT * FROM `exercise_in_complex` where `complex_id` = ? ORDER BY `serial_number`";
    private static final String UPDATE_EXERCISE_IN_COMPLEX =
            "REPLACE INTO `exercise_in_complex` SET `complex_id` = ?,`serial_number` = ?,`exercise_id` = ?, `repeat` = ?, `group` = ?";
    //    "INSERT INTO `exercise_in_complex` (`complex_id`,`serial_number`,`exercise_id`, `repeat`, `group`) VALUES (?,?,?,?,?) "
    //           + "ON DUPLICATE KEY UPDATE `exercise_id` = VALUES(`exercise_id`), `repeat` = VALUES (`repeat`),  `group` = VALUES( `group`) ";
    //TODO alias - workbanch ругается
    private static final String UPDATE_LIST_OF_EXERCISES_DELETE_REMAIN = "DELETE FROM `exercise_in_complex` WHERE `complex_id` = ? AND `serial_number` > ?";
    private static final String DELETE_LIST_OF_EXERCISES = "DELETE FROM `exercise_in_complex`  WHERE `complex_id` = ?";

    @Override
    public Integer create(Complex entity) throws PersistentException {
        int id = createComplex(entity);
        entity.setId(id);
        createListExercisesForComplex(entity);
        return id;
    }

    @Override
    public Complex read(Integer id) throws PersistentException {
        Complex complex = readComplex(id);
        if (complex != null) {
            complex.setListOfComplexUnit(readListOfUnitsByComplexId(id));
        }
        return complex;
    }


    @Override
    public List<Complex> read() throws PersistentException {
        List<Complex> list = readComplex();
        for (int i = 0; i < list.size() ; i++) {
            list.get(i).setListOfComplexUnit(readListOfUnitsByComplexId(i));
        }
        return list;
    }

    @Override
    public void update(Complex entity) throws PersistentException {
        updateComplex(entity);
        updateListOfExercises(entity);
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        deleteListOfExercises(id);
        deleteComplex(id);
    }

    public Integer createComplex(Complex entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getTitle());
            statement.setInt(2, entity.getTrainerDeveloped().getId());
            if (entity.getVisitorFor() == null) {
                statement.setNull(3, Types.INTEGER);
            } else {
                statement.setInt(3, entity.getVisitorFor().getId());
            }
            statement.setFloat(4, entity.getRating());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            logger.debug("inserted complex {}", entity);
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                logger.debug("id = {}", id);
                return id;
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `readers`");
                throw new PersistentException();
            }
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    public void createListExercisesForComplex(Complex complex) throws PersistentException {
        Complex.ComplexUnit unit = null;
        for (int i = 0; i < complex.sizeOfComplexUnitList(); i++) {
            try (PreparedStatement statement = connection.prepareStatement(CREATE_EXERCISE_IN_COMPLEX)) {
                unit = complex.getComplexUnit(i);
                statement.setInt(1, complex.getId());
                statement.setInt(2, i);
                statement.setInt(3, unit.getExercise().getId());
                statement.setInt(4, unit.getRepeat());
                statement.setInt(5, unit.getGroup());
                statement.executeUpdate();
                logger.debug("inserted complex unit {}", unit);
            } catch (SQLException e) {
                throw new PersistentException(e);
            }
        }
    }

    public Complex readComplex(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Complex complex = null;

            if (resultSet.next()) {
                complex = new Complex();
                complex.setId(id);
                complex.setTitle(resultSet.getString("title"));
                Trainer trainer = new Trainer();
                trainer.setId(resultSet.getInt("trainer_id"));
                complex.setTrainerDeveloped(trainer);
                int visitorId = resultSet.getInt("visitor_id");
                Visitor visitor= null;
                if (!resultSet.wasNull()) {
                    visitor = new Visitor();
                    visitor.setId(visitorId);
                }
                complex.setVisitorFor(visitor);
                complex.setRating(resultSet.getFloat("rating"));
            }
            return complex;

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    public List<Complex> readComplex() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Complex> list = new ArrayList<>();
            Complex complex = null;

            while (resultSet.next()) {
                complex = new Complex();
                complex.setId(resultSet.getInt("id"));
                complex.setTitle(resultSet.getString("title"));
                Trainer trainer = new Trainer();
                trainer.setId(resultSet.getInt("trainer_id"));
                complex.setTrainerDeveloped(trainer);
                int visitorId = resultSet.getInt("visitor_id");
                Visitor visitor= null;
                if (!resultSet.wasNull()) {
                    visitor = new Visitor();
                    visitor.setId(visitorId);
                }
                complex.setVisitorFor(visitor);
                complex.setRating(resultSet.getFloat("rating"));
                list.add(complex);
            }
            return list;

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    public List<Complex.ComplexUnit> readListOfUnitsByComplexId(Integer complexId) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_LIST_OF_EXERCISES_BY_COMPLEX_ID)) {
            statement.setInt(1, complexId);
            ResultSet resultSet = statement.executeQuery();
            List<Complex.ComplexUnit> list = new ArrayList<>();
            Complex.ComplexUnit unit = null;

            while (resultSet.next()) {
                unit = new Complex.ComplexUnit();
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("exercise_id"));
                unit.setExercise(exercise);
                unit.setGroup(resultSet.getInt("group"));
                unit.setRepeat(resultSet.getInt("repeat"));
                list.add(unit);
            }
            return list; //TODO возвращает пустой лист а не null??
        } catch (SQLException e) {
            throw new PersistentException(e);
        }

    }

    public void updateComplex(Complex entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_COMPLEX)) {
            statement.setString(1, entity.getTitle());
            statement.setInt(2, entity.getTrainerDeveloped().getId());
            if (entity.getVisitorFor() == null) {
                statement.setNull(3, Types.INTEGER);
            } else {
                statement.setInt(3, entity.getVisitorFor().getId());
            }
            statement.setFloat(4, entity.getRating());
            statement.setInt(5, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    public void updateListOfExercises(Complex complex) throws PersistentException {
        Complex.ComplexUnit unit = null;
        for (int i = 0; i < complex.sizeOfComplexUnitList(); i++) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_EXERCISE_IN_COMPLEX)) {
                unit = complex.getComplexUnit(i);
                statement.setInt(1, complex.getId());
                statement.setInt(2, i);
                statement.setInt(3, unit.getExercise().getId());
                statement.setInt(4, unit.getRepeat());
                statement.setInt(5, unit.getGroup());
                statement.executeUpdate();
                logger.debug("updated complex unit {}", unit);
            } catch (SQLException e) {
                throw new PersistentException(e);
            }
        }
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_LIST_OF_EXERCISES_DELETE_REMAIN)) {
            statement.setInt(1, complex.getId());
            statement.setInt(2, complex.sizeOfComplexUnitList() - 1);
            int result = statement.executeUpdate();
            logger.debug("deleted complex unit {} rows", result);
        } catch (SQLException e) {
            throw new PersistentException(e);
        }


    }

    public void deleteComplex(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    public void deleteListOfExercises(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_LIST_OF_EXERCISES)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.debug("deleted complex {}", id);

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }


}
