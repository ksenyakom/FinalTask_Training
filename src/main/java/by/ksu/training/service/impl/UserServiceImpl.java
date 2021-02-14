package by.ksu.training.service.impl;

import by.ksu.training.dao.database.UserDao;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ServiceImpl;
import by.ksu.training.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class UserServiceImpl extends ServiceImpl implements UserService {


    @Override
    public List<User> findUserByRole(final Role role) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        return userDao.readUserByRole(role);
    }

    /**
     * Finds User by id.
     *
     * @param id - identity of record to find.
     * @return found User object or null, if record with this id not found.
     * @throws PersistentException - if exception occur in dao layer.
     */
    @Override
    public User findById(final Integer id) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        return userDao.read(id);
    }

    @Override
    public void findLogin(final List<User> users) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        userDao.readLogin(users);
    }

    @Override
    public User findByLoginAndPassword(final String login, final String password) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        User user = userDao.readByLogin(login);
        // Check that an unencrypted password matches one that has  been hashed previously
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }


    @Override
    public void save(final User user) throws PersistentException {
        try {
            UserDao userDao = transaction.createDao(UserDao.class);

            if (user.getId() != null) {
                if (user.getPassword() != null) {
                    user.setPassword(bcrypt(user.getPassword()));
                } else {
                    User oldUser = userDao.read(user.getId());
                    user.setPassword(oldUser.getPassword());
                }
                userDao.update(user);
            } else {
                user.setPassword(bcrypt(user.getPassword()));
                user.setId(userDao.create(user));
            }
            transaction.commit();
        } catch (PersistentException e) {
            transaction.rollback();
            throw new PersistentException("User can not be updated or saved", e);
        }
    }

    @Override
    public void delete(final Integer id) throws PersistentException {
        try {
            UserDao userDao = transaction.createDao(UserDao.class);
            userDao.delete(id);
            transaction.commit();
        } catch (PersistentException e) {
            transaction.rollback();
            throw new PersistentException("Subscription can not be deleted", e);
        }
    }

    @Override
    public boolean checkLoginExist(final String login) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        return userDao.checkIfLoginExist(login);
    }

    private String bcrypt(final String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt(10));
    }
}
