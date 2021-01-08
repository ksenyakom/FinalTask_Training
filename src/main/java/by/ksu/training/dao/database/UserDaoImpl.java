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

    private static final String CREATE = "INSERT INTO `user`(`login`,`password`,`email`,`role`) VALUES (?,?,?,?)";
    private static final String READ_BY_ID = "SELECT * FROM `user` WHERE `id` = ? ";
    private static final String READ_LOGIN_BY_ID = "SELECT `login` FROM `user` WHERE `id` = ? ";
    private static final String READ_ID_BY_ROLE = "SELECT `id` FROM `user` WHERE `role`=? ORDER BY `id`";
    private static final String READ_BY_ROLE = "SELECT `id`,`login`,`email` FROM `user` WHERE `role`=? ORDER BY `id`";
    private static final String READ_BY_LOGIN = "SELECT `id`, `role`, `email`,`password` FROM `user` WHERE `login` = ?";
    private static final String UPDATE = "UPDATE `user` SET `login` = ?,`password` = ?, `email` = ?,`role` = ? WHERE `id` = ?";
    private static final String DELETE = "DELETE FROM `user` WHERE `id` = ?";
    private static final String CHECK_BY_LOGIN = "SELECT 1 FROM `user` WHERE `login`=? limit 1";


    @Override
    public List<Person> readPersonByRole(Role role) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_ID_BY_ROLE)) {
            statement.setInt(1, role.getIdentity());
            ResultSet resultSet = statement.executeQuery();
            List<Person> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(new Person(resultSet.getInt("id")));
            }
            return list;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public List<User> readUserByRole(Role role) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_ROLE)) {
            statement.setInt(1, role.getIdentity());
            ResultSet resultSet = statement.executeQuery();
            List<User> list = new ArrayList<>();
            User user = null;
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(role);

                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public User readByLogin(String login) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(login);
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.getByIdentity(resultSet.getInt("role")));
                user.setEmail(resultSet.getString("email"));
            }
            return user;

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public boolean checkIfLoginExist(String login) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(CHECK_BY_LOGIN)) {
            statement.setString(1, login);
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
    public Integer create(User entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getEmail());
            statement.setInt(4, entity.getRole().getIdentity());
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
        try (PreparedStatement statement = connection.prepareStatement(READ_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            User user = null;

            if (resultSet.next()) {
                user = new User();
                user.setId(id);
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(Role.getByIdentity(resultSet.getInt("role")));
            }
            return user;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public void readLogin(List<User> users) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(READ_LOGIN_BY_ID)) {
            for (User user : users) {
                statement.setInt(1, user.getId());
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    user.setLogin(resultSet.getString("login"));
                }
                resultSet.close(); //TODO еще почитать про закрытие
            }
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public void update(User entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getEmail());
            statement.setInt(4, entity.getRole().getIdentity());
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
            logger.debug("deleted user {}", id);

        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }
}
