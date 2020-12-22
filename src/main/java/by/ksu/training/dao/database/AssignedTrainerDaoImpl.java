package by.ksu.training.dao.database;

import by.ksu.training.dao.AssignedTrainerDao;
import by.ksu.training.entity.*;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ParseDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignedTrainerDaoImpl extends BaseDaoImpl implements AssignedTrainerDao {
    private static Logger logger = LogManager.getLogger(AssignedTrainerDaoImpl.class);
    ParseDate parseDate = new ParseDate();

    private static final String CREATE =
            "INSERT INTO `assigned_trainer`(`visitor_id`,`trainer_id`,`begin_date`,`end_date`) VALUES (?,?,?,?)";
    private static final String READ_BY_ID = "SELECT * FROM `assigned_trainer` WHERE `id` = ? ";
    private static final String READ_ALL = "SELECT * FROM `assigned_trainer` WHERE";
    private static final String READ_VISITORS_BY_TRAINER = "SELECT `visitor_id` FROM `assigned_trainer` WHERE `trainer_id` = ? ";
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
            assignedTrainer = new AssignedTrainer();
            assignedTrainer.setId(id);
            Visitor visitor = new Visitor();
            visitor.setId(resultSet.getInt("visitor_id"));
            assignedTrainer.setVisitor(visitor);
            Trainer trainer = new Trainer();
            trainer.setId(resultSet.getInt("trainer_id"));
            assignedTrainer.setTrainer(trainer);
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

            while (resultSet.next()) {
                assignedTrainer = new AssignedTrainer();
                assignedTrainer.setId(resultSet.getInt("id"));
                Visitor visitor = new Visitor();
                visitor.setId(resultSet.getInt("visitor_id"));
                assignedTrainer.setVisitor(visitor);
                Trainer trainer = new Trainer();
                trainer.setId(resultSet.getInt("trainer_id"));
                assignedTrainer.setTrainer(trainer);
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
    public List<Visitor> readListVisitorsByTrainer(Trainer trainer) throws PersistentException {
        List<Visitor> listVisitors = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(READ_VISITORS_BY_TRAINER)) {
            statement.setInt(1, trainer.getId());
            ResultSet resultSet = statement.executeQuery();
            Visitor visitor;
            while (resultSet.next()) {
                visitor = new Visitor();
                visitor.setId(resultSet.getInt("visitor_id"));
                listVisitors.add(visitor);
            }
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
        return listVisitors;
    }

    @Override
    public Trainer readCurrentTrainerByVisitor(Visitor visitor) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_CURRENT_TRAINER_BY_VISITOR)) {
            statement.setInt(1, visitor.getId());
            ResultSet resultSet = statement.executeQuery();

            Trainer trainer = null;
            if (resultSet.next()) {
                trainer = new Trainer();
                trainer.setId(resultSet.getInt("trainer_id"));
            }
            return trainer;

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public AssignedTrainer readCurrentByVisitor(Visitor visitor) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_CURRENT_BY_VISITOR)) {
            statement.setInt(1, visitor.getId());
            ResultSet resultSet = statement.executeQuery();

            AssignedTrainer assignedTrainer = null;

            if (resultSet.next()) {
                assignedTrainer = new AssignedTrainer();
                assignedTrainer.setId(resultSet.getInt("id"));
                Visitor visitor1 = new Visitor();
                visitor1.setId(visitor.getId());
                assignedTrainer.setVisitor(visitor1);
                Trainer trainer = new Trainer();
                trainer.setId(resultSet.getInt("trainer_id"));
                assignedTrainer.setTrainer(trainer);
                assignedTrainer.setBeginDate(parseDate.sqlToLocal(resultSet.getDate("begin_date")));
            }
            return assignedTrainer;

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

}
