package by.ksu.training.service;

import by.ksu.training.exception.PersistentException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GetDBProperties {
    //TODo нужно делать потокобезопасным?
    public Properties fromFile(String fileProperties) throws PersistentException {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(fileProperties));
        } catch (IOException e) {
            e.printStackTrace(); //TODO logger
            throw new PersistentException("Could not read by.ksu.database properties.",e);
        }
        return properties;
    }
}
