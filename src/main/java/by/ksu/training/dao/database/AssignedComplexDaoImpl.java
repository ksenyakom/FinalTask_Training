package by.ksu.training.dao.database;

import by.ksu.training.dao.AssignedComplexDao;
import by.ksu.training.entity.AssignedComplex;
import by.ksu.training.entity.Complex;
import by.ksu.training.entity.Visitor;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ParseDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignedComplexDaoImpl extends BaseDaoImpl implements AssignedComplexDao {
    private static Logger logger = LogManager.getLogger(AssignedComplexDaoImpl.class);
    ParseDate parseDate = new ParseDate();


    private static final String CREATE = "INSERT INTO `assigned_complex`(`visitor_id`,`complex_id`,`date_expected`,`date_executed`) VALUES (?,?,?,?)";
    private static final String READ_BY_ID = "SELECT * FROM `assigned_complex` WHERE `id` = ? ";
    private static final String READ_UNEXECUTED_BY_ID = "SELECT `id`,`complex_id`,`date_expected` FROM `assigned_complex` WHERE `visitor_id` = ? AND `date_executed` IS NULL";
    private static final String READ_EXECUTED_FOR_PERIOD =
            "SELECT * FROM `assigned_complex` WHERE DATEDIFF(CURDATE(), `date_executed`) < ?";

    private static final String READ_EXECUTED_BY_VISITOR_FOR_PERIOD =
            "SELECT `id`,`complex_id`,`date_expected`,`date_executed` FROM `assigned_complex` WHERE `visitor_id` = ? AND  DATEDIFF(CURDATE(), `date_executed`) < ?";

    private static final String UPDATE = "UPDATE `assigned_complex` SET `visitor_id`=?,`complex_id`=?,`date_expected`=?,`date_executed`=? WHERE `id` = ?";
    private static final String DELETE = "DELETE FROM `assigned_complex` WHERE `id` = ?";


    @Override
    public List<AssignedComplex> readUnexecutedByVisitor(Visitor visitor) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_UNEXECUTED_BY_ID)) {
            statement.setInt(1, visitor.getId());
            ResultSet resultSet = statement.executeQuery();
            List<AssignedComplex> list = new ArrayList<>();
            AssignedComplex assignedComplex = null;

            while (resultSet.next()) {
                assignedComplex = new AssignedComplex();
                assignedComplex.setId(resultSet.getInt("id"));
                assignedComplex.setVisitor(visitor);
                Complex complex = new Complex();
                complex.setId(resultSet.getInt("complex_id"));
                assignedComplex.setComplex(complex);
                assignedComplex.setDateExpected(parseDate.sqlToLocal(resultSet.getDate("date_expected")));
                list.add(assignedComplex);
            }
            return list;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<AssignedComplex> readExecutedByVisitorForPeriod(Visitor visitor, int periodDays) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_EXECUTED_BY_VISITOR_FOR_PERIOD)) {
            statement.setInt(1, visitor.getId());
            statement.setInt(2, periodDays);
            ResultSet resultSet = statement.executeQuery();
            List<AssignedComplex> list = new ArrayList<>();
            AssignedComplex assignedComplex = null;

            while (resultSet.next()) {
                assignedComplex = new AssignedComplex();
                assignedComplex.setId(resultSet.getInt("id"));
                Visitor visitor1 = new Visitor();
                visitor1.setId(visitor.getId());
                assignedComplex.setVisitor(visitor1);
                Complex complex = new Complex();
                complex.setId(resultSet.getInt("complex_id"));
                assignedComplex.setComplex(complex);
                assignedComplex.setDateExpected(parseDate.sqlToLocal(resultSet.getDate("date_expected")));
                assignedComplex.setDateExecuted(parseDate.sqlToLocal(resultSet.getDate("date_executed")));
                list.add(assignedComplex);
            }
            return list;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<AssignedComplex> readExecutedForPeriod(int periodDays) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_EXECUTED_FOR_PERIOD)) {
            statement.setInt(1, periodDays);
            ResultSet resultSet = statement.executeQuery();
            List<AssignedComplex> list = new ArrayList<>();
            AssignedComplex assignedComplex = null;

            while (resultSet.next()) {
                assignedComplex = new AssignedComplex();
                assignedComplex.setId(resultSet.getInt("id"));
                Visitor visitor = new Visitor();
                visitor.setId(resultSet.getInt("visitor_id"));
                assignedComplex.setVisitor(visitor);
                Complex complex = new Complex();
                complex.setId(resultSet.getInt("complex_id"));
                assignedComplex.setComplex(complex);
                assignedComplex.setDateExpected(parseDate.sqlToLocal(resultSet.getDate("date_expected")));
                assignedComplex.setDateExecuted(parseDate.sqlToLocal(resultSet.getDate("date_executed")));
                list.add(assignedComplex);
            }
            return list;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public Integer create(AssignedComplex entity) throws PersistentException {
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
    public AssignedComplex read(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            AssignedComplex assignedComplex = null;

            if (resultSet.next()) {
                assignedComplex = new AssignedComplex();
                assignedComplex.setId(id);
                Visitor visitor = new Visitor();
                visitor.setId(resultSet.getInt("visitor_id"));
                assignedComplex.setVisitor(visitor);
                Complex complex = new Complex();
                complex.setId(resultSet.getInt("complex_id"));
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
    public void update(AssignedComplex entity) throws PersistentException {
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
    public void delete(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.debug("deleted assigned complex {}", id);

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }
}
