package by.ksu.training.dao.database.impl;

import by.ksu.training.dao.database.SubscriptionDao;
import by.ksu.training.dao.BaseDaoImpl;
import by.ksu.training.entity.Subscription;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ParseDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionDaoImpl extends BaseDaoImpl implements SubscriptionDao {

    private static Logger logger = LogManager.getLogger(SubscriptionDaoImpl.class);
    ParseDate parseDate = new ParseDate();

    private static final String CREATE = "INSERT INTO `subscription`(`visitor_id`,`begin_date`,`end_date`,`price`) VALUES (?,?,?,?)";
    private static final String READ_BY_ID = "SELECT `visitor_id`,`begin_date`,`end_date`,`price` FROM `subscription` WHERE `id` = ? ";
    private static final String READ_BY_VISITOR = "SELECT `id`,`begin_date`,`end_date`,`price` FROM `subscription` WHERE `visitor_id` = ? ORDER BY `begin_date` DESC ";
    private static final String READ_ALL = "SELECT * FROM `subscription` ORDER BY `id`";
    private static final String READ_ALL_ACTIVE = "SELECT * FROM `subscription` WHERE `end_date` >= CURDATE() AND `begin_date`<= CURDATE() ORDER BY `id`";
    private static final String READ_ACTIVE_BY_VISITOR = "SELECT `id`,`begin_date`,`end_date`,`price` FROM `subscription` WHERE `begin_date`<= CURDATE() AND `end_date` >= CURDATE()  AND `visitor_id` = ?";
    private static final String UPDATE = "UPDATE `subscription` SET `visitor_id`=?,`begin_date`=?,`end_date`=?,`price` = ? WHERE `id` = ?";
    private static final String DELETE = "DELETE FROM `subscription` WHERE `id` = ?";

    private static final String READ_FROM = "SELECT * FROM `subscription` WHERE `begin_date`>=? ";
    private static final String READ_FROM_TO_VISITOR_ID = "SELECT * FROM `subscription` WHERE  `begin_date`>= ? AND `begin_date`<= ? AND `visitor_id` = ?";
    private static final String READ_TO_AND_VISITOR_ID = "SELECT * FROM `subscription` WHERE  `begin_date`<= ? AND `visitor_id` = ?";
    private static final String READ_TO = "SELECT * FROM `subscription` WHERE  `begin_date`<= ?";
    private static final String READ_FROM_TO =  "SELECT * FROM `subscription` WHERE  `begin_date`>= ? AND `begin_date`<= ?";
    private static final String READ_FROM_AND_VISITOR_ID = "SELECT * FROM `subscription` WHERE  `begin_date`>= ? AND `visitor_id` = ?";;

    @Override
    public List<Subscription> read() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            return resultSetToList(resultSet);
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Subscription> readFrom(LocalDate from) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_FROM)) {
            statement.setDate(1, parseDate.localToSql(from));
            ResultSet resultSet = statement.executeQuery();
            return resultSetToList(resultSet);
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Subscription> readTo(LocalDate to) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_TO)) {
            statement.setDate(1, parseDate.localToSql(to));
            ResultSet resultSet = statement.executeQuery();
            return resultSetToList(resultSet);
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Subscription> readFromTo(LocalDate from, LocalDate to) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_FROM_TO)) {
            statement.setDate(1, parseDate.localToSql(from));
            statement.setDate(2, parseDate.localToSql(to));
            ResultSet resultSet = statement.executeQuery();
            return resultSetToList(resultSet);
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Subscription> readFromLogin(LocalDate from, User visitor) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_FROM_AND_VISITOR_ID)) {
            statement.setDate(1, parseDate.localToSql(from));
            statement.setInt(2, visitor.getId());
            ResultSet resultSet = statement.executeQuery();
            return resultSetToList(resultSet);
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Subscription> readToLogin(LocalDate to,User visitor) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_TO_AND_VISITOR_ID)) {
            statement.setDate(1, parseDate.localToSql(to));
            statement.setInt(2, visitor.getId());
            ResultSet resultSet = statement.executeQuery();
            return resultSetToList(resultSet);
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Subscription> readFromToLogin(LocalDate from, LocalDate to, User visitor) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_FROM_TO_VISITOR_ID)) {
            statement.setDate(1, parseDate.localToSql(from));
            statement.setDate(2, parseDate.localToSql(to));
            statement.setInt(3, visitor.getId());
            ResultSet resultSet = statement.executeQuery();
            return resultSetToList(resultSet);
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Subscription> readByUser(User user) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_VISITOR)) {
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            Subscription subscription = null;
            List<Subscription> subscriptionList = new ArrayList<>();

            while (resultSet.next()) {
                subscription = new Subscription(resultSet.getInt("id"));
                subscription.setVisitor(user);
                subscription.setBeginDate(parseDate.sqlToLocal(resultSet.getDate("begin_date")));
                subscription.setEndDate(parseDate.sqlToLocal(resultSet.getDate("end_date")));
                subscription.setPrice(resultSet.getBigDecimal("price"));
                subscriptionList.add(subscription);
            }
            return subscriptionList;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Subscription> readAllActive() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_ALL_ACTIVE)) {
            ResultSet resultSet = statement.executeQuery();
            List<Subscription> list = resultSetToList(resultSet);
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
                subscription = new Subscription(id);
                subscription.setVisitor(new User(resultSet.getInt("visitor_id")));
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
    public Subscription readActiveByUser(User user) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_ACTIVE_BY_VISITOR)) {
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            Subscription subscription = null;

            if (resultSet.next()) {
                subscription = new Subscription(resultSet.getInt("id"));
                subscription.setVisitor(user);
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

    private List<Subscription> resultSetToList(ResultSet resultSet) throws PersistentException {
        try {
            List<Subscription> list = new ArrayList<>();
            Subscription subscription = null;
            Map<Integer, User> userMap = new HashMap<>();
            User user = null;
            Integer userId = null;

            while (resultSet.next()) {
                subscription = new Subscription();
                subscription.setId(resultSet.getInt("id"));

                userId = resultSet.getInt("visitor_id");
                user = userMap.containsKey(userId) ? userMap.get(userId) : new User(userId);
                userMap.putIfAbsent(userId, user);

                subscription.setVisitor(user);
                subscription.setBeginDate(parseDate.sqlToLocal(resultSet.getDate("begin_date")));
                subscription.setEndDate(parseDate.sqlToLocal(resultSet.getDate("end_date")));
                subscription.setPrice(resultSet.getBigDecimal("price"));

                list.add(subscription);
            }
            return list;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }
}
