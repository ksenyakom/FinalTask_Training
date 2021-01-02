package by.ksu.training.dao;

import by.ksu.training.entity.Person;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface UserDao extends Dao<User> {
    List<Person> readPersonByRole(Role role) throws PersistentException;
    List<User> readUserByRole(Role role) throws PersistentException; //TODO test
    User readByLoginAndPassword(String login, String password) throws PersistentException;
    boolean checkIfLoginExist(String login)  throws PersistentException;
}
