package by.ksu.training.dao;

import by.ksu.training.entity.Person;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface UserDao extends Dao<User> {
    List<User> readUserByRole(Role role) throws PersistentException;

    User readByLogin(String login) throws PersistentException;

    void readLogin(List<User> users) throws PersistentException;

    boolean checkIfLoginExist(String login) throws PersistentException;
}
