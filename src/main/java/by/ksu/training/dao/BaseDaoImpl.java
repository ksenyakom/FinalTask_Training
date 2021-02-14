package by.ksu.training.dao;

import java.sql.Connection;

/**
 * Base class containing connection.
 */
public abstract class BaseDaoImpl {
    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
