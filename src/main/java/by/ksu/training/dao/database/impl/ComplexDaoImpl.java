package by.ksu.training.dao.database.impl;

import by.ksu.training.dao.database.ComplexDao;
import by.ksu.training.dao.BaseDaoImpl;
import by.ksu.training.entity.*;
import by.ksu.training.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplexDaoImpl extends BaseDaoImpl implements ComplexDao {
    private static Logger logger = LogManager.getLogger(ComplexDaoImpl.class);

    private static final String CREATE = "INSERT INTO `complex`(`title`,`trainer_id`,`visitor_id`,`rating`) VALUES (?,?,?,?)";
    private static final String READ_BY_ID = "SELECT * FROM `complex` WHERE `id` = ? ";
    private static final String READ_ALL_COMMON_METADATA = "SELECT `id`,`title`,`trainer_id`,`rating` FROM `complex` WHERE `visitor_id` IS NULL ORDER  BY `rating` DESC";
    private static final String READ_METADATA_BY_VISITOR = "SELECT `id`,`title`,`visitor_id` FROM `complex` WHERE `visitor_id` IS NULL OR `visitor_id` = ? ORDER  BY `visitor_id` DESC";
    private static final String READ_INDIVIDUAL_METADATA_BY_VISITOR = "SELECT `id`,`title`,`trainer_id`,`rating` FROM `complex` WHERE `visitor_id` = ? ORDER  BY `visitor_id`";
    private static final String READ_TITLE_BY_ID = "SELECT `title` FROM `complex` WHERE `id` = ? ";
    private static final String READ_ALL = "SELECT * FROM `complex` ORDER BY `id`";
    private static final String UPDATE_COMPLEX = "UPDATE `complex` SET `title`=?,`trainer_id`=?,`visitor_id`=?,`rating`=? WHERE `id` = ?";
    private static final String DELETE = "DELETE FROM `complex` WHERE `id` = ?";

    private static final String CREATE_EXERCISE_IN_COMPLEX ="INSERT INTO `exercise_in_complex` (`complex_id`,`serial_number`,`exercise_id`, `repeat`, `group`) VALUES (?,?,?,?,?)";
    private static final String READ_LIST_OF_EXERCISES_BY_COMPLEX_ID = "SELECT * FROM `exercise_in_complex` where `complex_id` = ? ORDER BY `serial_number`";
    private static final String UPDATE_EXERCISE_IN_COMPLEX ="REPLACE INTO `exercise_in_complex` SET `complex_id` = ?,`serial_number` = ?,`exercise_id` = ?, `repeat` = ?, `group` = ?";
    private static final String UPDATE_LIST_OF_EXERCISES_DELETE_REMAIN = "DELETE FROM `exercise_in_complex` WHERE `complex_id` = ? AND `serial_number` > ?";
    private static final String DELETE_LIST_OF_EXERCISES = "DELETE FROM `exercise_in_complex`  WHERE `complex_id` = ?";
    private static final String CHECK_BY_TITLE = "SELECT 1 FROM `complex` WHERE `title`=? limit 1";


    @Override
    public boolean checkTitleExist(String title) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(CHECK_BY_TITLE)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) == 1;
            }
            return false;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

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
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setListOfComplexUnit(readListOfUnitsByComplexId(i));
        }
        return list;
    }

    @Override
    public List<Complex> readAllCommonComplexMetaData() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_ALL_COMMON_METADATA)) {
            ResultSet resultSet = statement.executeQuery();
            List<Complex> list = new ArrayList<>();
            Map<Integer, User> trainerMap = new HashMap<>();
            Complex complex = null;
            User trainer = null;

            while (resultSet.next()) {
                complex = new Complex(resultSet.getInt("id"));
                complex.setTitle(resultSet.getString("title"));

                Integer id = resultSet.getInt("trainer_id");
                if (!resultSet.wasNull()) {
                    trainer = trainerMap.merge(id, new User(id), (oldValue, newValue) -> oldValue);
                    complex.setTrainerDeveloped(trainer);
                }

                complex.setRating(resultSet.getFloat("rating"));

                list.add(complex);
            }
            return list;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * A method finds all individual complexes for list of visitors.
     *
     * @param visitors - users, for who we find complexes
     * @return - list of complexes
     * @throws PersistentException - when exception occur.
     */

    @Override
    public List<Complex> readIndividualComplexMetaDataByVisitors(List<User> visitors) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_INDIVIDUAL_METADATA_BY_VISITOR)) {
            List<Complex> list = new ArrayList<>();
            Map<Integer, User> trainerMap = new HashMap<>();
            Integer id;
            User trainer;

            for (User visitor : visitors) {
                statement.setInt(1, visitor.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    Complex complex = null;
                    while (resultSet.next()) {
                        complex = new Complex(resultSet.getInt("id"));
                        complex.setTitle(resultSet.getString("title"));
                        complex.setVisitorFor(visitor);
                        id = resultSet.getInt("trainer_id");
                        if (!resultSet.wasNull()) {
                            trainer = trainerMap.merge(id, new User(id), (oldValue, newValue) -> oldValue);
                            complex.setTrainerDeveloped(trainer);
                        }

                        complex.setRating(resultSet.getFloat("rating"));
                        list.add(complex);
                    }
                }
            }
            return list;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * A method finds all complexes available for visitor: all common complexes and special developed for him.
     *
     * @param visitor - user, for who we find complexes
     * @return - list of available complexes
     * @throws PersistentException - when exception occur.
     */
    @Override
    public List<Complex> readComplexMetaDataByUser(User visitor) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_METADATA_BY_VISITOR)) {
            statement.setInt(1, visitor.getId());
            ResultSet resultSet = statement.executeQuery();
            List<Complex> list = new ArrayList<>();
            Complex complex = null;

            while (resultSet.next()) {
                complex = new Complex(resultSet.getInt("id"));
                complex.setTitle(resultSet.getString("title"));

                int visitorId = resultSet.getInt("visitor_id");
                if (!resultSet.wasNull()) {
                    complex.setVisitorFor(visitor);
                }
                list.add(complex);
            }
            return list;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
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

    @Override
    public Integer createComplex(Complex entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getTitle());
            //TODO trainer and visitor might be null
            if (entity.getTrainerDeveloped() == null) {
                statement.setNull(2, Types.INTEGER);
            } else {
                statement.setInt(2, entity.getTrainerDeveloped().getId());
            }
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
                throw new PersistentException("There is no autoincremented index after trying to add record into table `readers`");
            }
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
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

    @Override
    public Complex readComplex(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Complex complex = null;

            if (resultSet.next()) {
                complex = new Complex();
                complex.setId(id);
                complex.setTitle(resultSet.getString("title"));

                int trainerId = resultSet.getInt("trainer_id");
                User trainer = null;
                if (!resultSet.wasNull()) {
                    trainer = new User(trainerId);
                    complex.setTrainerDeveloped(trainer);
                }

                int visitorId = resultSet.getInt("visitor_id");
                User visitor = null;
                if (!resultSet.wasNull()) {
                    visitor = new User(visitorId);
                    complex.setVisitorFor(visitor);
                }

                complex.setRating(resultSet.getFloat("rating"));
            }
            return complex;

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public void readTitle(List<Complex> complexes) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_TITLE_BY_ID)) {
            for (Complex complex : complexes) {
                statement.setInt(1, complex.getId());
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    complex.setTitle(resultSet.getString("title"));
                }
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Complex> readComplex() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Complex> list = new ArrayList<>();
            Map<Integer, User> trainerMap = new HashMap<>();
            Map<Integer, User> visitorMap = new HashMap<>();

            Complex complex = null;

            while (resultSet.next()) {
                complex = new Complex();
                complex.setId(resultSet.getInt("id"));
                complex.setTitle(resultSet.getString("title"));

                int trainerId = resultSet.getInt("trainer_id");
                User trainer = null;
                if (!resultSet.wasNull()) { //TODO проверить везде
                    trainer = trainerMap.merge(trainerId, new User(trainerId), (oldValue, newValue) -> oldValue);
                    complex.setTrainerDeveloped(trainer);
                }
                int visitorId = resultSet.getInt("visitor_id");
                User visitor = null;
                if (!resultSet.wasNull()) {
                    visitor = visitorMap.merge(visitorId, new User(visitorId), (oldValue, newValue) -> oldValue);
                    complex.setVisitorFor(visitor);
                }
                complex.setRating(resultSet.getFloat("rating"));
                list.add(complex);
            }
            return list;

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Complex.ComplexUnit> readListOfUnitsByComplexId(Integer complexId) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_LIST_OF_EXERCISES_BY_COMPLEX_ID)) {
            statement.setInt(1, complexId);
            ResultSet resultSet = statement.executeQuery();
            List<Complex.ComplexUnit> list = new ArrayList<>();
            Complex.ComplexUnit unit = null;
            Map<Integer, Exercise> exerciseMap = new HashMap<>();
            Exercise exercise;

            while (resultSet.next()) {
                unit = new Complex.ComplexUnit();
                int id = resultSet.getInt("exercise_id");
                exercise = exerciseMap.merge(id, new Exercise(id), (oldValue, newValue) -> oldValue);
                unit.setExercise(exercise);
                unit.setGroup(resultSet.getInt("group"));
                unit.setRepeat(resultSet.getInt("repeat"));
                list.add(unit);
            }
            return list;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }

    }

    @Override
    public void updateComplex(Complex entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_COMPLEX)) {
            statement.setString(1, entity.getTitle());
            if (entity.getTrainerDeveloped() == null) {
                statement.setNull(2, Types.INTEGER);
            } else {
                statement.setInt(2, entity.getTrainerDeveloped().getId());
            }
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

    @Override
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

    @Override
    public void deleteComplex(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
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
