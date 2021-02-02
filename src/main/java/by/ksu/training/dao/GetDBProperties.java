package by.ksu.training.dao;

import by.ksu.training.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetDBProperties implements GetProperties{
    private static Logger logger = LogManager.getLogger(GetDBProperties.class);

    @Override
    public Properties fromFile(String fileProperties) throws PersistentException {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try (InputStream stream = classLoader.getResourceAsStream(fileProperties)) {
            properties.load(stream);
            return properties;
        } catch (IOException e) {
            throw new PersistentException("Could not read database properties.", e);
        }

    }

//    public Properties fromFile(String fileProperties) throws PersistentException {
//        Properties properties = new Properties();
//        try (FileReader reader = new FileReader(fileProperties)) {
//            properties.load(reader);
//            return properties;
//        } catch (IOException e) {
//            logger.error("Could not read database properties.", e);
//            throw new PersistentException("Could not read database properties.", e);
//        }
//    }
}
