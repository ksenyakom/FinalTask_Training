package by.ksu.training.service.impl;

import by.ksu.training.entity.Person;
import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.EntityService;

import java.util.List;

public interface UserService extends EntityService {
    List<Person> findPersonIdByRole(Role role) throws PersistentException;

    User findByIdentity(Integer id) throws PersistentException;

    User findByLoginAndPassword(String login, String password) throws PersistentException;

    void save(User user) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
