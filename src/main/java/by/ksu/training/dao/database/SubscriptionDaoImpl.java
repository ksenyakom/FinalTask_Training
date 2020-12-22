package by.ksu.training.dao.database;

import by.ksu.training.dao.SubscriptionDao;
import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.Visitor;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ParseDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDaoImpl extends BaseDaoImpl implements SubscriptionDao {
    private static Logger logger = LogManager.getLogger(SubscriptionDaoImpl.class);
    ParseDate parseDate = new ParseDate();

    private static final String CREATE = "INSERT INTO `subscription`(`visitor_id`,`begin_date`,`end_date`,`price`) VALUES (?,?,?,?)";
    private static final String READ_BY_ID = "SELECT * FROM `subscription` WHERE `id` = ? ";
    private static final String READ_ALL = "SELECT * FROM `subscription` ORDER BY `id`";
    private static final String UPDATE = "UPDATE `subscription` SET `visitor_id`=?,`begin_date`=?,`end_date`=?,`price` = ? WHERE `id` = ?";
    private static final String DELETE = "DELETE FROM `subscription` WHERE `id` = ?";


    @Override
    public List<Subscription> read() throws PersistentException {

        try (PreparedStatement statement = connection.prepareStatement(READ_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Subscription> list = new ArrayList<>();
            Subscription subscription = null;

            while (resultSet.next()) {
                subscription = new Subscription();
                subscription.setId(resultSet.getInt("id"));
                Visitor visitor = new Visitor();
                visitor.setId(resultSet.getInt("visitor_id"));
                subscription.setVisitor(visitor);
                subscription.setBeginDate(parseDate.sqlToLocal(resultSet.getDate("begin_date")));
                subscription.setBeginDate(parseDate.sqlToLocal(resultSet.getDate("end_date")));
                subscription.setPrice(resultSet.getBigDecimal("price"));

                list.add(subscription);
            }
            return list;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public Integer create(Subscription entity) throws PersistentException {

        try (PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getVisitor().getId());
            statement.setDate(2, parseDate.localToSql(entity.getBeginDate()));
            statement.setDate(3, parseDate.localToSql(entity.getEndDate()));
            statement.setBigDecimal(4, entity.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            logger.debug("inserted subscription {}", entity);
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
    public Subscription read(Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Subscription subscription = null;

            if (resultSet.next()) {
                subscription = new Subscription();
                subscription.setId(resultSet.getInt("id"));
                Visitor visitor = new Visitor();
                visitor.setId(resultSet.getInt("visitor_id"));
                subscription.setVisitor(visitor);
                subscription.setBeginDate(parseDate.sqlToLocal(resultSet.getDate("begin_date")));
                subscription.setEndDate(parseDate.sqlToLocal(resultSet.getDate("end_date")));
                subscription.setPrice(resultSet.getBigDecimal("price"));
            }
            return subscription;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public void update(Subscription entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setInt(1, entity.getVisitor().getId());
            statement.setDate(2, parseDate.localToSql(entity.getBeginDate()));
            statement.setDate(3, parseDate.localToSql(entity.getEndDate()));
            statement.setBigDecimal(4, entity.getPrice());
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
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }
}
