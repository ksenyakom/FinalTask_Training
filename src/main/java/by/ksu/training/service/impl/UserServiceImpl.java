package by.ksu.training.service.impl;

import by.ksu.training.dao.UserDao;
import by.ksu.training.entity.Person;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ServiceImpl;
import by.ksu.training.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class UserServiceImpl extends ServiceImpl implements UserService {


    @Override
    public List<User> findUserByRole(Role role) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        return userDao.readUserByRole(role);
    }

    @Override
    public User findById(Integer id) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        return userDao.read(id);
    }

    @Override
    public void findLogin(List<User> users) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        userDao.readLogin(users);
    }

    @Override
    public User findByLoginAndPassword(String login, String password) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        User user = userDao.readByLogin(login);
        // Check that an unencrypted password matches one that has
// previously been hashed
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }


    @Override
    public void save(User user) throws PersistentException {
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
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        userDao.delete(id);
    }

    @Override
    public boolean checkLoginExist(String login) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        return userDao.checkIfLoginExist(login);
    }

    private String bcrypt(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt(10));
    }
}
