package by.bsu.mmf.devteam.database.pool;

import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class ConnectionPool {
    private static Logger logger = Logger.getLogger("db");
    private static ConnectionPool instance;
    private BlockingQueue<Connection> pool;
    private PoolConfiguration config;
    private static final String LOGGER_LOAD_JDBC_ERROR = "logger.error.jdbc.load";
    private static final String LOGGER_GET_CONNECTION_ERROR = "logger.error.get.connection";
    private static final String LOGGER_TAKE_CONNECTION_ERROR = "logger.error.take.connection";
    private static final String LOGGER_PUT_CONNECTION_ERROR = "logger.error.put.connection";

    public ConnectionPool() {
        initPool();
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private void initPool()  {
        config = new PoolConfiguration();
        pool = new ArrayBlockingQueue<Connection>(config.getMaxSize(), true);
        try {
            Class.forName(config.getDriver());
            for (int i = 0; i < config.getMinSize(); i++) {
                pool.add(DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword()));
            }
        } catch (ClassNotFoundException e) {
            logger.error(ResourceManager.getProperty(LOGGER_LOAD_JDBC_ERROR), e);
        } catch (SQLException e) {
            logger.error(ResourceManager.getProperty(LOGGER_GET_CONNECTION_ERROR), e);
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            if (!pool.isEmpty()) {
                connection = pool.take();
            } else if (pool.size() < config.getMaxSize()) {
                connection = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword());
            } else {
                connection = pool.take();
            }
            if (connection.isClosed()) {
                connection = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword());
            }
        } catch (InterruptedException exception) {
            logger.error(ResourceManager.getProperty(LOGGER_TAKE_CONNECTION_ERROR), exception);
        } catch (SQLException exception) {
            logger.error(ResourceManager.getProperty(LOGGER_GET_CONNECTION_ERROR), exception);
        }
        return connection;
    }

    public void returnConnection(Connection connection) {
        if (connection != null) {
            try {
                pool.put(connection);
            } catch (InterruptedException e) {
                logger.error(ResourceManager.getProperty(LOGGER_PUT_CONNECTION_ERROR), e);
            }
        }
    }

}
