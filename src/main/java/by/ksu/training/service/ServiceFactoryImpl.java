package by.ksu.training.service;

import by.ksu.training.dao.Transaction;
import by.ksu.training.dao.TransactionFactory;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceFactoryImpl implements ServiceFactory {
    //TODO разобраться что тут
    private static Logger logger = LogManager.getLogger(ServiceFactoryImpl.class);
    private TransactionFactory factory;

    public ServiceFactoryImpl(TransactionFactory factory) throws PersistentException {
        this.factory = factory;
    }

    public <T extends EntityService> T getService(Class<T> clazz) throws PersistentException {
        ServiceImpl service = null;
        if (clazz == AssignedComplexService.class) {
            service = new AssignedComplexServiceImpl();
        } else if (clazz == AssignedTrainerService.class) {
            service = new AssignedTrainerServiceImpl();
        } else if (clazz == ComplexService.class) {
            service = new ComplexServiceImpl();
        } else if (clazz == ExerciseService.class) {
            service = new ExerciseServiceImpl();
        } else if (clazz == SubscriptionService.class) {
            service = new SubscriptionServiceImpl();
        } else if (clazz == PersonService.class) {
            service = new PersonServiceImpl();
        } else if (clazz == UserService.class) {
            service = new UserServiceImpl();
        }
        if (service != null) {
            Transaction transaction = factory.createTransaction();
            service.setTransaction(transaction);
            return (T) service;
        } else {
            throw new PersistentException("No such service:" + clazz);
        }
    }

    @Override
    public void close() {
        factory.close();
    }
}