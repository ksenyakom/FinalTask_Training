package by.ksu.training.dao;

import by.ksu.training.exception.PersistentException;

public interface TransactionFactory {
	Transaction createTransaction() throws PersistentException;

	void close();
}
