package by.ksu.training.dao;

import by.ksu.training.dao.database.*;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ServiceFactoryImpl;
import by.ksu.training.service.ServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Author Kseniya Oznobishina
 * @Date 30.01.2021
 */
public class DaoFactoryImpl implements DaoFactory {
    private static Logger logger = LogManager.getLogger(DaoFactoryImpl.class);

    @Override
    public <Type extends BaseDaoImpl> Type getDao(Class<Type> clazz) throws PersistentException {
        BaseDaoImpl baseDao = null;
        if (clazz == AssignedComplexDaoImpl.class) {
            baseDao = new AssignedComplexDaoImpl();
        } else if (clazz == AssignedTrainerDaoImpl.class) {
            baseDao = new AssignedTrainerDaoImpl();
        } else if (clazz == ComplexDaoImpl.class) {
            baseDao = new ComplexDaoImpl();
        } else if (clazz == ExerciseDaoImpl.class) {
            baseDao = new ExerciseDaoImpl();
        } else if (clazz == SubscriptionDaoImpl.class) {
            baseDao = new SubscriptionDaoImpl();
        } else if (clazz == PersonDaoImpl.class) {
            baseDao = new PersonDaoImpl();
        } else if (clazz == UserDaoImpl.class) {
            baseDao = new UserDaoImpl();
        }
        if (baseDao != null) {
            return (Type) baseDao;
        } else {
    //        logger.error("No such dao: {}", clazz);
            throw new PersistentException("No such dao: " + clazz);
        }


    }
}
//    ServiceImpl service = null;
//            if (clazz == AssignedComplexService.class) { service = new AssignedComplexServiceImpl();}
//        else if (clazz == AssignedTrainerService.class) {service = new AssignedTrainerServiceImpl(); }
//        else if (clazz == ComplexService.class) {service =  new ComplexServiceImpl();}
//        else if (clazz == ExerciseService.class) {service = new ExerciseServiceImpl(); }
//        else if (clazz == SubscriptionService.class) {service =  new SubscriptionServiceImpl();}
//        else if (clazz == PersonService.class) {service =  new PersonServiceImpl();}
//        else if (clazz == UserService.class) {service =  new UserServiceImpl();}
//        if (service != null) {
//        Transaction transaction = factory.createTransaction();
//        service.setTransaction(transaction);
//        return (T)service;
//        } else {
//        logger.error("No such service: {}", clazz);
//        throw new PersistentException("No such service");
//        }