package by.ksu.training.service;

import by.ksu.training.entity.Person;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.EntityService;

import java.util.List;

public interface UserService extends EntityService<User> {

    List<User> findUserByRole(Role role) throws PersistentException;

    void findLogin(List<User> users) throws PersistentException;

    User findByLoginAndPassword(String login, String password) throws PersistentException;

    boolean checkLoginExist(String login) throws PersistentException;
}
