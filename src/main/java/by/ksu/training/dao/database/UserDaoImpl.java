package by.ksu.training.dao.database;

import by.ksu.training.entity.*;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {
    private static Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private static final String CREATE = "INSERT INTO `user`(`login`,`password`,`role`) VALUES (?,?,?)";
    private static final String READ_BY_ID = "SELECT * FROM `user` WHERE `id` = ? ";
    private static final String READ_BY_ROLE = "SELECT * FROM `user` WHERE `role`=? ORDER BY `id`";
    private static final String UPDATE = "UPDATE `user` SET `login` = ?,`password` = ?,`role` = ? WHERE `id` = ?";
    private static final String DELETE = "DELETE FROM `user` WHERE `id` = ?";


    @Override
    public List<Integer> readIdListByRole(Role role) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_ROLE)) {
            statement.setInt(1, role.getIdentity());
            ResultSet resultSet = statement.executeQuery();
            List<Integer> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getInt("id"));
            }
            return list;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public Integer create(User entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setInt(3, entity.getRole().getIdentity());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            logger.debug("inserted user {}", entity);
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
    public User read(Integer id) throws PersistentException {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            user = new User();
            user.setId(id);
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setRole(Role.getByIdentity(resultSet.getInt("role")));
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
        return user;
    }

    @Override
    public void update(User entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setInt(3, entity.getRole().getIdentity());
            statement.setInt(4, entity.getId());
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
            logger.debug("deleted user {}", id);

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }
}
