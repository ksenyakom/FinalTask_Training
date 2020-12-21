package by.ksu.training.controller;

import by.ksu.training.dao.PersonDao;
import by.ksu.training.dao.Transaction;
import by.ksu.training.dao.TransactionFactory;
import by.ksu.training.dao.UserDao;
import by.ksu.training.dao.database.TransactionFactoryImpl;
import by.ksu.training.dao.pool.ConnectionPool;
import by.ksu.training.entity.Role;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.FilePath;
import by.ksu.training.service.GetDBProperties;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class Runner {
    public static final int DB_POOL_START_SIZE = 10;
    public static final int DB_POOL_MAX_SIZE = 100;
    public static final int DB_POOL_CHECK_CONNECTION_TIMEOUT = 0;

    public static void main(String[] args) throws SQLException {

        //TODO инициализовать справочники types
//        Connection connection = ConnectionCreator.getConnection();
//
//        try(Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM user ");
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString("login"));
//            }
//        }
        GetDBProperties getDBProperties = new GetDBProperties();
        Properties properties;
        try {
            // прочитали properties из файла
            properties = getDBProperties.fromFile(FilePath.dataBasePropertiesPath);

            //инициализируем пул соединений
            ConnectionPool.getInstance().init(properties, DB_POOL_START_SIZE, DB_POOL_MAX_SIZE, DB_POOL_CHECK_CONNECTION_TIMEOUT);

            TransactionFactory transactionFactory = new TransactionFactoryImpl();
            // он вытягивает соединение и хранит у себя
            Transaction transaction = transactionFactory.createTransaction();
            // получили transaction который хранит connection

            //получим всех тренеров
            UserDao userDao = transaction.createDao(UserDao.class);
            List<Integer> trainersIdList = userDao.readIdListByRole(Role.TRAINER);
            PersonDao dao = transaction.createDao(PersonDao.class);

//            List<Trainer> list = dao.read(trainersIdList);
//            transaction.commit();
//            list.forEach(item ->System.out.print(item + "\n"));

        } catch (PersistentException e) {
            e.printStackTrace();
        }
    }
}
