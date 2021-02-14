package by.ksu.training.dao;


import by.ksu.training.exception.PersistentException;

public interface Transaction {
	<T extends Dao<?>> T createDao(Class<T> key) throws PersistentException;

	void commit() throws PersistentException;

	void rollback() throws PersistentException;
}
