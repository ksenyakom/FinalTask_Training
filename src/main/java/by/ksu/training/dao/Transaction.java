package by.ksu.training.dao;


import by.ksu.training.exception.PersistentException;

public interface Transaction {
	<Type extends Dao<?>> Type createDao(Class<Type> key) throws PersistentException, PersistentException;

	void commit() throws PersistentException;

	void rollback() throws PersistentException;
}
