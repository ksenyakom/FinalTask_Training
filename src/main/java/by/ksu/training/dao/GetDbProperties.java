package by.ksu.training.dao;

import by.ksu.training.exception.PersistentException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetDbProperties implements GetProperties {

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
}
