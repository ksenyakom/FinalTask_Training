package by.ksu.training.service;

import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.entity.Visitor;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface UserService {
    List<Integer> findIdByRole(Role role) throws PersistentException;

    User findByIdentity(Integer id) throws PersistentException;

    User findByLoginAndPassword(String login, String password) throws PersistentException;

    void save(User user) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
