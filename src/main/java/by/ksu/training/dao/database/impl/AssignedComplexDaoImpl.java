package by.ksu.training.dao.database.impl;

import by.ksu.training.dao.database.AssignedComplexDao;
import by.ksu.training.dao.BaseDaoImpl;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ParseDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignedComplexDaoImpl extends BaseDaoImpl implements AssignedComplexDao {
    private static Logger logger = LogManager.getLogger(AssignedComplexDaoImpl.class);

    private static final String CREATE = "INSERT INTO `assigned_complex`(`visitor_id`,`complex_id`,`date_expected`,`date_executed`) VALUES (?,?,?,?)";
    private static final String READ_BY_ID = "SELECT * FROM `assigned_complex` WHERE `id` = ? ";
    private static final String READ_UNEXECUTED_BY_ID = "SELECT `id`,`complex_id`,`date_expected` FROM `assigned_complex` WHERE `visitor_id` = ? AND `date_executed` IS NULL";
    private static final String READ_EXECUTED_FOR_PERIOD = "SELECT * FROM `assigned_complex` WHERE DATEDIFF(CURDATE(), `date_executed`) < ? ORDER BY `date_executed` DESC";
    private static final String READ_EXECUTED_BY_VISITOR_FOR_PERIOD = "SELECT `id`,`complex_id`,`date_expected`,`date_executed` FROM `assigned_complex` WHERE `visitor_id` = ? AND  DATEDIFF(CURDATE(), `date_executed`) < ?";
    private static final String UPDATE = "UPDATE `assigned_complex` SET `visitor_id`=?,`complex_id`=?,`date_expected`=?,`date_executed`=? WHERE `id` = ?";
    private static final String DELETE = "DELETE FROM `assigned_complex` WHERE `id` = ?";

    @Override
    public Integer create(final AssignedComplex entity) throws PersistentException {
        ParseDate parseDate = new ParseDate();
        try (PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getVisitor().getId());
            statement.setInt(2, entity.getComplex().getId());
            statement.setDate(3, parseDate.localToSql(entity.getDateExpected()));
            statement.setDate(4, parseDate.localToSql(entity.getDateExecuted()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            logger.debug("inserted assigned complex {}", entity);
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

    @Override
    public AssignedComplex read(final Integer id) throws PersistentException {
        ParseDate parseDate = new ParseDate();
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            AssignedComplex assignedComplex = null;
            if (resultSet.next()) {
                assignedComplex = new AssignedComplex(id);
                User user = new User(resultSet.getInt("visitor_id"));
                assignedComplex.setVisitor(user);
                Complex complex = new Complex(resultSet.getInt("complex_id"));
                assignedComplex.setComplex(complex);
                assignedComplex.setDateExpected(parseDate.sqlToLocal(resultSet.getDate("date_expected")));
                assignedComplex.setDateExecuted(parseDate.sqlToLocal(resultSet.getDate("date_executed")));
            }
            return assignedComplex;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public AssignedComplex read(final AssignedComplex assignedComplex) throws PersistentException {
        ParseDate parseDate = new ParseDate();
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_ID)) {
            statement.setInt(1, assignedComplex.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User(resultSet.getInt("visitor_id"));
                assignedComplex.setVisitor(user);
                Complex complex = new Complex(resultSet.getInt("complex_id"));
                assignedComplex.setComplex(complex);
                assignedComplex.setDateExpected(parseDate.sqlToLocal(resultSet.getDate("date_expected")));
                assignedComplex.setDateExecuted(parseDate.sqlToLocal(resultSet.getDate("date_executed")));
            }
            return assignedComplex;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Reads assigned complexes unexecuted by user.
     *
     * @param user - user, who complex was assigned for.
     * @return - list of AssignedComplex for user.
     * @throws PersistentException - if SQLException occur while receiving data from database.
     */
    @Override
    public List<AssignedComplex> readUnexecutedByUser(final User user) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_UNEXECUTED_BY_ID)) {
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();

            return resultSetToObjectList(resultSet, user, false);
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Reads assigned complexes executed by user for period.
     *
     * @param user       - user, who complex was assigned for.
     * @param periodDays - period in days.
     * @return - list of AssignedComplex executed by user for period.
     * @throws PersistentException - if SQLException occur while receiving data from database.
     */

    @Override
    public List<AssignedComplex> readExecutedByUserForPeriod(final User user, final int periodDays) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_EXECUTED_BY_VISITOR_FOR_PERIOD)) {
            statement.setInt(1, user.getId());
            statement.setInt(2, periodDays);
            ResultSet resultSet = statement.executeQuery();

            return resultSetToObjectList(resultSet, user, true);
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Reads assigned complexes executed for period.
     *
     * @param periodDays - period in days.
     * @return - list of AssignedComplex executed  for period.
     * @throws PersistentException - if SQLException occur while receiving data from database
     */
    @Override
    public List<AssignedComplex> readExecutedForPeriod(final int periodDays) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_EXECUTED_FOR_PERIOD)) {
            statement.setInt(1, periodDays);
            ResultSet resultSet = statement.executeQuery();

            return resultSetToObjectList(resultSet, null, true);
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }


    @Override
    public void update(final AssignedComplex entity) throws PersistentException {
        ParseDate parseDate = new ParseDate();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setInt(1, entity.getVisitor().getId());
            statement.setInt(2, entity.getComplex().getId());
            statement.setDate(3, parseDate.localToSql(entity.getDateExpected()));
            statement.setDate(4, parseDate.localToSql(entity.getDateExecuted()));
            statement.setInt(5, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public void delete(final Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.debug("deleted assigned complex {}", id);
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    private List<AssignedComplex> resultSetToObjectList(final ResultSet resultSet, final User existUser, final boolean readDateExecuted) throws SQLException {
        ParseDate parseDate = new ParseDate();
        List<AssignedComplex> list = new ArrayList<>();
        Map<Integer, User> users = existUser == null ? new HashMap<>() : null;
        Map<Integer, Complex> complexes = new HashMap<>();
        AssignedComplex assignedComplex = null;
        Integer id = null;
        User user;
        Complex complex;

        while (resultSet.next()) {
            assignedComplex = new AssignedComplex();
            assignedComplex.setId(resultSet.getInt("id"));
            if (existUser != null) {
                assignedComplex.setVisitor(existUser);
            } else {
                id = resultSet.getInt("visitor_id");
                user = users.merge(id, new User(id), (oldValue, newValue) -> oldValue);
                assignedComplex.setVisitor(user);
            }
            id = resultSet.getInt("complex_id");
            complex = complexes.merge(id, new Complex(id), (oldValue, newValue) -> oldValue);
            assignedComplex.setComplex(complex);
            assignedComplex.setDateExpected(parseDate.sqlToLocal(resultSet.getDate("date_expected")));
            if (readDateExecuted) {
                assignedComplex.setDateExecuted(parseDate.sqlToLocal(resultSet.getDate("date_executed")));
            }
            list.add(assignedComplex);
        }
        return list;
    }
}
