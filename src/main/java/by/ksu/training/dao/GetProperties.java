package by.ksu.training.dao;

import by.ksu.training.exception.PersistentException;

import java.util.Properties;

public interface GetProperties {
     Properties fromFile(String fileProperties) throws PersistentException;
}
