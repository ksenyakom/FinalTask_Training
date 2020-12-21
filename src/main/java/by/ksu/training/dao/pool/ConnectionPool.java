package by.ksu.training.dao.pool;

import by.ksu.training.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {
    private static Logger logger = LogManager.getLogger(ConnectionPool.class);

    private Properties properties;
    private String databaseUrl;
    private int maxSize;
    private int checkConnectionTimeout;

    private BlockingQueue<PooledConnection> freeConnections = new LinkedBlockingQueue<>();
    private Set<PooledConnection> usedConnections = new ConcurrentSkipListSet<>();
    private static final ReentrantLock lock = new ReentrantLock();

    /**
     * Constructor
     */
    private ConnectionPool() {
    }

    public Connection getConnection() throws PersistentException {
            PooledConnection connection = null;
            while (connection == null) {
                try {
                    if (!freeConnections.isEmpty()) {
                        connection = freeConnections.take();
                        if (!connection.isValid(checkConnectionTimeout)) {
                            try {
                                connection.getConnection().close();
                            } catch (SQLException e) {
                            }
                            connection = null;
                        }
                    } else if (usedConnections.size() < maxSize) {
                        connection = createConnection();
                    } else {
                        logger.error("The limit of number of by.ksu.database connections is exceeded");
                        throw new PersistentException();

                    }
                } catch (InterruptedException | SQLException e) {
                    logger.error("It is impossible to connect to a by.ksu.database", e);
                    throw new PersistentException(e);
                }
            }

            usedConnections.add(connection);
            logger.debug("Connection was received from pool. Current pool size:{} used connections; {} free connection", usedConnections.size(), freeConnections.size());
            return connection;
    }

    void freeConnection(PooledConnection connection) {
        try {
            if (connection.isValid(checkConnectionTimeout)) {
                connection.clearWarnings();
                connection.setAutoCommit(true);
                usedConnections.remove(connection);
                freeConnections.put(connection);
                logger.debug("Connection was returned into pool. Current pool size: {} used connections; {} free connection", usedConnections.size(), freeConnections.size());
            }
        } catch (SQLException | InterruptedException e1) {
            logger.warn("It is impossible to return by.ksu.database connection into pool", e1);
            try {
                connection.getConnection().close();
            } catch (SQLException e2) {
            }
        }
    }

    public void init(Properties properties, int startSize, int maxSize, int checkConnectionTimeout) throws PersistentException {
        lock.lock();
        try {
            destroy();

            String driverName = (String) properties.get("driver"); // подключили драйвер
            Class.forName(driverName);
            this.properties = properties;
            this.databaseUrl = (String) properties.get("db.url");
            this.maxSize = maxSize;
            this.checkConnectionTimeout = checkConnectionTimeout;
            for (int counter = 0; counter < startSize; counter++) {
                freeConnections.put(createConnection());
            }
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            logger.fatal("It is impossible to initialize connection pool", e);
            throw new PersistentException(e);
        } finally {
            lock.unlock();
        }
    }

    private static ConnectionPool instance;

    public static ConnectionPool getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new ConnectionPool();
            }
            return instance;
        } finally {
            lock.unlock();
        }
    }

    private PooledConnection createConnection() throws SQLException {
        return new PooledConnection(DriverManager.getConnection(databaseUrl, properties));
    }

    public void destroy() {
        lock.lock();
        try {
            usedConnections.addAll(freeConnections);
            freeConnections.clear();
            for (PooledConnection connection : usedConnections) {
                try {
                    connection.getConnection().close();
                } catch (SQLException e) {
                }
            }
            usedConnections.clear();
        } finally {
            lock.unlock();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
    }
}
