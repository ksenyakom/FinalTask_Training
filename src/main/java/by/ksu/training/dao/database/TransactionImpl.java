package by.ksu.training.dao.database;

import by.ksu.training.dao.*;
import by.ksu.training.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionImpl implements Transaction {
    private static Logger logger = LogManager.getLogger(TransactionImpl.class);

    private static Map<Class<? extends Dao<?>>, Class<? extends BaseDaoImpl>> classes = new ConcurrentHashMap<>();

    static {
        classes.put(PersonDao.class, PersonDaoImpl.class);
        classes.put(UserDao.class, UserDaoImpl.class);
        classes.put(ExerciseDao.class, ExerciseDaoImpl.class);
        classes.put(ComplexDao.class, ComplexDaoImpl.class);
        classes.put(SubscriptionDao.class, SubscriptionDaoImpl.class);
        classes.put(AssignedComplexDao.class, AssignedComplexDaoImpl.class);
        classes.put(AssignedTrainerDao.class, AssignedTrainerDaoImpl.class);
    }

    private Connection connection;
    private DaoFactory daoFactory;

    public TransactionImpl(Connection connection) {
        this.connection = connection;
        daoFactory = DaoFactoryImpl.getInstance();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <Type extends Dao<?>> Type createDao(Class<Type> key) throws PersistentException {
        Class<? extends BaseDaoImpl> value = classes.get(key);
        if (value != null) {
            BaseDaoImpl dao = daoFactory.getDao(value);
            dao.setConnection(connection);
            return (Type) dao;
        }
        return null;
    }
//	@SuppressWarnings("unchecked")
//	@Override
//	public <Type extends Dao<?>> Type createDao(Class<Type> key) throws PersistentException {
//		Class<? extends BaseDaoImpl> value = classes.get(key);
//		if(value != null) {
//			try {
//				BaseDaoImpl dao = value.getConstructor().newInstance();// .newInstance(); //TODO Factory
//				dao.setConnection(connection);
//				return (Type)dao;
//			} catch(InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
//				logger.error("It is impossible to create data access object", e);
//				throw new PersistentException(e);
//			}
//		}
//		return null;
//	}

    @Override
    public void commit() throws PersistentException {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.error("It is impossible to commit transaction", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public void rollback() throws PersistentException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("It is impossible to rollback transaction", e);
            throw new PersistentException(e);
        }
    }
}
