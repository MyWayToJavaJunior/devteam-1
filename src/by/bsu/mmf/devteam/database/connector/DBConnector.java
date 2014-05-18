package by.bsu.mmf.devteam.database.connector;

import by.bsu.mmf.devteam.database.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class DBConnector {
    private ConnectionPool pool = ConnectionPool.getInstance();
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public DBConnector() {
        connection = pool.getConnection();
    }

    public Statement getStatement() {
        if (statement == null) {
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return statement;
    }

    public PreparedStatement getPreparedStatement(String query) {
        if (preparedStatement == null) {
            try {
                preparedStatement = connection.prepareStatement(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return preparedStatement;
    }

    public void close() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            pool.returnConnection(connection);
        }
    }

}
