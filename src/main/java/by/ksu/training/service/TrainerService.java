package by.ksu.training.service;

import by.ksu.training.entity.Trainer;

import java.util.List;

public interface TrainerService {
    List<Trainer> findAll() throws ServiceException;

    Trainer findByIdentity(Integer identity) throws ServiceException;

    Trainer findByLoginAndPassword(String login, String password) throws ServiceException;

    void save(Trainer user) throws ServiceException;

    void delete(Integer identity) throws ServiceException;
}
