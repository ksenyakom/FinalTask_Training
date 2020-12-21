package by.ksu.training.dao;


import by.ksu.training.entity.Entity;
import by.ksu.training.exception.PersistentException;

public interface Dao<Type extends Entity> {
	Integer create(Type entity) throws PersistentException;

	Type read(Integer id) throws PersistentException;

	void update(Type entity) throws PersistentException;

	void delete(Integer id) throws PersistentException;
}
