package by.ksu.training.dao.database;

import by.ksu.training.dao.AssignedTrainerDao;
import by.ksu.training.entity.*;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ParseDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignedTrainerDaoImpl extends BaseDaoImpl implements AssignedTrainerDao {
    private static Logger logger = LogManager.getLogger(AssignedTrainerDaoImpl.class);
    ParseDate parseDate = new ParseDate();

    private static final String CREATE =
            "INSERT INTO `assigned_trainer`(`visitor_id`,`trainer_id`,`begin_date`,`end_date`) VALUES (?,?,?,?)";
    private static final String READ_BY_ID = "SELECT * FROM `assigned_trainer` WHERE `id` = ? ";
    private static final String READ_ALL = "SELECT * FROM `assigned_trainer`";
    private static final String READ_VISITORS_BY_TRAINER = "SELECT `visitor_id` FROM `assigned_trainer` WHERE `trainer_id` = ? and `end_date` IS NULL";
    private static final String READ_CURRENT_TRAINER_BY_VISITOR =
            "SELECT `trainer_id` FROM `assigned_trainer` WHERE `visitor_id` = ? and `end_date` IS NULL";
    private static final String READ_CURRENT_BY_VISITOR =
            "SELECT `id`,`trainer_id`,`begin_date` FROM `assigned_trainer` WHERE `visitor_id` = ? and `end_date` IS NULL";
    private static final String UPDATE =
            "UPDATE `assigned_trainer` SET `visitor_id` = ?,`trainer_id` = ?,`begin_date` = ?,`end_date` = ? WHERE `id` = ?";
    private static final String DELETE = "DELETE FROM `assigned_trainer` WHERE `id` = ?";

    @Override
    public Integer create(AssignedTrainer entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getVisitor().getId());
            statement.setInt(2, entity.getTrainer().getId());
            statement.setDate(3, parseDate.localToSql(entity.getBeginDate()));
            statement.setDate(4, parseDate.localToSql(entity.getEndDate()));
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

    @Override
    public AssignedTrainer read(Integer id) throws PersistentException {
        AssignedTrainer assignedTrainer = null;
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            assignedTrainer = new AssignedTrainer(id);
            assignedTrainer.setVisitor(new User(resultSet.getInt("visitor_id")));
            assignedTrainer.setTrainer(new User(resultSet.getInt("trainer_id")));
            assignedTrainer.setBeginDate(parseDate.sqlToLocal(resultSet.getDate("begin_date")));
            assignedTrainer.setEndDate(parseDate.sqlToLocal(resultSet.getDate("end_date")));
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
        return assignedTrainer;
    }

    @Override
    public List<AssignedTrainer> read() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            AssignedTrainer assignedTrainer = null;
            List<AssignedTrainer> list = new ArrayList<>();
            Map<Integer, User> userMap = new HashMap<>();
            Integer id =  null;
            User user = null;

            while (resultSet.next()) {
                assignedTrainer = new AssignedTrainer();
                assignedTrainer.setId(resultSet.getInt("id"));

                id = resultSet.getInt("visitor_id");
                user = userMap.containsKey(id) ? userMap.get(id) : new User(id);
                userMap.putIfAbsent(id, user);
                assignedTrainer.setVisitor(user);

                id = resultSet.getInt("trainer_id");
                user = userMap.containsKey(id) ? userMap.get(id) : new User(id);
                userMap.putIfAbsent(id, user);
                assignedTrainer.setTrainer(user);

                assignedTrainer.setBeginDate(parseDate.sqlToLocal(resultSet.getDate("begin_date")));
                assignedTrainer.setEndDate(parseDate.sqlToLocal(resultSet.getDate("end_date")));
                list.add(assignedTrainer);
            }
            return list;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<AssignedTrainer> readCurrentByUsers(List<User> users) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_CURRENT_BY_VISITOR)) {
            AssignedTrainer assignedTrainer = null;
            List<AssignedTrainer> list = new ArrayList<>();
            Map<Integer, User> trainerMap = new HashMap<>();
            User trainer = null;
            Integer id = null;
            for (User user : users) {
                statement.setInt(1, user.getId());
                try(ResultSet resultSet = statement.executeQuery()) {
                    assignedTrainer = new AssignedTrainer();
                    assignedTrainer.setVisitor(user);
                    if (resultSet.next()) {
                        assignedTrainer.setId(resultSet.getInt("id"));

                        id = resultSet.getInt("trainer_id");
                        user = trainerMap.containsKey(id) ? trainerMap.get(id) : new User(id);
                        trainerMap.putIfAbsent(id, user);
                        assignedTrainer.setTrainer(user);

                        assignedTrainer.setBeginDate(parseDate.sqlToLocal(resultSet.getDate("begin_date")));
                    }
                    list.add(assignedTrainer);
                }
            }
            return list;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public void update(AssignedTrainer entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setInt(1, entity.getVisitor().getId());
            statement.setInt(2, entity.getTrainer().getId());
            statement.setDate(3, parseDate.localToSql(entity.getBeginDate()));
            statement.setDate(4, parseDate.localToSql(entity.getEndDate()));
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

    @Override
    public List<User> readListVisitorsByTrainer(User trainer) throws PersistentException {
        List<User> listVisitors = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(READ_VISITORS_BY_TRAINER)) {
            statement.setInt(1, trainer.getId());
            ResultSet resultSet = statement.executeQuery();
            User visitor;
            while (resultSet.next()) {
                visitor = new User(resultSet.getInt("visitor_id"));
                listVisitors.add(visitor);
            }
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
        return listVisitors;
    }

    @Override
    public User readCurrentTrainerByVisitor(User visitor) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_CURRENT_TRAINER_BY_VISITOR)) {
            statement.setInt(1, visitor.getId());
            ResultSet resultSet = statement.executeQuery();

            User trainer = null;
            if (resultSet.next()) {
                trainer = new User(resultSet.getInt("trainer_id"));
            }
            return trainer;

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public AssignedTrainer readCurrentByVisitor(User visitor) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_CURRENT_BY_VISITOR)) {
            statement.setInt(1, visitor.getId());
            ResultSet resultSet = statement.executeQuery();

            AssignedTrainer assignedTrainer = null;

            if (resultSet.next()) {
                assignedTrainer = new AssignedTrainer(resultSet.getInt("id"));
                assignedTrainer.setVisitor(visitor);
                assignedTrainer.setTrainer(new User(resultSet.getInt("trainer_id")));
                assignedTrainer.setBeginDate(parseDate.sqlToLocal(resultSet.getDate("begin_date")));
            }
            return assignedTrainer;

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

}
