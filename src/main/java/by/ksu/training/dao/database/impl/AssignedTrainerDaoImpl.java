package by.ksu.training.dao.database.impl;

import by.ksu.training.dao.database.AssignedTrainerDao;
import by.ksu.training.dao.BaseDaoImpl;
import by.ksu.training.entity.AssignedTrainer;
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

public class AssignedTrainerDaoImpl extends BaseDaoImpl implements AssignedTrainerDao {
    private static Logger logger = LogManager.getLogger(AssignedTrainerDaoImpl.class);


    private static final String CREATE = "INSERT INTO `assigned_trainer`(`visitor_id`,`trainer_id`,`begin_date`,`end_date`) VALUES (?,?,?,?)";
    private static final String READ_BY_ID = "SELECT * FROM `assigned_trainer` WHERE `id` = ? ";
    private static final String READ_ALL = "SELECT * FROM `assigned_trainer`";
    private static final String READ_VISITORS_BY_TRAINER = "SELECT `visitor_id` FROM `assigned_trainer` WHERE `trainer_id` = ? and `end_date` IS NULL";
    private static final String READ_CURRENT_TRAINER_BY_VISITOR = "SELECT `trainer_id` FROM `assigned_trainer` WHERE `visitor_id` = ? and `end_date` IS NULL";
    private static final String READ_CURRENT_BY_VISITOR = "SELECT `id`,`trainer_id`,`begin_date` FROM `assigned_trainer` WHERE `visitor_id` = ? and `end_date` IS NULL";
    private static final String UPDATE = "UPDATE `assigned_trainer` SET `visitor_id` = ?,`trainer_id` = ?,`begin_date` = ?,`end_date` = ? WHERE `id` = ?";
    private static final String DELETE = "DELETE FROM `assigned_trainer` WHERE `id` = ?";

    @Override
    public Integer create(final AssignedTrainer entity) throws PersistentException {
        ParseDate parseDate = new ParseDate();
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
    public AssignedTrainer read(final Integer id) throws PersistentException {
        ParseDate parseDate = new ParseDate();
        AssignedTrainer assignedTrainer = null;
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                assignedTrainer = new AssignedTrainer(id);
                assignedTrainer.setVisitor(new User(resultSet.getInt("visitor_id")));
                assignedTrainer.setTrainer(new User(resultSet.getInt("trainer_id")));
                assignedTrainer.setBeginDate(parseDate.sqlToLocal(resultSet.getDate("begin_date")));
                assignedTrainer.setEndDate(parseDate.sqlToLocal(resultSet.getDate("end_date")));
            }
            return assignedTrainer;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }

    }

    @Override
    public List<AssignedTrainer> read() throws PersistentException {
        ParseDate parseDate = new ParseDate();
        try (PreparedStatement statement = connection.prepareStatement(READ_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            AssignedTrainer assignedTrainer = null;
            List<AssignedTrainer> list = new ArrayList<>();
            Map<Integer, User> userMap = new HashMap<>();
            Integer id = null;
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

    /**
     * Reads current trainers for list of users.
     *
     * @param users - users, who's trainer assignment should be found.
     * @return - list of AssignedTrainer.
     * @throws PersistentException - if SQLException occur while receiving data from database.
     */
    @Override
    public List<AssignedTrainer> readCurrentByUsers(final List<User> users) throws PersistentException {
        ParseDate parseDate = new ParseDate();
        try (PreparedStatement statement = connection.prepareStatement(READ_CURRENT_BY_VISITOR)) {
            AssignedTrainer assignedTrainer = null;
            List<AssignedTrainer> list = new ArrayList<>();
            Map<Integer, User> trainerMap = new HashMap<>();
            User trainer = null;
            Integer id = null;
            for (User user : users) {
                statement.setInt(1, user.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    assignedTrainer = new AssignedTrainer();
                    assignedTrainer.setVisitor(user);
                    if (resultSet.next()) {
                        assignedTrainer.setId(resultSet.getInt("id"));

                        id = resultSet.getInt("trainer_id");
                        trainer = trainerMap.containsKey(id) ? trainerMap.get(id) : new User(id);
                        trainerMap.putIfAbsent(id, trainer);
                        assignedTrainer.setTrainer(trainer);

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

    /**
     * Reads list of visitors by trainer.
     *
     * @param trainer - trainer, who's visitors should be read.
     * @return - list of visitors.
     * @throws PersistentException - if SQLException occur while receiving data from database.
     */
    @Override
    public List<User> readListVisitorsByTrainer(final User trainer) throws PersistentException {
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

    /**
     * Reads current trainer of visitor.
     *
     * @param visitor - user, who's actual trainer should be found.
     * @return - user - current trainer of visitor.
     * @throws PersistentException - if SQLException occur while receiving data from database.
     */
    @Override
    public User readCurrentTrainerByVisitor(final User visitor) throws PersistentException {
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

    /**
     * Reads current AssignedTrainer of visitor.
     *
     * @param visitor - user, who's actual AssignedTrainer should be found.
     * @return - AssignedTrainer of visitor.
     * @throws PersistentException - if SQLException occur while receiving data from database.
     */
    @Override
    public AssignedTrainer readCurrentByVisitor(final User visitor) throws PersistentException {
        ParseDate parseDate = new ParseDate();
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

    @Override
    public void update(final AssignedTrainer entity) throws PersistentException {
        ParseDate parseDate = new ParseDate();
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
    public void delete(final Integer id) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }


}
