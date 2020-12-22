package by.ksu.training.service;


import by.ksu.training.dao.Transaction;

abstract public class ServiceImpl implements Service {
	protected Transaction transaction = null;

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
}
