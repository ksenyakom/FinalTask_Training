package by.ksu.training.dao;

import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.exception.PersistentException;

import java.util.List;

public interface UserDao extends Dao<User> {
    List<Integer> readIdListByRole(Role role) throws PersistentException;
}
